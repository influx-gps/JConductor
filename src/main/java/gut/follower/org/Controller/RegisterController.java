package gut.follower.org.Controller;

import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Account registerUser(@RequestBody Map<String, String> map){
        Account account = new Account(map.get("username"), map.get("password"), map.get("email"));
        if(accountRepository.findByEmail(account.getEmail()) != null){
            throw new IllegalStateException("There is account registered with the given email");
        } else if(accountRepository.findByUsername(account.getUsername()) == null){
            return accountRepository.save(account);
        } else {
            throw new IllegalStateException("Username "+account.getUsername()+" is already taken");
        }
    }
}
