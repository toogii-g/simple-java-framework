package framework;

import framework.annotation.Autowired;
import framework.annotation.Value;
import framework.util.FieldTypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

public class DependencyInjector {

    FieldTypeConverter converter = new FieldTypeConverter();

    private final Properties properties = new Properties();


    public void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void doDependencyInjection(Map<Class<?>, Object> beans) throws IllegalAccessException {
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object dependency = beans.get(field.getType());
                    field.set(bean, dependency);
                } else if (field.isAnnotationPresent(Value.class)) {
                    String val = properties.getProperty(field.getAnnotation(Value.class).value());
                    field.set(bean, converter.convertToFieldType(field.getType(), val));
                }
            }
        }
    }
}
