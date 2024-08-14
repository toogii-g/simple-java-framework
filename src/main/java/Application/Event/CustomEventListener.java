package Application.Event;
import framework.annotation.EventListener;
import framework.annotation.Service;

@Service
public class CustomEventListener  {

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        System.out.println("Received event: " + event.getMessage());
    }
}