package gut.follower.org.RestSecurity;

import gut.follower.org.Models.Account;
import gut.follower.org.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(name);
        if(account == null){
            throw new UsernameNotFoundException(name);
        }else {
            return new User(account.getUsername(), account.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
        }
    }
}
