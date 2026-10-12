package adapter.repository;

import domain.exception.EntityNotFoundException;
import domain.repository.IRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class InMemoryRepository<T, ID> implements IRepository<T, ID> {
    private final Map<ID, T> records = new LinkedHashMap<>();
    private final Supplier<ID> idGenerator;
    private final Function<T, ID> idExtractor;
    private final BiFunction<T, ID, T> idAssigner;

    protected InMemoryRepository(
            Supplier<ID> idGenerator,
            Function<T, ID> idExtractor,
            BiFunction<T, ID, T> idAssigner) {
        this.idGenerator = idGenerator;
        this.idExtractor = idExtractor;
        this.idAssigner = idAssigner;
    }

    @Override
    public T save(T entity) {
        ID id = idExtractor.apply(entity);
        T entityToStore = entity;
        if (id == null) {
            id = idGenerator.get();
            entityToStore = idAssigner.apply(entity, id);
        }
        records.put(id, entityToStore);
        return entityToStore;
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
    public List<T> findBy(Predicate<T> predicate) {
        List<T> matches = new ArrayList<>();
        for (T entity : records.values()) {
            if (predicate.test(entity)) {
                matches.add(entity);
            }
        }
        return matches;
    }

    @Override
    public void deleteById(ID id) throws EntityNotFoundException {
        if (!records.containsKey(id)) {
            throw new EntityNotFoundException("Transaksi dengan ID " + id + " tidak ditemukan.");
        }
        records.remove(id);
    }
}