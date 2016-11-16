package gut.follower.org.Repositories;

import gut.follower.org.Models.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TrackRepository extends MongoRepository<Track, String>{
    public Track findByAccountIdAndId(String accountId, String Id);
    @Query(fields = "{'locations' : 0}")
    public List<Track> findByAccountIdOrderByStartTimeDesc(String accountId);
}
