package gut.follower.org.Controller;


import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;
import gut.follower.org.Repositories.AccountRepository;
import gut.follower.org.Repositories.TrackRepository;
import gut.follower.org.Utils.TrackUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/rest/track")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Track postTrack(Principal principal, @RequestBody Location location) {
        return trackRepository.save(createNewTrack(principal, location));
    }

    private Track createNewTrack(Principal principal, Location location){
        return new Track(getAccountId(principal),
                         Collections.singletonList(location),
                         System.currentTimeMillis());
    }

    private String getAccountId(Principal principal){
        return accountRepository
                .findByUsername(principal.getName())
                .getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Track addLocation(Principal principal,
                             @RequestBody Location location,
                             @PathVariable String id,
                             @RequestParam(name = "finished", defaultValue = "false") boolean finished) {
        return Optional.ofNullable(getTrackByIds(principal, id))
                .filter(track -> !track.getFinished())
                .map(track -> {
                    track.getLocations().add(location);
                    track.setDistance(TrackUtil.calculateDistance(track.getLocations()));
                    track.setFinished(finished);
                    track.setFinishTime(location.getTime());
                    track.setAvgSpeed(TrackUtil.calculateAvgSpeed(track));
                    track.setRunPace(TrackUtil.calculateRunPace(track));
                    return trackRepository.save(track);
                })
                .orElseThrow(() ->
                        new IllegalStateException("This track has been finished"));
    }

    private Track getTrackByIds(Principal principal, String trackId){
        return Optional
                .ofNullable(trackRepository
                        .findByAccountIdAndId(accountRepository
                                .findByUsername(principal.getName()).getId(), trackId))
                .orElseThrow(() ->
                        new IllegalStateException("Track does not exists"));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getTracks(Principal principal) {
        return trackRepository.findByAccountIdOrderByStartTimeDesc(accountRepository.findByUsername(principal.getName()).getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Track getTrack(Principal principal, @PathVariable String id) {
        return trackRepository.findByAccountIdAndId(accountRepository.findByUsername(principal.getName()).getId(), id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpStatus deleteTrack(Principal principal, @PathVariable String id){
        trackRepository.delete(getTrackByIds(principal, id));
        return HttpStatus.OK;
    }
}
