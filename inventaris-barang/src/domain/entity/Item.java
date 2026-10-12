package domain.entity;

public class Item {
    private final Integer id;
    private String name;
    private int quantity;
    private String category;

    public Item(Integer id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public String getCategory() { return category; }
    public void changeQuantity(int quantity) { this.quantity = quantity; }
    public void changeName(String name) { this.name = name; }
    public void changeCategory(String category) { this.category = category; }
}