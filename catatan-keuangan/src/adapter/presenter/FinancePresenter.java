package adapter.presenter;

import domain.entity.Transaction;

import java.util.List;

public class FinancePresenter extends ConsolePresenter {
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
        printLine("Berhasil menambah transaksi: " + formatTransactionWithoutBalance(transaction));
        printBlankLine();
    }

    public void showDashboard(List<Transaction> transactions, long balance) {
        printLine("Daftar Transaksi:");
        if (transactions.isEmpty()) {
            printLine("- Belum ada transaksi!");
        } else {
            showTransactions(transactions);
        }
        printLine("Saldo: Rp " + balance);
    }

    public void showSearchResult(String query, List<Transaction> transactions) {
        printLine("Hasil Pencarian: \"" + query + "\"");
        if (transactions.isEmpty()) {
            printLine("- Transaksi tidak ditemukan!");
        } else {
            showTransactions(transactions);
        }
        printBlankLine();
    }

    public void showSortedResult(List<Transaction> transactions, long balance) {
        printLine("Daftar Transaksi (Terurut):");
        showTransactions(transactions);
        printBlankLine();
    }

    public void showTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            printLine(formatTransactionWithoutBalance(transaction));
        }
    }

    public void showTransactionsWithBalance(List<Transaction> transactions, long balance) {
        for (Transaction transaction : transactions) {
            printLine(formatTransaction(transaction, balance));
        }
    }

    public void showBalance(long balance) {
        printLine("Saldo saat ini: Rp " + balance);
        printBlankLine();
    }

    public void showDeleted() {
        printLine("Berhasil menghapus transaksi.");
    }

    public void showMessage(String message) {
        printLine(message);
    }
}