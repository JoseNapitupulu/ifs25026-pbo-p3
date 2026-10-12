package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;

public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public Contact add(String name, String phone, String email) throws ValidationException {
        validate(name, "Nama");
        validate(phone, "Nomor telepon");
        validate(email, "Email");
        if (!email.trim().contains("@")) {
            throw new ValidationException("Email harus memiliki format yang valid.");
        }
        return repository.save(name.trim(), phone.trim(), email.trim());
    }

    public Contact update(int id, String name, String phone, String email)
            throws ValidationException, EntityNotFoundException {
        Contact contact = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Kontak dengan ID " + id
                        + " tidak ditemukan."));
        if (!name.trim().isEmpty()) {
            validate(name, "Nama");
            contact.changeName(name.trim());
        }
        if (!phone.trim().isEmpty()) {
            validate(phone, "Nomor telepon");
            contact.changePhone(phone.trim());
        }
        if (!email.trim().isEmpty()) {
            validate(email, "Email");
            contact.changeEmail(email.trim());
        }
        repository.update(contact);
        return contact;
    }

    public List<Contact> search(String name) throws ValidationException {
        validate(name, "Nama");
        return repository.findByName(name.trim());
    }

    public List<Contact> sort(SortOption option) {
        List<Contact> result = new ArrayList<>(repository.findAll());
        result.sort(option.getComparator().thenComparing(Contact::getId));
        return result;
    }

    public List<Contact> list() {
        return repository.findAll();
    }

    public void delete(int id) throws EntityNotFoundException {
        repository.deleteById(id);
    }

    private void validate(String value, String label) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(label + " tidak boleh kosong.");
        }
    }
}
