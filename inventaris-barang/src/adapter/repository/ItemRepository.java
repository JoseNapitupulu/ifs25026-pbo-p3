package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ItemRepository extends InMemoryRepository<Item, Integer> implements IItemRepository {
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Item save(Item item) {
        int id = nextId.getAndIncrement();
        return saveWithId(id, new Item(id, item.getName(), item.getQuantity(), item.getCategory()));
    }

    @Override
    public List<Item> findBy(Predicate<Item> predicate) {
        return super.findBy(predicate);
    }

    @Override
    public List<Item> findByName(String name) {
        String query = name.toLowerCase();
        return findBy(item -> item.getName().toLowerCase().contains(query));
    }
}