package Application.dao;

import Application.domain.Contact;
import Application.domain.Customer;
import framework.annotation.Service;

import java.util.HashMap;

@Service
public class CustomerDAO implements ICustomerDAO{
    private HashMap<String, Customer> customerHashMap = new HashMap<>();

    public void save(Customer customer){
        System.out.println("CustomerDAO: DB customer added");
        customerHashMap.put(customer.getFirstName(), customer);
    }

    public Customer find(String firstName){
        return customerHashMap.get(firstName);
    }
}
