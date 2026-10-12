package adapter.presenter;

import domain.entity.Guest;

import java.util.List;

public class GuestPresenter {
    public String formatGuest(Guest guest) {
        return String.format("[%d] %s | Tujuan: %s", guest.getId(), guest.getName(), guest.getPurpose());
    }

    public void showDashboard(List<Guest> guests) {
        System.out.println("Daftar Tamu:");
        if (guests.isEmpty()) {
            System.out.println("- Data tamu belum tersedia!");
        } else {
            showGuests(guests);
        }
    }

    public void showGuests(List<Guest> guests) {
        for (Guest guest : guests) {
            System.out.println(formatGuest(guest));
        }
    }

    public void showRegistered(Guest guest) {
        System.out.println("Berhasil mendaftarkan tamu: " + formatGuest(guest));
        System.out.println();
    }

    public void showSearchResult(String query, List<Guest> guests) {
        System.out.println("Hasil Pencarian: \"" + query + "\"");
        if (guests.isEmpty()) {
            System.out.println("- Tamu tidak ditemukan!");
        } else {
            showGuests(guests);
        }
        System.out.println();
    }

    public void showDeleted() {
        System.out.println("Berhasil menghapus tamu.");
        System.out.println();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}