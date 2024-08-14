package framework;

import framework.util.Runnable;

public class SimpleJavaFramework {
    public static SimpleJavaFrameworkContext run(Class<?> applicationClass, String... args) throws Exception {
        SimpleJavaFrameworkContext context = new SimpleJavaFrameworkContext(applicationClass);

        return context;
    }
}
