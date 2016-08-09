package gut.follower.org.Controller;


import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;
import gut.follower.org.Repositories.AccountRepository;
import gut.follower.org.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Track postTrack(Principal principal, @RequestBody Map<String, Double> map) {

        List<Location> locations = new LinkedList<>();
        Location location = new Location(map.get("latitude"), map.get("longitude"), map.get("time").longValue());
        locations.add(location);

        String accountId = accountRepository.findByUsername(principal.getName()).getId();

        Track track = new Track(accountId, locations, location.getTime());

        return trackRepository.save(track);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Track addLocation(Principal principal,
                             @RequestBody Map<String, Double> map,
                             @PathVariable String id,
                             @RequestParam(name = "finished", defaultValue = "false") boolean finished) {
        Track track = trackRepository.findByAccountIdAndId(accountRepository.findByUsername(principal.getName()).getId(), id);

        if (!track.getFinished()) {
            Location location = new Location(map.get("latitude"), map.get("longitude"), map.get("time").longValue());
            track.getLocations().add(location);
            track.setFinished(finished);
            track.setFinishTime(location.getTime());
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
