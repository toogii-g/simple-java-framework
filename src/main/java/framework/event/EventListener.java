package framework.event;


// Interface for event listeners
public interface EventListener<E extends Event> {
    void onEvent(E event);
}
