package framework.handler.beanhandler;

import java.util.Map;

public interface BeanHandler {

    void handle(Map<Class<?>, Object> beans, Class<?> clazz) throws Exception;
}
