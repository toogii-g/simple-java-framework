package framework.event;

// Interface for event publishing
public interface ApplicationEventPublisher {
    void publishEvent(Object event);

}