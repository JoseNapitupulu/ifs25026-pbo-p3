package adapter.presenter;

import domain.entity.Guest;

import java.util.List;

public class GuestPresenter extends ConsolePresenter {
    public String formatGuest(Guest guest) {
        return String.format("[%d] %s | Tujuan: %s", guest.getId(), guest.getName(), guest.getPurpose());
    }

    public void showDashboard(List<Guest> guests) {
        printLine("Daftar Tamu:");
        if (guests.isEmpty()) {
            printLine("- Data tamu belum tersedia!");
        } else {
            showGuests(guests);
        }
    }

    public void showGuests(List<Guest> guests) {
        for (Guest guest : guests) {
            printLine(formatGuest(guest));
        }
    }

    public void showRegistered(Guest guest) {
        printLine("Berhasil mendaftarkan tamu: " + formatGuest(guest));
        printBlankLine();
    }

    public void showSearchResult(String query, List<Guest> guests) {
        printLine("Hasil Pencarian: \"" + query + "\"");
        if (guests.isEmpty()) {
            printLine("- Tamu tidak ditemukan!");
        } else {
            showGuests(guests);
        }
        printBlankLine();
    }

    public void showDeleted() {
        printLine("Berhasil menghapus tamu.");
        printBlankLine();
    }

    public void showMessage(String message) {
        printLine(message);
    }
}