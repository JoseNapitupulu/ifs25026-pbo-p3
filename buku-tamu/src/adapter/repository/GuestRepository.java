package adapter.repository;

import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.repository.IGuestRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class GuestRepository extends InMemoryRepository<Guest, Integer> implements IGuestRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Guest save(Guest guest) {
        int id = nextId.getAndIncrement();
        return saveWithId(id, new Guest(id, guest.getName(), guest.getPurpose()));
    }

    @Override
    public List<Guest> findBy(Predicate<Guest> predicate) {
        return super.findBy(predicate);
    }

    @Override
    public List<Guest> findByName(String name) {
        String query = name.toLowerCase();
        return findBy(guest -> guest.getName().toLowerCase().contains(query));
    }

    @Override
    public void deleteById(Integer id) throws EntityNotFoundException {
        super.deleteById(id);
    }
}