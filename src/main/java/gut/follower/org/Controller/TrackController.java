package gut.follower.org.Controller;


import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;
import gut.follower.org.Repositories.AccountRepository;
import gut.follower.org.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("/rest/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Track postTrack(Principal principal, @RequestBody Map<String, String> map) {

        List<Location> locations = new LinkedList<>();
        Location location = new Location(Long.parseLong(map.get("latitude")), Long.parseLong(map.get("longitude")));
        locations.add(location);

        String accountId = accountRepository.findByUsername(principal.getName()).getId();

        Track track = new Track(accountId, locations);

        return trackRepository.save(track);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Track addLocation(Principal principal, @RequestBody Map<String, String> map, @PathVariable String id) {
        Track track = trackRepository.findByAccountIdAndId(accountRepository.findByUsername(principal.getName()).getId(), id);

        if (!track.getFinished()) {
            Location location = new Location(Long.parseLong(map.get("latitude")), Long.parseLong(map.get("longitude")));
            track.getLocations().add(location);
            track.setFinished(Boolean.valueOf(map.get("finished")));
            return trackRepository.save(track);
        } else {
            throw new IllegalStateException("This track is finished");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getTracks(Principal principal) {
        return trackRepository.findByAccountId(accountRepository.findByUsername(principal.getName()).getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getTrack(Principal principal, @PathVariable String id) {
        return trackRepository.findByAccountIdAndId(accountRepository.findByUsername(principal.getName()).getId(), id);
    }
}
