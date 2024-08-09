package framework;

import framework.annotations.Autowired;
import framework.annotations.Service;
import framework.annotations.Value;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Simple Java Framework
 */
public class SimpleJavaFrameworkContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    private final Properties properties = new Properties();

    public SimpleJavaFrameworkContext(Class<?> clazz) throws Exception {
        loadProperties();
        instantiateClasses(clazz);
        doDependencyInjection();
    }

    private void instantiateClasses(Class<?> clazz) {
        scanServices(clazz);
    }

    private void scanServices(Class<?> clazz) {
        try {
            Reflections reflections = new Reflections(clazz.getPackageName());
            Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);
            for (Class<?> serviceClass : serviceClasses) {
                Object obj = serviceClass.getDeclaredConstructor().newInstance();
                beans.put(serviceClass, obj);
            }

            Object applicationInstance = clazz.getDeclaredConstructor().newInstance();
            beans.put(clazz, applicationInstance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(beans);
    }

    private void doDependencyInjection() throws IllegalAccessException {
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = beans.get(field.getType());
                    field.set(bean, dependency);
                } else if (field.isAnnotationPresent(Value.class)) {
                    String val = properties.getProperty(field.getAnnotation(Value.class).value());
                    field.set(bean, convertToFieldType(field.getType(), val));
                }
            }
        }
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Object convertToFieldType(Class<?> fieldType, String value) {
        if (fieldType == int.class || fieldType == Integer.class) {
            return Integer.parseInt(value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return Long.parseLong(value);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}
