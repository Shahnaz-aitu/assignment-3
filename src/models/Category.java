package models;

public class Category extends AbstractEntity {
    private String name;

    public Category(int id, String name) {
        super(id);  // Наследуем id от AbstractEntity
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
