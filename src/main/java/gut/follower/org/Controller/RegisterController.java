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
    public Account registerUser(@RequestBody Account account) {
        return accountRepository.save(registerAccount(account));
    }

    private Account checkIfEmailNotTaken(Account account) {
        return Optional.of(account)
                .filter(acc ->
                            accountRepository
                                    .findByEmail(acc.getEmail()) == null)
                .orElseThrow(() ->
                        new IllegalStateException("Email has been taken"));
    }

    private Account registerAccount(Account account) {
        return Optional
                .ofNullable(checkIfEmailNotTaken(account))
                .filter(acc ->
                            accountRepository
                                    .findByUsername(acc.getUsername()) == null)
                .orElseThrow(() ->
                        new IllegalStateException("Username has been taken"));
    }
}
