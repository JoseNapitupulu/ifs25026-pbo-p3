package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

import java.util.List;

public class FinanceView {
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;
    private final InputUtil input;

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
        this.input = new InputUtil();
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showDashboard(useCase.list(), useCase.getBalance());
            showMenu();
            String choice = input.readLine("Pilih : ").trim();
            presenter.showMessage("");
            try {
                switch (choice.toLowerCase()) {
                    case "1":
                        addIncome();
                        break;
                    case "2":
                        addExpense();
                        break;
                    case "3":
                        search();
                        break;
                    case "4":
                        sort();
                        break;
                    case "5":
                        presenter.showBalance(useCase.getBalance());
                        break;
                    case "6":
                        delete();
                        break;
                    case "x":
                        running = false;
                        break;
                    default:
                        presenter.showMessage("[!] Pilihan tidak dimengerti.");
                        presenter.showMessage("");
                }
            } catch (ValidationException exception) {
                presenter.showMessage("[!] " + exception.getMessage());
                presenter.showMessage("");
            } catch (EntityNotFoundException exception) {
                presenter.showMessage("[!] Gagal menghapus transaksi dengan ID: " + lastId + ".");
                presenter.showMessage("");
            }
        }
    }

    private int lastId;

    private void showMenu() {
        presenter.showMessage("Menu:");
        presenter.showMessage("1. Tambah Pemasukan");
        presenter.showMessage("2. Tambah Pengeluaran");
        presenter.showMessage("3. Cari");
        presenter.showMessage("4. Urutkan");
        presenter.showMessage("5. Lihat Saldo");
        presenter.showMessage("6. Hapus");
        presenter.showMessage("x. Keluar");
    }

    private void addIncome() throws ValidationException {
        presenter.showMessage("[Tambah Pemasukan]");
        String description = input.readLine("Keterangan (x Jika Batal) : ");
        if (description.trim().equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        long amount = readAmount();
        Transaction transaction = useCase.addIncome(description, amount);
        presenter.showAdded(transaction, useCase.getBalance());
    }

    private void addExpense() throws ValidationException {
        presenter.showMessage("[Tambah Pengeluaran]");
        String description = input.readLine("Keterangan (x Jika Batal) : ");
        if (description.trim().equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        long amount = readAmount();
        Transaction transaction = useCase.addExpense(description, amount);
        presenter.showAdded(transaction, useCase.getBalance());
    }

    private long readAmount() throws ValidationException {
        String value = input.readLine("Jumlah : ").trim();
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new ValidationException("Jumlah tidak valid!");
        }
    }

    private void search() throws ValidationException {
        presenter.showMessage("[Cari Transaksi]");
        String query = input.readLine("Kata Kunci (x Jika Batal) : ");
        if (query.trim().equalsIgnoreCase("x")) {
            return;
        }
        List<Transaction> results = useCase.search(query);
        presenter.showSearchResult(query, results);
    }

    private void sort() {
        presenter.showMessage("[Urutkan Transaksi]");
        presenter.showMessage("1. Jumlah (Terkecil)");
        presenter.showMessage("2. Jumlah (Terbesar)");
        presenter.showMessage("3. Pemasukan Dulu");
        presenter.showMessage("4. Pengeluaran Dulu");
        presenter.showMessage("x. Batal");
        String value = input.readLine("Pilih : ").trim();
        presenter.showMessage("");
        if (value.equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        SortOption option;
        switch (value) {
            case "1": option = SortOption.AMOUNT_ASC; break;
            case "2": option = SortOption.AMOUNT_DESC; break;
            case "3": option = SortOption.INCOME_FIRST; break;
            case "4": option = SortOption.EXPENSE_FIRST; break;
            default:
                presenter.showMessage("[!] Pilihan tidak valid!");
                presenter.showMessage("");
                return;
        }
        presenter.showSortedResult(useCase.sort(option), useCase.getBalance());
    }

    private void delete() throws EntityNotFoundException {
        presenter.showMessage("[Hapus Transaksi]");
        String value = input.readLine("ID Transaksi (x Jika Batal) : ").trim();
        if (value.equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        try {
            lastId = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            presenter.showMessage("[!] ID tidak valid!");
            presenter.showMessage("");
            return;
        }
        useCase.delete(lastId);
        presenter.showDeleted();
        presenter.showMessage("");
    }
}