package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.exception.EntityNotFoundException;
import domain.exception.ValidationException;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;

public class ItemUseCase {
    private final IItemRepository repository;
    public ItemUseCase(IItemRepository repository) { this.repository = repository; }

    public Item add(String name, int quantity, String category) throws ValidationException {
        validateText(name, "Nama barang");
        validateQuantity(quantity);
        validateText(category, "Kategori");
        return repository.save(new Item(null, name.trim(), quantity, category.trim()));
    }

    public Item updateQuantity(int id, Integer quantity) throws ValidationException, EntityNotFoundException {
        if (quantity != null) validateQuantity(quantity);
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item dengan ID " + id + " tidak ditemukan."));
        if (quantity != null) item.changeQuantity(quantity);
        return item;
    }

    public List<Item> search(String name) throws ValidationException {
        validateText(name, "Nama barang");
        return repository.findByName(name.trim());
    }

    public List<Item> sort(SortOption option) {
        List<Item> items = new ArrayList<>(repository.findAll());
        items.sort(option.getComparator().thenComparing(Item::getId));
        return items;
    }

    public List<Item> list() { return repository.findAll(); }
    public void delete(int id) throws EntityNotFoundException { repository.deleteById(id); }

    private void validateQuantity(int quantity) throws ValidationException {
        if (quantity <= 0) throw new ValidationException("Jumlah stok tidak valid!");
    }
    private void validateText(String value, String label) throws ValidationException {
        if (value == null || value.trim().isEmpty()) throw new ValidationException(label + " tidak boleh kosong.");
    }
}