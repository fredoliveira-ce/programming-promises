package domain;

import java.util.List;
import java.util.Optional;

public class Catalogue {

    private final List<Product> catalogue = List.of(
            new Product(1, "iPhone", Product.Category.ELECTRONICS),
            new Product(2, "Galaxy S21", Product.Category.ELECTRONICS),
            new Product(3, "Dress", Product.Category.CLOTHING),
            new Product(4, "Shirt", Product.Category.CLOTHING),
            new Product(5, "Pen", Product.Category.STATIONARY),
            new Product(6, "Paper", Product.Category.STATIONARY)
    );

    public Optional<Product> productById(final Integer id) {
        return catalogue.stream()
                .filter(product -> id.equals(product.getId()))
                .findFirst();
    }

    public Optional<Product> productByName(final String productName) {
        return catalogue.stream()
                .filter(product -> productName.equals(product.getName()))
                .findFirst();
    }

}
