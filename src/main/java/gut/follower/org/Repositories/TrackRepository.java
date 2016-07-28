package gut.follower.org.Repositories;

import gut.follower.org.Models.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrackRepository extends MongoRepository<Track, String>{
    public Track findByAccountIdAndId(String accountId, String Id);
    public List<Track> findByAccountId(String accountId);
}
