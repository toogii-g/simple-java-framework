package Application.Event;
public class CustomEvent {
    private final String message;

    public CustomEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}