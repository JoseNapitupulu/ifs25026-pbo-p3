package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.exception.DomainException;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.ITransactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FinanceUseCase {
    private final ITransactionRepository repository;

    public FinanceUseCase(ITransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addIncome(String description, long amount) throws ValidationException {
        return add(description, amount, TransactionType.INCOME);
    }

    public Transaction addExpense(String description, long amount) throws ValidationException {
        return add(description, amount, TransactionType.EXPENSE);
    }

    private Transaction add(String description, long amount, TransactionType type)
            throws ValidationException {
        validateDescription(description);
        if (amount <= 0) {
            throw new ValidationException("Jumlah tidak valid!");
        }
        return repository.save(new Transaction(null, description.trim(), amount, type));
    }

    public List<Transaction> search(String description) throws ValidationException {
        validateDescription(description);
        return repository.findByDescription(description.trim());
    }

    public List<Transaction> sort(SortOption sortOption) {
        List<Transaction> transactions = new ArrayList<>(repository.findAll());
        transactions.sort(sortOption.getComparator().thenComparing(Transaction::getId));
        return transactions;
    }

    public List<Transaction> list() {
        return repository.findAll();
    }

    public long getBalance() {
        return repository.findAll().stream()
                .mapToLong(transaction -> transaction.getType() == TransactionType.INCOME
                        ? transaction.getAmount()
                        : -transaction.getAmount())
                .sum();
    }

    public void delete(int id) throws EntityNotFoundException {
        repository.deleteById(id);
    }

    private void validateDescription(String description) throws ValidationException {
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("Keterangan tidak boleh kosong.");
        }
    }
}