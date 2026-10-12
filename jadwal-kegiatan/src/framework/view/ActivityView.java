package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.Activity;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import framework.util.InputUtil;
import java.util.List;
import usecase.ActivityUseCase;

public class ActivityView {
    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;
    private final InputUtil input = new InputUtil();

    public ActivityView(ActivityUseCase useCase, ActivityPresenter presenter) {
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;
        while (running) {
            presenter.dashboard(useCase.list());
            showMenu();
            String choice = input.readLine("Pilih : ").trim();
            presenter.message("");

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
            } catch (ValidationException e) {
                presenter.message("[!] " + e.getMessage());
            }

            if (running) {
                presenter.message("");
            }
        }
    }

    private void showMenu() {
        presenter.message("Menu:");
        presenter.message("1. Tambah");
        presenter.message("2. Ubah");
        presenter.message("3. Cari");
        presenter.message("4. Urutkan");
        presenter.message("5. Hapus");
        presenter.message("x. Keluar");
    }

    private void add() throws ValidationException {
        presenter.message("[Menambah Kegiatan]");
        String title = input.readLine("Judul (x Jika Batal) : ");
        if (isCancelled(title)) {
            return;
        }
        String day = input.readLine("Hari (x Jika Batal) : ");
        if (isCancelled(day)) {
            return;
        }
        String time = input.readLine("Waktu (x Jika Batal) : ");
        if (isCancelled(time)) {
            return;
        }
        presenter.added(useCase.add(title, day, time));
    }

    private void update() throws ValidationException {
        presenter.message("[Mengubah Kegiatan]");
        String idValue = input.readLine("ID Kegiatan yang diubah (x Jika Batal) : ");
        if (isCancelled(idValue)) {
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idValue.trim());
        } catch (NumberFormatException e) {
            presenter.message("[!] ID tidak valid!");
            return;
        }

        String title = input.readLine("Judul Baru (Kosongkan jika tidak ingin mengubah) : ");
        if (isCancelled(title)) {
            return;
        }
        String day = input.readLine("Hari Baru (Kosongkan jika tidak ingin mengubah) : ");
        if (isCancelled(day)) {
            return;
        }
        String time = input.readLine("Waktu Baru (Kosongkan jika tidak ingin mengubah) : ");
        if (isCancelled(time)) {
            return;
        }

        try {
            presenter.updated(useCase.update(id, title, day, time));
        } catch (EntityNotFoundException e) {
            presenter.message("[!] Gagal mengubah kegiatan dengan ID: " + id + ".");
        }
    }

    private void search() throws ValidationException {
        presenter.message("[Mencari Kegiatan]");
        String keyword = input.readLine("Kata Kunci (x Jika Batal) : ");
        if (!isCancelled(keyword)) {
            List<Activity> result = useCase.search(keyword);
            presenter.search(keyword, result);
        }
    }

    private void sort() {
        presenter.message("[Mengurutkan Kegiatan]");
        presenter.message("Pilihan Pengurutan:");
        presenter.message("1. Hari (Senin -> Minggu)");
        presenter.message("2. Waktu (Awal -> Akhir)");
        presenter.message("3. Judul (A-Z)");
        presenter.message("4. Judul (Z-A)");
        presenter.message("x. Batal");
        String choice = input.readLine("Pilih : ");
        presenter.message("");
        if (isCancelled(choice)) {
            return;
        }

        SortOption option = switch (choice.trim()) {
            case "1" -> SortOption.DAY;
            case "2" -> SortOption.TIME;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
        if (option == null) {
            presenter.message("[!] Pilihan tidak valid!");
            return;
        }
        presenter.sorted(useCase.sort(option));
    }

    private void delete() throws ValidationException {
        presenter.message("[Menghapus Kegiatan]");
        String idValue = input.readLine("[ID Kegiatan] yang dihapus (x Jika Batal) : ");
        if (isCancelled(idValue)) {
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idValue.trim());
        } catch (NumberFormatException e) {
            presenter.message("[!] ID tidak valid!");
            return;
        }

        try {
            useCase.delete(id);
            presenter.deleted();
        } catch (EntityNotFoundException e) {
            presenter.message("[!] Gagal menghapus kegiatan dengan ID: " + id + ".");
        }
    }

    private boolean isCancelled(String value) {
        return value.trim().equalsIgnoreCase("x");
    }
}
