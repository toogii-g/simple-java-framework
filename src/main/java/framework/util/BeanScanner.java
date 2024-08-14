package framework.util;

import framework.annotation.Profile;
import framework.annotation.Service;
import framework.handler.beanhandler.BeanHandlerManager;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanScanner {

    private final String activeProfile;
//        try {
//            // Register Event Listeners
//           // registerEventListeners(beans);
//            BeanHandlerManager handlerManager = new BeanHandlerManager();
//            return handlerManager.handleAll(beans, clazz);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(beans);
//
//        return beans;

    public BeanScanner(String activeProfile) {
        this.activeProfile = activeProfile;

    }

    public Map<Class<?>, Object> scanBeans(Class<?> baseClass) {
        Map<Class<?>, Object> beans = new HashMap<>();

        BeanHandlerManager handlerManager = new BeanHandlerManager();
        return handlerManager.handleAll(beans, baseClass, activeProfile);
    }

    private boolean isActiveProfile(Class<?> clazz) {
        Profile profileAnnotation = clazz.getAnnotation(Profile.class);
        if (profileAnnotation != null) {
            String profile = profileAnnotation.value();
            return profile.equals(activeProfile);
        }
        return true;
    }
}
