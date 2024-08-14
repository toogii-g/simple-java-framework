package framework;


import framework.util.Runnable;

public class SimpleJavaFramework{
    public static SimpleJavaFrameworkContext run(Class<?> applicationClass, String... args) throws Exception {
        SimpleJavaFrameworkContext context = new SimpleJavaFrameworkContext(applicationClass);
        //get the bean corresponding to the application class
        Object applicationInstance = context.getBean(applicationClass);
        Runnable runnable= (Runnable) applicationInstance;
        runnable.run(args);
        return context;
    }
}
