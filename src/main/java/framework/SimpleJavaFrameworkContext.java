package framework;

import framework.interfaces.Autowired;
import framework.interfaces.Service;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple Java Framework
 */
public class SimpleJavaFrameworkContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();


    public SimpleJavaFrameworkContext(Class<?> clazz)throws Exception{
        instantiateClasses(clazz);
        doDependencyInjection();
    }

    private void instantiateClasses(Class<?> clazz){
        scanServices(clazz);
    }

    private void scanServices(Class<?> clazz){
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
            for (Class<?> serviceClass : serviceClasses) {
                System.out.println(serviceClass.getName());
                Object obj = serviceClass.getDeclaredConstructor().newInstance();
                beans.put(serviceClass, obj);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void doDependencyInjection() throws IllegalAccessException{
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Object dependency = beans.get(field.getType());
                    field.set(bean, dependency);
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}
