package adapter.repository;
import domain.exception.EntityNotFoundException;
import domain.repository.IRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {
    private final Map<ID, T> records = new LinkedHashMap<>();
    public List<T> findAll() { return new ArrayList<>(records.values()); }
    public Optional<T> findById(ID id) { return Optional.ofNullable(records.get(id)); }
    public List<T> findBy(Predicate<T> predicate) { List<T> result = new ArrayList<>(); for (T item : records.values()) if (predicate.test(item)) result.add(item); return result; }
    protected T saveWithId(ID id, T entity) { records.put(id, entity); return entity; }
    public void deleteById(ID id) throws EntityNotFoundException { if (!records.containsKey(id)) throw new EntityNotFoundException("Activity dengan ID " + id + " tidak ditemukan."); records.remove(id); }
}