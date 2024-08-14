package framework.handler.beanhandler;

import framework.annotation.Service;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServiceScanHandler implements BeanHandler{

    @Override
    public void handle(Map<Class<?>, Object> beans, Class<?> clazz) throws Exception {
        Reflections reflections = new Reflections(clazz.getPackageName());
        Set<Class<?>> serviceClasses = reflections.getTypesAnnotatedWith(Service.class);

        for (Class<?> serviceClass : serviceClasses) {
            try {
                Object obj = serviceClass.getDeclaredConstructor().newInstance();
                beans.put(serviceClass, obj);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        System.out.println("service class initialized well");
    }

}
