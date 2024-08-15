package framework.util;

import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;
import framework.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

public class DependencyInjector {

    FieldTypeConverter converter = new FieldTypeConverter();
    private final Properties properties = new Properties();
    private String activeProfile;

    public void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
                activeProfile = properties.getProperty("profiles.active", "default");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getActiveProfile() {
        return activeProfile;
    }

    public void doDependencyInjection(Map<Class<?>, Object> beans) throws Exception {
        for (Object bean : beans.values()) {
            System.out.println("Injecting dependencies for bean: " + bean.getClass().getName());
            injectConstructorDependencies(bean, beans);
            injectFieldDependencies(bean, beans);
            injectSetterDependencies(bean, beans);
        }
    }

    private void injectConstructorDependencies(Object bean, Map<Class<?>, Object> beans) throws Exception {
        Constructor<?>[] constructors = bean.getClass().getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] dependencies = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    dependencies[i] = findDependency(parameterTypes[i], beans);
                }
                constructor.setAccessible(true);
                Object newBean = constructor.newInstance(dependencies);
                beans.put(bean.getClass(), newBean);
                return;
            }
        }
    }

    private void injectFieldDependencies(Object bean, Map<Class<?>, Object> beans) throws IllegalAccessException {
        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = findDependency(field, beans);
                if (dependency == null) {
                    System.err.println("Failed to inject field: " + field.getName() + " in bean: " + bean.getClass().getName());
                } else {
                    System.out.println("Injecting field dependency: " + dependency.getClass().getName() + " into " + bean.getClass().getName());
                    field.set(bean, dependency);
                }
            } else if (field.isAnnotationPresent(Value.class)) {
                String val = properties.getProperty(field.getAnnotation(Value.class).value());
                field.set(bean, converter.convertToFieldType(field.getType(), val));
            }
        }
    }

    private void injectSetterDependencies(Object bean, Map<Class<?>, Object> beans) throws Exception {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowired.class) && method.getName().startsWith("set")) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    Object dependency = findDependency(parameterTypes[0], beans);
                    method.setAccessible(true);
                    method.invoke(bean, dependency);
                }
            }
        }
    }

    private Object findDependency(Class<?> type, Map<Class<?>, Object> beans) {
        for (Class<?> beanClass : beans.keySet()) {
            if (type.isAssignableFrom(beanClass)) {
                return beans.get(beanClass);
            }
        }
        return null;
    }

    private Object findDependency(Field field, Map<Class<?>, Object> beans) {
        if (field.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = field.getAnnotation(Qualifier.class).value();
            return findQualifiedDependency(beans, field.getType(), qualifierValue);
        } else {
            return findDependency(field.getType(), beans);
        }
    }

    private Object findQualifiedDependency(Map<Class<?>, Object> beans, Class<?> type, String qualifierValue) {
        for (Class<?> beanClass : beans.keySet()) {
            if (type.isAssignableFrom(beanClass) && beanClass.getSimpleName().equals(qualifierValue)) {
                return beans.get(beanClass);
            }
        }
        return null;
    }
}
