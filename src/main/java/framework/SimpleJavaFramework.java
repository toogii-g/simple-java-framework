package framework;



public class SimpleJavaFramework{
    private static SimpleJavaFrameworkContext context;
    public void run(Class<? extends Runnable> applicationClass, String... args){
        try{
//            context = new SimpleJavaFrameworkContext(applicationClass);
//            Object applicationInstance = context.getBean(applicationClass);
//
//            Runnable runnable= (Runnable) applicationInstance;
//            runnable.run(args);
            context = new SimpleJavaFrameworkContext(applicationClass);
            Runnable runnable = context.getBean(applicationClass);
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static SimpleJavaFrameworkContext getContext() {
        return context;
    }
}
