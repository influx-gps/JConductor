package gut.follower.org.KalmanConnection;

import gut.follower.org.Models.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Optional;


public class Kalman {

    private final static String BaseKalmanAddress = "http://localhost:9099/kalman";
    private static JSONParser parser = new JSONParser();


    public static Location filter(Location location, String trackId, String state) {
        validateLocation(location);
        String filterUrl = buildKalmanAddress(trackId);
        String payload = prepareRequestBody(location, state);
        return makeRequestToFilter(payload, filterUrl, location);
    }

    public static String prepareRequestBody(Location location, String state){
        return String.format("{\"latitude\":%f" +
                        ",\"longitude\":%f" +
                        ",\"position\":\"%s\"}",
                location.getLatitude(),
                location.getLongitude(),
                state);
    }

    public static void validateLocation(Location location){
        Optional.ofNullable(location.getLatitude())
                .orElseThrow(() -> new IllegalStateException("You need to specify latitude in this request"));
        Optional.ofNullable(location.getLongitude())
                .orElseThrow(() -> new IllegalStateException("You need to specify longitude in this request"));
        Optional.ofNullable(location.getTime())
                .orElseThrow(() -> new IllegalStateException("You need to specify time in this request"));
    }

    private static Location makeRequestToFilter(String payload, String filterUrl, Location location){
        String response = RequestCreator.sendPostRequest(filterUrl, payload);
        try {
            Object obj = parser.parse(response);
            JSONObject data = (JSONObject) obj;
            Float longitude = Float.parseFloat((String)data.get("longitude"));
            Float latitude = Float.parseFloat((String)data.get("latitude"));
            return new Location(latitude, longitude, location.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException("Data parsing error");
        }
    }

    private static String buildKalmanAddress(String trackId){
        return String.format("%s/%s", BaseKalmanAddress, trackId);
    }

}
