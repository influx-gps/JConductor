package gut.follower.org.Models;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 *
 */
public class Track {

    @Id
    private String id;

    private Account account;

    private List<Location> locations;

    public Track(Account account, List<Location> locations) {
        this.account = account;
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}