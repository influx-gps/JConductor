package gut.follower.org.Controller;


import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;
import gut.follower.org.Repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 */
@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private TrackRepository repository;

    @RequestMapping(method = RequestMethod.POST)
    public Track postTrack(@RequestBody Map<String, Long> map) {

        List<Location> locations = new LinkedList<>();
        Location location = new Location(map.get("latitude"), map.get("longitude"));
        locations.add(location);
        Track track = new Track(locations);

        repository.save(track);
        return track;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Track addLocation(@RequestBody Map<String, Long> map, @PathVariable String id) {
        Track track = repository.findOne(id);
        Location location = new Location(map.get("latitude"), map.get("longitude"));
        track.getLocations().add(location);
        repository.save(track);
        return track;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getTracks() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getTrack(@PathVariable String id) {
        return repository.findOne(id);
    }
}
