package Application.dao;

import Application.domain.Contact;
import Application.domain.Customer;

public interface ICustomerDAO {
    public void save(Customer customer);
    public Customer find(String firstName);
}
