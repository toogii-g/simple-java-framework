package Application.dao;

import Application.domain.Contact;

public interface IContactDAO {
    public void save(Contact contact);
    public Contact find(String phone);
}
