package Application.dao;

import Application.domain.Contact;
import Application.domain.Customer;

import java.util.HashMap;

public class CustomerDAO implements ICustomerDAO{
    private HashMap<String, Customer> customerHashMap = new HashMap<>();

    public void save(Customer customer){
        customerHashMap.put(customer.getFirstName(), customer);
    }

    public Customer find(String firstName){
        return customerHashMap.get(firstName);
    }
}
