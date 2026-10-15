package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.ItemUseCase;
import java.util.List;

public class ItemView {
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;
    private final InputUtil input = new InputUtil();

    public ItemView(ItemUseCase useCase, ItemPresenter presenter) { this.useCase = useCase; this.presenter = presenter; }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showDashboard(useCase.list());
            showMenu();
            String choice = input.readLine("Pilih : ").trim();
            presenter.showMessage("");
            try {
                switch (choice.toLowerCase()) {
                    case "1": add(); break;
                    case "2": update(); break;
                    case "3": search(); break;
                    case "4": sort(); break;
                    case "5": delete(); break;
                    case "x": running = false; break;
                    default: presenter.showMessage("[!] Pilihan tidak dimengerti."); presenter.showMessage("");
                }
            } catch (ValidationException exception) {
                presenter.showMessage("[!] " + exception.getMessage()); presenter.showMessage("");
            } catch (EntityNotFoundException exception) {
                presenter.showMessage("[!] " + exception.getMessage()); presenter.showMessage("");
            }
        }
    }

    private void showMenu() {
        presenter.showMessage("Menu:");
        presenter.showMessage("1. Tambah");
        presenter.showMessage("2. Ubah Stok");
        presenter.showMessage("3. Cari");
        presenter.showMessage("4. Urutkan");
        presenter.showMessage("5. Hapus");
        presenter.showMessage("x. Keluar");
    }

    private void add() throws ValidationException {
        presenter.showMessage("[Menambah Barang]");
        String name = input.readLine("Nama (x Jika Batal) : "); if (name.trim().equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        int quantity = readQuantity();
        String category = input.readLine("Kategori (x Jika Batal) : "); if (category.trim().equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        presenter.showAdded(useCase.add(name, quantity, category));
    }

    private int readQuantity() throws ValidationException {
        String value = input.readLine("Jumlah : ").trim();
        try { return Integer.parseInt(value); } catch (NumberFormatException exception) { throw new ValidationException("Jumlah stok tidak valid!"); }
    }

    private void update() throws ValidationException, EntityNotFoundException {
        presenter.showMessage("[Mengubah Stok]");
        String idValue = input.readLine("ID Barang yang diubah (x Jika Batal) : ").trim(); if (idValue.equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        int id;
        try { id = Integer.parseInt(idValue); } catch (NumberFormatException exception) { presenter.showMessage("[!] ID tidak valid!"); presenter.showMessage(""); return; }
        String quantityValue = input.readLine("Jumlah Baru (Kosongkan jika tidak ingin mengubah) : ").trim();
        Integer quantity = null;
        if (!quantityValue.isEmpty()) {
            try { quantity = Integer.parseInt(quantityValue); } catch (NumberFormatException exception) { throw new ValidationException("Jumlah stok tidak valid!"); }
        }
        useCase.updateQuantity(id, quantity);
        presenter.showUpdated();
    }

    private void search() throws ValidationException {
        presenter.showMessage("[Mencari Barang]");
        String query = input.readLine("Kata Kunci (x Jika Batal) : "); if (query.trim().equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        List<Item> results = useCase.search(query); presenter.showSearchResult(query, results);
    }

    private void sort() {
        presenter.showMessage("[Mengurutkan Barang]");
        presenter.showMessage("Pilihan Pengurutan:");
        presenter.showMessage("1. Nama (A-Z)"); presenter.showMessage("2. Nama (Z-A)");
        presenter.showMessage("3. Jumlah (Terkecil -> Terbesar)"); presenter.showMessage("4. Jumlah (Terbesar -> Terkecil)"); presenter.showMessage("x. Batal");
        String value = input.readLine("Pilih : ").trim(); presenter.showMessage(""); if (value.equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        SortOption option;
        switch (value) { case "1": option = SortOption.NAME_ASC; break; case "2": option = SortOption.NAME_DESC; break; case "3": option = SortOption.QUANTITY_ASC; break; case "4": option = SortOption.QUANTITY_DESC; break; default: presenter.showMessage("[!] Pilihan tidak valid!"); presenter.showMessage(""); return; }
        presenter.showSortedResult(useCase.sort(option));
    }

    private void delete() throws EntityNotFoundException {
        presenter.showMessage("[Menghapus Barang]");
        String value = input.readLine("[ID Barang] yang dihapus (x Jika Batal) : ").trim(); if (value.equalsIgnoreCase("x")) { presenter.showMessage(""); return; }
        int id;
        try { id = Integer.parseInt(value); } catch (NumberFormatException exception) { presenter.showMessage("[!] ID tidak valid!"); presenter.showMessage(""); return; }
        useCase.delete(id); presenter.showDeleted();
    }
}