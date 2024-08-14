package Application;

import Application.Event.CustomEvent;
import framework.SimpleJavaFramework;
import framework.SimpleJavaFrameworkContext;
import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;
import framework.event.ApplicationEventPublisher;

@Service
public class Main implements Runnable {
    @Autowired
    @Qualifier("Samsung")
    private IProduct iProduct;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private  ITestConstructor testConstrcutor;



    public  static void main(String[] args) throws Exception {
        new SimpleJavaFramework().run(Main.class,args) ;
    }

    public void run() {
       //Testing @Qualifier
        iProduct.dispaly();
        //Testing Event
        // Publish a custom event
       // eventPublisher.publishEvent(new CustomEvent("Hello from Custom Event!"));
//        System.out.println("Test......");
       // testConstrcutor.test();
    }
}
