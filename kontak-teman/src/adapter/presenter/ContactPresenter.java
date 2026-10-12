package adapter.presenter;

import domain.entity.Contact;
import java.util.List;

public class ContactPresenter {
    public void dashboard(List<Contact> contacts) {
        printList("Daftar Kontak:", contacts, "- Data kontak belum tersedia!");
    }

    public void added(Contact contact) {
        System.out.println("Berhasil menambah kontak: " + format(contact));
    }

    public void updated(Contact contact) {
        System.out.println("Berhasil mengubah kontak.");
    }

    public void search(String keyword, List<Contact> contacts) {
        printList("Hasil Pencarian: \"" + keyword + "\"", contacts,
                "- Kontak tidak ditemukan!");
    }

    public void sorted(List<Contact> contacts) {
        printList("Daftar Kontak (Terurut):", contacts, "- Data kontak belum tersedia!");
    }

    public void deleted() {
        System.out.println("Berhasil menghapus kontak.");
    }

    public void message(String message) {
        System.out.println(message);
    }

    private void printList(String header, List<Contact> contacts, String emptyMessage) {
        System.out.println(header);
        if (contacts.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        contacts.forEach(contact -> System.out.println(format(contact)));
    }

    private String format(Contact contact) {
        return String.format("[%d] %s | Telp: %s | Email: %s",
                contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }
}
