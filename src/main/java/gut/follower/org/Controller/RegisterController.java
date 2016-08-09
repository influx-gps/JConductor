package gut.follower.org.Controller;

import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.tags.form.OptionsTag;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Account registerUser(@RequestBody Map<String, String> map){
        return Optional.ofNullable(checkEmailExistence(map))
                .filter(this::checkUsernameExistence)
                .orElseThrow(() ->
                        new IllegalStateException("Username has been taken"));
    }

    private Account checkEmailExistence(Map<String, String> map){
        return Optional.of(new Account(map.get("username"), map.get("password"), map.get("email")))
                .filter(account ->
                        accountRepository
                                .findByEmail(account.getEmail()) == null)
                .orElseThrow(() ->
                        new IllegalStateException("Email has been taken"));
    }

    private boolean checkUsernameExistence(Account account){
        return Optional
                .ofNullable(accountRepository.findByUsername(account.getUsername()))
                .isPresent();
    }
}
