package Application;

import framework.SimpleJavaFramework;
import framework.SimpleJavaFrameworkContext;
import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;

@Service
public class Main implements Runnable {
    @Autowired
    @Qualifier("Samsung")
    private IProduct iProduct;

    @Autowired
    private  ITestConstructor testConstrcutor;



    public  static void main(String[] args) throws Exception {
        new SimpleJavaFramework().run(Main.class,args) ;
    }

    public void run() {
     //  iProduct.dispaly();
        System.out.println("Test......");
        testConstrcutor.test();
    }
}
