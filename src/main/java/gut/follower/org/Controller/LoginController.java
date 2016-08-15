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
                .ofNullable(getAccountForUsername(account))
                .filter(acc ->
                            acc.getPassword().equals(account.getPassword()))
                .orElseThrow(() ->
                            new IllegalStateException("Wrong user credentials"));
    }

    public Account getAccountForUsername(Account username){
        return Optional
                .ofNullable(accountRepository.findByUsername(username.getUsername()))
                .orElseThrow(() ->
                             new IllegalStateException("Wrong user credentials"));
    }
}
