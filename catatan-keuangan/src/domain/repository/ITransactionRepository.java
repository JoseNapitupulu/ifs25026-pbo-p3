package domain.repository;

import domain.entity.Transaction;

import java.util.List;

public interface ITransactionRepository extends IRepository<Transaction, Integer> {
    List<Transaction> findByDescription(String description);
}