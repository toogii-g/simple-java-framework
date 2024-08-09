package framework.handler.beanhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeanHandlerManager {

    private final List<BeanHandler> handlers = new ArrayList<>();

    public BeanHandlerManager() {
        handlers.add(new ServiceScanHandler());
        handlers.add(new ApplicationInstanceHandler());
    }

    public Map<Class<?>, Object> handleAll(Map<Class<?>, Object> beans, Class<?> clazz) {
        try {
            for (BeanHandler handler : handlers) {
                handler.handle(beans, clazz);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return beans;
    }
}
