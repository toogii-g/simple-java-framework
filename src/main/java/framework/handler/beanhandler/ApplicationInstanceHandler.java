package framework.handler.beanhandler;

import java.util.Map;

public class ApplicationInstanceHandler implements BeanHandler{
    @Override
    public void handle(Map<Class<?>, Object> beans, Class<?> clazz, String activeProfile) throws Exception {
        Object applicationInstance = clazz.getDeclaredConstructor().newInstance();
        beans.put(clazz, applicationInstance);
    }
}
