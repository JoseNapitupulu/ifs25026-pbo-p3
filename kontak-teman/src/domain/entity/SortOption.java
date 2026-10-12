package domain.entity;

import java.util.Comparator;

/**
 * Opsi pengurutan kontak.
 * Comparator disimpan di domain agar aturan pengurutan tidak berada di view.
 */
public enum SortOption {
    /** Nama dari A ke Z, tanpa membedakan huruf besar dan kecil. */
    NAME_ASC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)),
    /** Nama dari Z ke A, tanpa membedakan huruf besar dan kecil. */
    NAME_DESC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Contact> comparator;

    SortOption(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator untuk opsi ini. */
    public Comparator<Contact> getComparator() {
        return comparator;
    }
}
