package gut.follower.org.Utils;

import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;

import java.util.List;

public class TrackUtil {

    public static Double calculateAvgSpeed(Track track) {
        long time = track.getFinishTime() - track.getStartTime() ;
        Double distance = track.getDistance();
        double avgSpeed = (distance * 3600000) / time;
        return avgSpeed;
    }

    public static Double calculateDistance(List<Location> locationList) {
        Double distance = 0d;
        Location lastLocation = null;
        for(Location location: locationList){
            if(lastLocation != null){
                distance += location.getDistnace(lastLocation);
                lastLocation = location;
            } else {
                lastLocation = location;
            }
        }
        return distance;
    }

    public static Double calculateRunPace(Track track){
        long time = track.getFinishTime() - track.getStartTime() ;
        Double distance = track.getDistance();
        double rate = time / (distance * 60000);
        return rate;
    }
}
