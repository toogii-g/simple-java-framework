package framework.event;

import java.lang.reflect.Method;
import java.util.*;

public class SimpleEventPublisher implements ApplicationEventPublisher {
    private final Map<Class<?>, Set<Method>> eventListeners = new HashMap<>();
    private final Map<Object, Object> listenerBeans = new HashMap<>();

    @Override
    public void publishEvent(Object event) {
        Class<?> eventType = event.getClass();
        Set<Method> listeners = eventListeners.get(eventType);
        if (listeners != null) {
            for (Method method : listeners) {
                Object bean = listenerBeans.get(method);
                try {
                    method.setAccessible(true);
                    method.invoke(bean, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerListener(Object bean, Method method, Class<?> eventType) {
        eventListeners.computeIfAbsent(eventType, k -> new HashSet<>()).add(method);
        listenerBeans.put(method, bean);
    }
}
