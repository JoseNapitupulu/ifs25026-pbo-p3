package domain.repository;

import domain.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface IRepository<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T entity);

    void deleteById(ID id) throws EntityNotFoundException;

    List<T> findBy(Predicate<T> predicate);
}