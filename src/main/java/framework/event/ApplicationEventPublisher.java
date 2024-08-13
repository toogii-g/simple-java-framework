package framework.event;

// Interface for event publishing
public interface ApplicationEventPublisher {
    void publishEvent(Event event);
   // <E extends Event> void addEventListener(Class<E> eventType, EventListener<E> listener);

}