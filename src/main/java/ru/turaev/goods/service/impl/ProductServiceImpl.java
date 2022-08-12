package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.model.Product;
import ru.turaev.goods.repository.ProductRepository;
import ru.turaev.goods.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Продукт с id = " + id + " не найден"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByType(String type) {
        return productRepository.getProductsByType(type);
    }

    @Transactional
    @Override
    public Product changePrice(long productId, int newPrice) {
        Product product = findById(productId);
        product.setPrice(newPrice);
        return product;
    }

    @Transactional
    @Override
    public Product deleteProduct(long id) {
        Product product = findById(id);
        product.setProduced(false);
        return product;
    }
}
