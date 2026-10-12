package adapter.repository;

import domain.entity.Transaction;
import domain.exception.EntityNotFoundException;
import domain.repository.ITransactionRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionRepository extends InMemoryRepository<Transaction, Integer>
        implements ITransactionRepository {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    public TransactionRepository() {
        super(
            () -> NEXT_ID.getAndIncrement(),
                Transaction::getId,
                (transaction, id) -> new Transaction(
                        id,
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getType()));
    }

    @Override
    public List<Transaction> findByDescription(String description) {
        String query = description.toLowerCase();
        return findBy(transaction -> transaction.getDescription().toLowerCase().contains(query));
    }
}