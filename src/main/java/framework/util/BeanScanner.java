package framework.util;

import framework.annotation.Service;
import framework.handler.beanhandler.BeanHandlerManager;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanScanner {

    public Map<Class<?>, Object> scanBeans(Class<?> clazz) {
        Map<Class<?>, Object> beans = new HashMap<>();

        try {
            BeanHandlerManager handlerManager = new BeanHandlerManager();
            return handlerManager.handleAll(beans, clazz);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(beans);

        return beans;
    }
}
