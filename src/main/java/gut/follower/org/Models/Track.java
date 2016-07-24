package gut.follower.org.Models;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 *
 */
public class Track {

    @Id
    private String id;

    private List<Location> locations;

    public Track(List<Location> locations) {
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}