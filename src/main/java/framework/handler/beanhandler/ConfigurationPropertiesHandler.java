package framework.handler.beanhandler;

import framework.annotation.ConfigurationProperties;
import org.reflections.Reflections;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigurationPropertiesHandler implements BeanHandler {

    private final Properties properties = new Properties();

    public ConfigurationPropertiesHandler() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception e) {
            System.out.println("Could not load application.properties: " + e.getMessage());
        }
    }

    @Override
    public void handle(Map<Class<?>, Object> beans, Class<?> clazz) throws Exception {

        Reflections reflections = new Reflections(clazz.getPackageName());
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(ConfigurationProperties.class);
        for (Class<?> beanClass : serviceClasses) {
                Object obj = beanClass.getDeclaredConstructor().newInstance();
                beans.put(beanClass, obj);
                ConfigurationProperties annotation = beanClass.getAnnotation(ConfigurationProperties.class);
                String prefix = annotation.prefix();

                Field[] fields = beanClass.getDeclaredFields();
                for (Field field : fields) {
                    String propertyKey = prefix + "." + field.getName();
                    String propertyValue = properties.getProperty(propertyKey);
                    if (propertyValue != null) {
                        field.setAccessible(true);
                        field.set(obj, convertToFieldType(field.getType(), propertyValue));
                    }
            }
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
}
