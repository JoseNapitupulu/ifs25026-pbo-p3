package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import usecase.GuestUseCase;

import java.util.List;

public class GuestView {
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;
    private final InputUtil input = new InputUtil();

    public GuestView(GuestUseCase useCase, GuestPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.showDashboard(useCase.list());
            showMenu();
            String choice = input.readLine("Pilih : ").trim();
            presenter.showMessage("");
            try {
                switch (choice.toLowerCase()) {
                    case "1": register(); break;
                    case "2": search(); break;
                    case "3": delete(); break;
                    case "x": running = false; break;
                    default:
                        presenter.showMessage("[!] Pilihan tidak dimengerti.");
                        presenter.showMessage("");
                }
            } catch (ValidationException exception) {
                presenter.showMessage("[!] " + exception.getMessage());
                presenter.showMessage("");
            } catch (EntityNotFoundException exception) {
                presenter.showMessage("[!] " + exception.getMessage());
                presenter.showMessage("");
            }
        }
    }

    private void showMenu() {
        presenter.showMessage("Menu:");
        presenter.showMessage("1. Daftarkan");
        presenter.showMessage("2. Cari");
        presenter.showMessage("3. Hapus");
        presenter.showMessage("x. Keluar");
    }

    private void register() throws ValidationException {
        presenter.showMessage("[Mendaftarkan Tamu]");
        String name = input.readLine("Nama (x Jika Batal) : ");
        if (name.trim().equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        String purpose = input.readLine("Tujuan Kunjungan (x Jika Batal) : ");
        if (purpose.trim().equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        Guest guest = useCase.register(name, purpose);
        presenter.showRegistered(guest);
    }

    private void search() throws ValidationException {
        presenter.showMessage("[Mencari Tamu]");
        String query = input.readLine("Nama (x Jika Batal) : ");
        if (query.trim().equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        List<Guest> guests = useCase.search(query);
        presenter.showSearchResult(query, guests);
    }

    private void delete() throws EntityNotFoundException {
        presenter.showMessage("[Menghapus Tamu]");
        String value = input.readLine("[ID Tamu] yang dihapus (x Jika Batal) : ").trim();
        if (value.equalsIgnoreCase("x")) {
            presenter.showMessage("");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            presenter.showMessage("[!] ID tidak valid!");
            presenter.showMessage("");
            return;
        }
        useCase.delete(id);
        presenter.showDeleted();
    }
}