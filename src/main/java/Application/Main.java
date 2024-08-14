package Application;

import framework.SimpleJavaFramework;
import framework.SimpleJavaFrameworkContext;
import framework.util.Runnable;

public class Main implements Runnable {
    public  static void main(String[] args) throws Exception {
        System.out.println("Test");

        SimpleJavaFramework.run(Main.class,args) ;
    }

    @Override
    public void run(String... args) {

    }
}
