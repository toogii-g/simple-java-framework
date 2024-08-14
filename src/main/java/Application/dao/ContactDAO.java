package Application.dao;

import Application.domain.Contact;
import framework.annotation.Service;

import java.util.HashMap;

@Service
public class ContactDAO implements IContactDAO{
    private HashMap<String, Contact> contactHashMap = new HashMap<>();

    public void save(Contact contact){
        System.out.println("ContactDAO: contact saved");
        contactHashMap.put(contact.getPhone(), contact);
    }

    public Contact find(String phone){
        return contactHashMap.get(phone);
    }
}
