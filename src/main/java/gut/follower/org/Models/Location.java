package gut.follower.org.Models;

public class Location {

    private double latitude;
    private double longitude;
    private long time;

    private Location(){}

    public Location(double latitude, double longitude, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Double getDistnace(Location location){
        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(this.latitude - location.getLatitude());
        Double lonDistance = Math.toRadians(this.longitude - location.getLongitude());
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location.getLatitude())) * Math.cos(Math.toRadians(this.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; //output in km

        return distance;
    }
}