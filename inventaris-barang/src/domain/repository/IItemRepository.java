package domain.repository;

import domain.entity.Item;
import java.util.List;

public interface IItemRepository extends IRepository<Item, Integer> {
    List<Item> findByName(String name);
}