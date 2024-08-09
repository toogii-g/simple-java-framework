package framework;

import framework.util.BeanScanner;
import framework.util.DependencyInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple Java Framework
 */
public class SimpleJavaFrameworkContext {

    private Map<Class<?>, Object> beans = new HashMap<>();

    DependencyInjector dependencyInjector = new DependencyInjector();

    BeanScanner beanScanner = new BeanScanner();
    public SimpleJavaFrameworkContext(Class<?> clazz) throws Exception {
        dependencyInjector.loadProperties();
        instantiateClasses(clazz);
        dependencyInjector.doDependencyInjection(beans);
    }

    private void instantiateClasses(Class<?> clazz) {
        beans = beanScanner.scanBeans(clazz);
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }
}
