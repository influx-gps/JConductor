package gut.follower.org.Repositories;

import gut.follower.org.Models.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track, String>{
}
