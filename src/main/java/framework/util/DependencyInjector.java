package framework.util;

import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;
import framework.annotation.Value;
import framework.util.FieldTypeConverter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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



//    public void doDependencyInjection(Map<Class<?>, Object> beans) throws IllegalAccessException {
//>>>>>>> 343024ffd3c95b7c714f40c83079b63a2b2baeb1
        for (Object bean : beans.values()) {
            System.out.println("wtf is happening: class: "+bean.getClass()+ " "+beans.get(bean.getClass()));
            injectConstructorDependencies(beans.get(bean.getClass()), beans);
            System.out.println(beans.get(bean.getClass()));
            injectFieldDependencies(beans.get(bean.getClass()), beans);
            injectSetterDependencies(beans.get(bean.getClass()), beans);
        }


//        for (Object bean : beans.values()) {
//            for (Field field : bean.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                if (field.isAnnotationPresent(Autowired.class)) {
//                    Object dependency = beans.get(field.getType());
//                    field.set(bean, dependency);
//                } else if (field.isAnnotationPresent(Value.class)) {
//                    String val = properties.getProperty(field.getAnnotation(Value.class).value());
//                    field.set(bean, converter.convertToFieldType(field.getType(), val));
//                }
//            }
//        }
        }
    private void injectConstructorDependencies(Object bean, Map<Class<?>, Object> beans) throws Exception {
        System.out.println("starting constructor injection for bean="+bean.getClass());
        Constructor<?>[] constructors = bean.getClass().getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("inside constructor forloop constructor:"+constructor.getName());
            if (constructor.isAnnotationPresent(Autowired.class)) {
                System.out.println("constructor: is annotaton autowired present");
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] dependencies = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    dependencies[i] = findDependency(parameterTypes[i], beans);
                }
                constructor.setAccessible(true);
                System.out.println("====>Dependencies="+dependencies.getClass());
                Object newBean = constructor.newInstance(dependencies);
                System.out.println("Constructor created object: "+newBean);
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
                System.out.println("injectFieldDependencies=====" + field.getType() + " "+dependency +" the bean"+bean);
                field.set(bean, dependency);
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
        if(type.isInterface()){
        for(Class beanClass: beans.keySet()){
            Class<?>[] interfaces = beanClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if(anInterface.getName().equals(type.getName())){
                    return beans.get(beanClass);
                }
            }
        }
        } else{
            for (Class<?> beanClass : beans.keySet()) {
                if (type.isAssignableFrom(beanClass)) {
                    return beans.get(beanClass);
                }
            }
        }
        return null;
    }

    private Object findDependency(Field field, Map<Class<?>, Object> beans) {
        if (field.isAnnotationPresent(Qualifier.class)) {
            System.out.println("Qualifier=====" + field.getName() +" "+field.getType());
            String qualifierValue = field.getAnnotation(Qualifier.class).value();
            //findQualifiedDependency(beans, interface, className)
            Object ret =  findQualifiedDependency(beans, field.getType(), qualifierValue);
            System.out.println(ret);
            return ret;
        } else {
            return findDependency(field.getType(), beans);
        }
    }

    private Object findQualifiedDependency(Map<Class<?>, Object> beans, Class<?> type, String qualifierValue) {
        for(Class beanClass: beans.keySet()){
            Class<?>[] interfaces = beanClass.getInterfaces();
           for (Class<?> anInterface : interfaces) {
                if(anInterface.getName().equals(type.getName()) && beanClass.getSimpleName().equals(qualifierValue)){
                    return beans.get(beanClass);
                }
            }
        }
        return null;
//        Optional<Object> qualifiedBean = beans.values().stream()
//                .filter(bean -> type.isAssignableFrom(bean.getClass()) && bean.getClass().isAnnotationPresent(Service.class))
//                .filter(bean -> {
//                    Qualifier qualifier = bean.getClass().getAnnotation(Qualifier.class);
//                    return qualifier != null && qualifier.value().equals(qualifierValue);
//                })
//                .findFirst();
//
//        return qualifiedBean.orElseThrow(() -> new RuntimeException(
//                "No qualifying bean of type " + type.getName() + " with qualifier '" + qualifierValue + "' found"));
    }

}

