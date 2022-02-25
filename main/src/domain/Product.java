package domain;

public class Product {

    private final Integer id;
    private final String name;
    private final Category category;

    public Product(final Integer id, final String productName, final Category category) {
        this.id = id;
        this.name = productName;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    enum Category {
        ELECTRONICS,
        CLOTHING,
        FOOD,
        STATIONARY
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
