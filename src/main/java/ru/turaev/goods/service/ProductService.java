package ru.turaev.goods.service;

import ru.turaev.goods.model.Product;

import java.util.List;

public interface ProductService {
    Product findById(long id);

    List<Product> getAllProducts();

    List<Product> getProductsByType(String type);

    Product changePrice(long productId, int newPrice);

    Product deleteProduct(long id);
}
