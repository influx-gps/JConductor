package gut.follower.org.Kalman;


import gut.follower.org.KalmanConnection.Kalman;
import gut.follower.org.KalmanConnection.RequestCreator;
import gut.follower.org.KalmanConnection.State;
import gut.follower.org.Models.Location;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KalmanTest {

    private static final String BaseKalmanAddress = "http://localhost:9099/kalman";
    private static final Double latitude = 3.14;
    private static final String trackId = "582cadc86771a3245ea893ae";
    private static final String kalmanFullUrl = String.format("%s/%s", BaseKalmanAddress, trackId);
    private static final Double longitude = 1729.;
    private static final Long time = 123124124L;
    private static final Location location = new Location(latitude, longitude, time);

    @Mock
    private RequestCreator requestCreator;

    @Test
    public void testFilterData_allOk() {
        //given
        when(requestCreator.sendPostRequest(any(String.class))).thenReturn(getResponse());

        //when
        Location loc = Kalman.filter(location, trackId, State.START.name());

        //then
        Assert.assertEquals(loc.getLatitude(), location.getLatitude(), 0.1);
        Assert.assertEquals(loc.getLongitude(), location.getLongitude(), 0.1);
    }

    @Test
    public void convertToKalmanApiFormat_allOk() {
        Assert.assertEquals(location.convertToKalmanApiFormat(State.START.name()), getPayload(State.START));
        Assert.assertEquals(location.convertToKalmanApiFormat(State.CONTINUE.name()), getPayload(State.CONTINUE));
    }

    @Test
    public void validateLocation_allOk() {
        Location.validateLocation(location);
    }

    public static String getPayload(State state){
        return String.format("{\"latitude\": 3.140000,\"longitude\": 1729.000000,\"position\": \"%s\"}", state.name());
    }

    public static String getResponse() {
        return "{\"latitude\": 3.14,\"longitude\": 1729.\"}";
    }

}