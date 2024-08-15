package Application;

import Application.domain.Contact;
import Application.service.ContactService;
import Application.service.IContactService;
import Application.service.ICustomerService;
import framework.SimpleJavaFramework;
import framework.SimpleJavaFrameworkContext;
import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;
import framework.event.ApplicationEventPublisher;

import framework.util.Runnable;

public class Application implements Runnable {
    @Autowired
    private IContactService iContactService;
    @Autowired
    private ICustomerService iCustomerService;

    public  static void main(String[] args) throws Exception {
        SimpleJavaFramework.run(Application.class,args) ;
    }

    @Override
    public void run(String... args) {
        iContactService.addContact("012-345-678", "johndoe@miu.edu");
        iCustomerService.addCustomer("John", "Dow");

    }

}
