package domain.repository;

import domain.entity.Contact;
import java.util.List;

public interface IContactRepository extends IRepository<Contact, Integer> {
    Contact save(String name, String phone, String email);

    List<Contact> findByName(String name);
}
