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
    public Account authenticateUser(@RequestBody Account account){
        return Optional
                .ofNullable(getAccountForUsername(account.getUsername()))
                .filter(acc ->
                        acc.getPassword().equals(account.getPassword()))
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
