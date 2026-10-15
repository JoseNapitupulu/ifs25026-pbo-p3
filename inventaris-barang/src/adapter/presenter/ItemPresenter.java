package adapter.presenter;

import domain.entity.Item;
import java.util.List;

public class ItemPresenter extends ConsolePresenter {
    public String formatItem(Item item) {
        return String.format("[%d] %s | Stok: %d | Kategori: %s", item.getId(), item.getName(), item.getQuantity(), item.getCategory());
    }
    public void showDashboard(List<Item> items) {
        printLine("Daftar Barang:");
        if (items.isEmpty()) printLine("- Data barang belum tersedia!");
        else showItems(items);
    }
    public void showItems(List<Item> items) { printList(items, this::formatItem); }
    public void showAdded(Item item) { printLine("Berhasil menambah barang: " + formatItem(item)); printBlankLine(); }
    public void showUpdated() { printLine("Berhasil mengubah stok barang."); printBlankLine(); }
    public void showSearchResult(String query, List<Item> items) {
        printLine("Hasil Pencarian: \"" + query + "\"");
        if (items.isEmpty()) printLine("- Barang tidak ditemukan!"); else showItems(items);
        printBlankLine();
    }
    public void showSortedResult(List<Item> items) { printLine("Daftar Barang (Terurut):"); showItems(items); printBlankLine(); }
    public void showDeleted() { printLine("Berhasil menghapus barang."); printBlankLine(); }
    public void showMessage(String message) { printLine(message); }
}