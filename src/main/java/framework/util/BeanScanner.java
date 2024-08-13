package framework.util;

import framework.annotation.Service;
import framework.handler.beanhandler.BeanHandlerManager;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanScanner {

    public Map<Class<?>, Object> scanBeans(Class<?> clazz) {
        Map<Class<?>, Object> beans = new HashMap<>();


        try {
            // Register Event Listeners
           // registerEventListeners(beans);
            BeanHandlerManager handlerManager = new BeanHandlerManager();
            return handlerManager.handleAll(beans, clazz);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(beans);

        return beans;
    }

//    private void registerEventListeners(Map<Class<?>, Object> beans) throws Exception {
//        for (Object bean : beans.values()) {
//            for (Method method : bean.getClass().getDeclaredMethods()) {
//                if (method.isAnnotationPresent((Class<? extends Annotation>) EventListener.class)) {
//                    method.setAccessible(true);
//                    // Register this method for event handling
//                    // You might need to store this mapping in the ApplicationEventPublisher or a similar structure
//                }
//            }
//        }
//    }

}
