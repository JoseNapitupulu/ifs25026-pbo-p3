package adapter.presenter;

import domain.entity.Item;
import java.util.List;

public class ItemPresenter {
    public String formatItem(Item item) {
        return String.format("[%d] %s | Stok: %d | Kategori: %s", item.getId(), item.getName(), item.getQuantity(), item.getCategory());
    }
    public void showDashboard(List<Item> items) {
        System.out.println("Daftar Barang:");
        if (items.isEmpty()) System.out.println("- Data barang belum tersedia!");
        else showItems(items);
    }
    public void showItems(List<Item> items) { for (Item item : items) System.out.println(formatItem(item)); }
    public void showAdded(Item item) { System.out.println("Berhasil menambah barang: " + formatItem(item)); System.out.println(); }
    public void showUpdated() { System.out.println("Berhasil mengubah stok barang."); System.out.println(); }
    public void showSearchResult(String query, List<Item> items) {
        System.out.println("Hasil Pencarian: \"" + query + "\"");
        if (items.isEmpty()) System.out.println("- Barang tidak ditemukan!"); else showItems(items);
        System.out.println();
    }
    public void showSortedResult(List<Item> items) { System.out.println("Daftar Barang (Terurut):"); showItems(items); System.out.println(); }
    public void showDeleted() { System.out.println("Berhasil menghapus barang."); System.out.println(); }
    public void showMessage(String message) { System.out.println(message); }
}