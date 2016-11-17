package gut.follower.org.KalmanConnection;


public enum State {

    START ("START"),
    CONTINUE ("CONTINUE");

    private final String name;

    State(String s) {
        name = s;
    }
}
