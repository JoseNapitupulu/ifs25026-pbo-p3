package domain.repository;

import domain.entity.Contact;

import java.util.List;

/**
 * Port repository khusus untuk operasi kontak.
 */
public interface IContactRepository extends IRepository<Contact, Integer> {
    /** Menyimpan kontak baru dengan ID yang dibuat repository. */
    Contact save(String name, String phone, String email);

    /** Mencari kontak berdasarkan bagian nama. */
    List<Contact> findByName(String name);
}
