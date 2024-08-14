package Application.service;

import Application.dao.ICustomerDAO;
import Application.domain.Customer;
import Application.integration.ILogger;
import framework.annotation.Autowired;
import framework.annotation.Qualifier;
import framework.annotation.Service;

@Service
public class CustomerService implements ICustomerService {
//    @Autowired
//    @Qualifier("MockCustomerDAO")
//    private ICustomerDAO iCustomerDAO;
    private ILogger iLogger;

    @Autowired
    public void setILogger(ILogger iLogger) {
        this.iLogger = iLogger;
    }

    public void addCustomer(String firstName, String lastName){
        //iCustomerDAO.save(new Customer(firstName, lastName));
        iLogger.log("Customer created");
    }
}
