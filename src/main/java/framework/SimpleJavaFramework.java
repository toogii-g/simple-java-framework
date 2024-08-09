package framework;

public class SimpleJavaFramework {
    public static void run(Class<?> applicationClass, String... args) throws Exception {
        SimpleJavaFrameworkContext context = new SimpleJavaFrameworkContext(applicationClass);
        Object applicationInstance = context.getBean(applicationClass);

        Runnable runnable= (Runnable) applicationInstance;
        runnable.run();
    }
}
