package Application.service;

import Application.dao.IContactDAO;
import Application.domain.Contact;
import Application.integration.ILogger;
import framework.annotation.Autowired;
import framework.annotation.Service;

@Service
public class ContactService implements IContactService{
    @Autowired
    private IContactDAO iContactDAO;

    private ILogger iLogger;

    public ContactService(){}

    @Autowired
    public ContactService(ILogger iLogger){
        this.iLogger = iLogger;
    }

    public void addContact(String phone, String email){
        iContactDAO.save(new Contact(phone, email));
        iLogger.log("Contact created");
    }
}
