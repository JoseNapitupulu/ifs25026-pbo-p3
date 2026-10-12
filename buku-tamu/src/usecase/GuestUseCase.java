package usecase;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IGuestRepository;

import java.util.List;

public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public Guest register(String name, String purpose) throws ValidationException {
        validateText(name, "Nama tamu");
        validateText(purpose, "Tujuan kunjungan");
        return repository.save(new Guest(null, name.trim(), purpose.trim()));
    }

    public List<Guest> search(String name) throws ValidationException {
        validateText(name, "Nama tamu");
        return repository.findByName(name.trim());
    }

    public List<Guest> list() {
        return repository.findAll();
    }

    public void delete(int id) throws EntityNotFoundException {
        repository.deleteById(id);
    }

    private void validateText(String value, String field) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(field + " tidak boleh kosong.");
        }
    }
}