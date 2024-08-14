package Application.dao;

import Application.domain.Customer;

import java.util.HashMap;

public class MockCustomerDAO implements ICustomerDAO{
    private HashMap<String, Customer> customerHashMap = new HashMap<>();

    public void save(Customer customer){
        System.out.println("MockCustomerDAO: DB customer added");
        customerHashMap.put(customer.getFirstName(), customer);
    }

    public Customer find(String firstName){
        return customerHashMap.get(firstName);
    }
}
