package gut.follower.org.UtilsTests;

import gut.follower.org.Models.Location;
import gut.follower.org.Models.Track;
import gut.follower.org.Utils.TrackUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TrackUtilTest {

    private static final Double TEST_DISTANCE = 13.5d;
    private static final long TEST_START_TIME = 1452852000000l;
    private static final long TEST_FINISH_TIME = 1452856220000l;
    private static final Double TEST_LATITUDE1 = 54.47;
    private static final Double TEST_LONGITUDE1 = 18.55;
    private static final Double TEST_LATITUDE2 = 54.48;
    private static final Double TEST_LONGITUDE2 = 18.56;


    private Track track;

    @Before
    public void setUp(){
        track = new Track();
        track.setDistance(TEST_DISTANCE);
        track.setStartTime(TEST_START_TIME);
        track.setFinishTime(TEST_FINISH_TIME);
    }

    @Test
    public void testTrackAvgSpeedCalculator(){
        Double avgSpeed = TrackUtil.calculateAvgSpeed(track);
        assertThat(avgSpeed.intValue(), is(11)); //casting to int only for comparing purposes
    }

    @Test
    public void testTrackRunPaceCalculator(){
        Double rate = TrackUtil.calculateRunPace(track);
        assertThat(rate.intValue(), is(5));
    }

    @Test
    public void testTrackDistanceCalculator(){
        Double distance = TrackUtil.calculateDistance(generateLocationList());
        assertThat(distance.intValue(), is(1));
    }

    private List<Location> generateLocationList() {
        LinkedList<Location> locations = new LinkedList<>();
        locations.add(new Location(TEST_LATITUDE1, TEST_LONGITUDE1, 11));
        locations.add(new Location(TEST_LATITUDE2, TEST_LONGITUDE2, 12));
        return locations;
    }
}
