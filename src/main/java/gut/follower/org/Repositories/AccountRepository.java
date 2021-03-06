package gut.follower.org.Repositories;

import gut.follower.org.Models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String>{
    public Account findByUsername(String username);

    public Account findByEmail(String email);
}
