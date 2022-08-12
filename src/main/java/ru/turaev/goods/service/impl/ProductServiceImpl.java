package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.exception.ProductNotFoundException;
import ru.turaev.goods.model.Product;
import ru.turaev.goods.repository.ProductRepository;
import ru.turaev.goods.service.ProductService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product findById(long id) {
        log.info("Trying to find product with id = {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id = " + id + " not found"));
        log.info("The product with id = {} was found", id);
        return product;
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
        int oldPrice = product.getPrice();
        product.setPrice(newPrice);
        log.info("The price of a product with id = {} has been changed from {} to {}", productId, oldPrice, newPrice);
        return product;
    }

    @Transactional
    @Override
    public Product deleteProduct(long id) {
        Product product = findById(id);
        product.setProduced(false);
        log.info("The product with id = {} has been deleted", id);
        return product;
    }
}
