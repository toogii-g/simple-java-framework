package framework.handler.beanhandler;

import framework.annotation.Async;
import framework.handler.beanhandler.BeanHandler;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncHandler implements BeanHandler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void handle(Map<Class<?>, Object> beans, Class<?> clazz, String activeProfile) throws Exception {
        for (Object bean : beans.values()) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Async.class)) {
                    wrapMethodWithAsync(bean, method);
                }
            }
        }
    }

    private void wrapMethodWithAsync(Object bean, Method method) {
        executorService.submit(() -> {
            try {
                method.setAccessible(true);
                method.invoke(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}