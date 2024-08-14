package framework.handler.beanhandler;

import framework.annotation.Profile;
import framework.annotation.Service;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ServiceScanHandler implements BeanHandler{

    @Override
    public void handle(Map<Class<?>, Object> beans, Class<?> clazz, String activeProfile) throws Exception {
        Reflections reflections = new Reflections(clazz.getPackageName());
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);

        for (Class<?> serviceClass : serviceClasses) {
            if (isActiveProfile(serviceClass, activeProfile)) {
                Object obj = serviceClass.getDeclaredConstructor().newInstance();
                beans.put(serviceClass, obj);
            }
        }
    }

    private boolean isActiveProfile(Class<?> clazz, String activeProfile) {
        Profile profileAnnotation = clazz.getAnnotation(Profile.class);
        if (profileAnnotation != null) {
            return profileAnnotation.value().equals(activeProfile);
        }
        return true;
    }
}

