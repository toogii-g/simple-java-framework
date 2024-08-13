package framework.event;

import java.lang.reflect.Method;
import java.util.*;

public class SimpleEventPublisher implements ApplicationEventPublisher {
    private final Map<Class<?>, Set<Method>> eventListeners = new HashMap<>();

    @Override
    public void publishEvent(Event event) {
        Set<Method> listeners = eventListeners.get(event.getClass());
        if (listeners != null) {
            for (Method method : listeners) {
                try {
                    method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerListener(Class<?> eventType, Method method, Object bean) {
        eventListeners.computeIfAbsent(eventType, k -> new HashSet<>()).add(method);
    }
}
