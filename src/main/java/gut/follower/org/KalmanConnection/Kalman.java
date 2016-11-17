package gut.follower.org.KalmanConnection;

import gut.follower.org.Models.Location;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Kalman {

    private final static String BaseKalmanAddress = "http://localhost:9099/kalman";
    private static JSONParser parser = new JSONParser();


    public static Location filter(Location location, String trackId, String state) {
        Location.validateLocation(location);
        String filterUrl = addTrackIdEndpoint(trackId);
        String payload = location.convertToKalmanApiFormat(state);
        return makeRequestToFilter(payload, filterUrl, location);
    }

    private static Location makeRequestToFilter(String payload, String filterUrl, Location location){
        RequestCreator requestCreator = new RequestCreator(filterUrl);
        String response = requestCreator.sendPostRequest(payload);
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

    private static String addTrackIdEndpoint(String trackId){
        return String.format("%s/%s", BaseKalmanAddress, trackId);
    }

}
