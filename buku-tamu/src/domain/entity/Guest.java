package domain.entity;

public class Guest {
    private final Integer id;
    private String name;
    private String purpose;

    public Guest(Integer id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePurpose(String purpose) {
        this.purpose = purpose;
    }
}