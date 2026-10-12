package domain.entity;

import java.util.Comparator;

public enum SortOption {
    NAME_ASC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)),
    NAME_DESC(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed()),
    PHONE_ASC(Comparator.comparing(Contact::getPhone)),
    EMAIL_ASC(Comparator.comparing(Contact::getEmail, String.CASE_INSENSITIVE_ORDER));

    private final Comparator<Contact> comparator;

    SortOption(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Contact> getComparator() {
        return comparator;
    }
}
