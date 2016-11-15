package gut.follower.org.Controller;

import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/rest/account")
public class AccountController {

    @Autowired
    AccountRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public Account getAccount(Principal principal){
        return Optional
                .ofNullable(repository.findByUsername(principal.getName()))
                .orElseThrow(() -> new IllegalStateException("Account does not exist"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account updateAccount(Principal principal, @RequestBody Account account){
        return Optional
                .ofNullable(account)
                .map(newAcc -> {
                    Account oldAcc = getAccountByName(principal);
                    oldAcc.setPassword(getPassword(oldAcc, newAcc));
                    oldAcc.setEmail(getEmail(oldAcc, newAcc));
                    return repository.save(oldAcc);
                })
                .orElseThrow(() -> new IllegalStateException("Changes could not be applied"));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public HttpStatus deleteAccount(Principal principal){
        repository.delete(getAccountByName(principal));
        return HttpStatus.OK;
    }

    private String getEmail(Account oldAcc, Account newAcc) {
        return Optional
                .ofNullable(newAcc.getEmail())
                .map(this::getNewEmailIfNotAlreadyTaken)
                .orElse(oldAcc.getEmail());
    }

    private String getNewEmailIfNotAlreadyTaken(String email) {
        return Optional
                .ofNullable(email)
                .filter(em -> repository.findByEmail(em) == null)
                .orElseThrow(() -> new IllegalStateException("Email already taken"));
    }

    private String getPassword(Account oldAcc, Account newAcc) {
        return Optional
                .ofNullable(newAcc.getPassword())
                .orElse(oldAcc.getPassword());
    }

    private Account getAccountByName(Principal principal) {
        return Optional
                .ofNullable(repository.findByUsername(principal.getName()))
                .orElseThrow(() -> new IllegalStateException("That account does not exist"));
    }
}
