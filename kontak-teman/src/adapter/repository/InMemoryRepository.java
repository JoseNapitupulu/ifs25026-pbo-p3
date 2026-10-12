package adapter.repository;

import domain.exception.EntityNotFoundException;
import domain.repository.IRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {
    private final Map<ID, T> records = new LinkedHashMap<>();
    private final Function<T, ID> idExtractor;

    protected InMemoryRepository(Function<T, ID> idExtractor) {
        this.idExtractor = idExtractor;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(records.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(records.get(id));
    }

    @Override
    public T save(T entity) {
        records.put(idExtractor.apply(entity), entity);
        return entity;
    }

    @Override
    public void deleteById(ID id) throws EntityNotFoundException {
        if (!records.containsKey(id)) {
            throw new EntityNotFoundException("Kontak dengan ID " + id + " tidak ditemukan.");
        }
        records.remove(id);
    }

    @Override
    public void update(T entity) {
        ID id = idExtractor.apply(entity);
        if (records.containsKey(id)) {
            records.put(id, entity);
        }
    }

    @Override
    public List<T> findBy(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T entity : records.values()) {
            if (predicate.test(entity)) {
                result.add(entity);
            }
        }
        return result;
    }
}
