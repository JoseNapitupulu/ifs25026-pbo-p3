package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.List;

public class ContactRepository extends InMemoryRepository<Contact, Integer>
        implements IContactRepository {
    private int nextId = 1;

    public ContactRepository() {
        super(Contact::getId);
    }

    public Contact save(String name, String phone, String email) {
        return save(new Contact(nextId++, name, phone, email));
    }

    @Override
    public List<Contact> findByName(String name) {
        String query = name.toLowerCase();
        return findBy(contact -> contact.getName().toLowerCase().contains(query));
    }
}
