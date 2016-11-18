package gut.follower.org.Models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Track {

    @Id
    private String id;

    private String accountId;

    private boolean finished;

    private String activity;

    private Double avgSpeed = 0d;

    private Double runPace = 0d;

    private Double distance = 0d;

    private long startTime;

    private long finishTime;

    private List<Location> locations;

    public Track(){};

    public Track(String accountId, List<Location> locations, long startTime, String activity) {
        this.accountId = accountId;
        this.locations = locations;
        this.startTime = startTime;
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Double getRunPace() {
        return runPace;
    }

    public void setRunPace(Double runPace) {
        this.runPace = runPace;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}