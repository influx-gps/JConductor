package gut.follower.org.Controller;

import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Account authenticateUser(@RequestBody Map<String, String> userData){
        return Optional
                .ofNullable(getAccountForUsername(userData.get("username")))
                .filter(account ->
                        account.getPassword().equals(userData.get("password")))
                .orElseThrow(() ->
                        new IllegalStateException("Wrong user credentials"));
    }

    private Account getAccountForUsername(String username){
        return Optional
                .ofNullable(accountRepository.findByUsername(username))
                .orElseThrow(() ->
                        new IllegalStateException("Wrong user credentials"));
    }
}
