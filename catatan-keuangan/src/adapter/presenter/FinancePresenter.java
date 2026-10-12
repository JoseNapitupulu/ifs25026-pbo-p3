package adapter.presenter;

import domain.entity.Transaction;

import java.util.List;

public class FinancePresenter {
    public String formatTransaction(Transaction transaction, long balance) {
        return String.format(
                "[%d] %s | Rp %d | [%s]Saldo: Rp %d",
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType().getLabel(),
                balance);
    }

    public String formatTransactionWithoutBalance(Transaction transaction) {
        return String.format(
                "[%d] %s | Rp %d | [%s]",
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType().getLabel());
    }

    public void showAdded(Transaction transaction, long balance) {
        System.out.println("Berhasil menambah transaksi: " + formatTransactionWithoutBalance(transaction));
        System.out.println();
    }

    public void showDashboard(List<Transaction> transactions, long balance) {
        System.out.println("Daftar Transaksi:");
        if (transactions.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            showTransactions(transactions);
        }
        System.out.println("Saldo: Rp " + balance);
    }

    public void showSearchResult(String query, List<Transaction> transactions) {
        System.out.println("Hasil Pencarian: \"" + query + "\"");
        if (transactions.isEmpty()) {
            System.out.println("- Transaksi tidak ditemukan!");
        } else {
            showTransactions(transactions);
        }
        System.out.println();
    }

    public void showSortedResult(List<Transaction> transactions, long balance) {
        System.out.println("Daftar Transaksi (Terurut):");
        showTransactions(transactions);
        System.out.println();
    }

    public void showTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(formatTransactionWithoutBalance(transaction));
        }
    }

    public void showTransactionsWithBalance(List<Transaction> transactions, long balance) {
        for (Transaction transaction : transactions) {
            System.out.println(formatTransaction(transaction, balance));
        }
    }

    public void showBalance(long balance) {
        System.out.println("Saldo saat ini: Rp " + balance);
        System.out.println();
    }

    public void showDeleted() {
        System.out.println("Berhasil menghapus transaksi.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}