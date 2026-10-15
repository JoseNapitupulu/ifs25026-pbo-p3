package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter extends ConsolePresenter {
    public void dashboard(List<Contact> contacts) {
        printList("Daftar Kontak:", contacts, "- Data kontak belum tersedia!", this::format);
    }

    public void added(Contact contact) {
        printLine("Berhasil menambah kontak: " + format(contact));
    }

    public void updated(Contact contact) {
        printLine("Berhasil mengubah kontak.");
    }

    public void search(String keyword, List<Contact> contacts) {
        printList("Hasil Pencarian: \"" + keyword + "\"", contacts,
                "- Kontak tidak ditemukan!", this::format);
    }

    public void sorted(List<Contact> contacts) {
        printList("Daftar Kontak (Terurut):", contacts, "- Data kontak belum tersedia!", this::format);
    }

    public void deleted() {
        printLine("Berhasil menghapus kontak.");
    }

    public void message(String message) {
        printLine(message);
    }

    private String format(Contact contact) {
        return String.format("[%d] %s | Telp: %s | Email: %s",
                contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }
}
