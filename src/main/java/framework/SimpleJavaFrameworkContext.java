package framework;

import framework.event.ApplicationEventPublisher;
import framework.event.SimpleEventPublisher;
import framework.util.BeanScanner;
import framework.util.DependencyInjector;
import framework.util.Scheduler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Java Framework
 */
public class SimpleJavaFrameworkContext {

    private Map<Class<?>, Object> beans = new HashMap<>();
    private final Scheduler scheduler = new Scheduler();
    DependencyInjector dependencyInjector = new DependencyInjector();
    private final SimpleEventPublisher eventPublisher = SimpleEventPublisher.getSimpleEventPublisher();
    BeanScanner beanScanner;
    public SimpleJavaFrameworkContext(Class<?> clazz) throws Exception {
        dependencyInjector.loadProperties();
        String activeProfile = dependencyInjector.getActiveProfile();
        beanScanner = new BeanScanner(activeProfile);
        instantiateClasses(clazz,activeProfile);
        dependencyInjector.doDependencyInjection(beans);
        scheduleTasks();
        registerEventListeners();
    }


    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }
    private void scheduleTasks() {
        for (Object bean : beans.values()) {
            scheduler.schedule(bean);
        }
    }
    // Make sure to call this method when shutting down the application
    public void shutdown() {
        scheduler.shutdown();
    }

    private void instantiateClasses(Class<?> clazz, String activeProfile) {


        beans = beanScanner.scanBeans(clazz);
        // Ensure to scan all relevant packages
        beans.put(SimpleEventPublisher.class, SimpleEventPublisher.getSimpleEventPublisher());
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
    private void registerEventListeners() {
        for (Object bean : beans.values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(framework.annotation.EventListener.class)) { // Use the fully qualified name
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        Class<?> eventType = parameterTypes[0];
                        ((SimpleEventPublisher) eventPublisher).registerListener(bean, method, eventType);
                    }
                }
            }
        }
    }

}
