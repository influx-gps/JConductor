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
    public Track postTrack(Principal principal, @RequestBody Map<String, Long> map) {

        List<Location> locations = new LinkedList<>();
        Location location = new Location(map.get("latitude"), map.get("longitude"));
        locations.add(location);
        Track track = new Track(accountRepository.findByUsername(principal.getName()), locations);

        return trackRepository.save(track);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Track addLocation(Principal principal, @RequestBody Map<String, Long> map, @PathVariable String id) {
        Track track = trackRepository.findByAccountAndId(accountRepository.findByUsername(principal.getName()), id);
        Location location = new Location(map.get("latitude"), map.get("longitude"));
        track.getLocations().add(location);
        return trackRepository.save(track);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getTracks(Principal principal) {
        return trackRepository.findByAccount(accountRepository.findByUsername(principal.getName()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getTrack(Principal principal, @PathVariable String id) {
        return trackRepository.findByAccountAndId(accountRepository.findByUsername(principal.getName()), id);
    }
}
