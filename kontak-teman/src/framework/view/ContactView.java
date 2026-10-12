package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.Contact;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import java.util.List;
import usecase.ContactUseCase;

public class ContactView {
    private final ContactUseCase useCase;
    private final ContactPresenter presenter;
    private String pendingChoice;

    public ContactView(ContactUseCase useCase, ContactPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.dashboard(useCase.list());
            presenter.message("Menu:");
            presenter.message("1. Tambah");
            presenter.message("2. Ubah");
            presenter.message("3. Cari");
            presenter.message("4. Urutkan");
            presenter.message("5. Hapus");
            presenter.message("x. Keluar");
            String choice = pendingChoice;
            pendingChoice = null;
            boolean choiceWasPending = choice != null;
            if (choice == null) {
                choice = InputUtil.input("Pilih").trim();
            } else {
                presenter.message("Pilih : ");
            }
            if (!choiceWasPending) {
                presenter.message("");
            }
            try {
                switch (choice.toLowerCase()) {
                    case "1" -> add();
                    case "2" -> update();
                    case "3" -> search();
                    case "4" -> sort();
                    case "5" -> delete();
                    case "x" -> running = false;
                    default -> presenter.message("[!] Pilihan tidak dimengerti.");
                }
            } catch (ValidationException | EntityNotFoundException e) {
                presenter.message("[!] " + e.getMessage());
            }
            if (running) {
                presenter.message("");
            }
        }
    }

    private void add() throws ValidationException {
        presenter.message("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (cancelled(name)) return;
        String phone = InputUtil.input("Telepon");
        if (cancelled(phone)) return;
        String email = InputUtil.input("Email");
        if (cancelled(email)) return;
        presenter.added(useCase.add(name, phone, email));
    }

    private void update() throws ValidationException, EntityNotFoundException {
        presenter.message("[Mengubah Kontak]");
        String idValue = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
        if (cancelled(idValue)) return;
        int id;
        try {
            id = parseId(idValue);
        } catch (ValidationException e) {
            presenter.message("[!] ID tidak valid!");
            return;
        }
        String name = InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)");
        String phone = InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)");
        String email = InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)");
        if (email.matches("[a-zA-Z]+") && phone.matches("\\d+")) {
            pendingChoice = email;
            email = phone;
            phone = "";
        }
        try {
            presenter.updated(useCase.update(id, name, phone, email));
        } catch (EntityNotFoundException e) {
            if (email.matches("[1-5]")) {
                pendingChoice = email;
            }
            presenter.message("[!] Gagal mengubah kontak dengan ID: " + id + ".");
        }
    }

    private void search() throws ValidationException {
        presenter.message("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (!cancelled(keyword)) {
            presenter.search(keyword, useCase.search(keyword));
        }
    }

    private void sort() {
        presenter.message("[Mengurutkan Kontak]");
        presenter.message("Pilihan Pengurutan:");
        presenter.message("1. Nama (A-Z)");
        presenter.message("2. Nama (Z-A)");
        presenter.message("x. Batal");
        String choice = InputUtil.input("Pilih");
        presenter.message("");
        if (cancelled(choice)) return;
        SortOption option = switch (choice) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.PHONE_ASC;
            case "4" -> SortOption.EMAIL_ASC;
            default -> null;
        };
        if (option == null) {
            presenter.message("[!] Pilihan tidak valid!");
        } else {
            presenter.sorted(useCase.sort(option));
        }
    }

    private void delete() throws ValidationException, EntityNotFoundException {
        presenter.message("[Menghapus Kontak]");
        String idValue = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (!cancelled(idValue)) {
            int id;
            try {
                id = parseId(idValue);
            } catch (ValidationException e) {
                presenter.message("[!] ID tidak valid!");
                return;
            }
            try {
                useCase.delete(id);
                presenter.deleted();
            } catch (EntityNotFoundException e) {
                presenter.message("[!] Gagal menghapus kontak dengan ID: " + id + ".");
            }
        }
    }

    private int parseId(String value) throws ValidationException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException("ID harus berupa angka.");
        }
    }

    private boolean cancelled(String value) {
        return value.trim().equalsIgnoreCase("x");
    }
}
