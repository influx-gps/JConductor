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
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Account authenticateUser(@RequestBody Map<String, String> map){
        Account account = accountRepository.findByUsername(map.get("username"));
        if(account != null){
            if(account.getPassword().equals(map.get("password"))){
                return account;
            } else {
                throw new IllegalStateException("Wrong user credentials");
            }
        } else {
            throw new IllegalStateException("Wrong user credentials");
        }
    }
}
