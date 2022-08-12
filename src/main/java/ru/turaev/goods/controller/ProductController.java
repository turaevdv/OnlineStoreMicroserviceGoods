package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.goods.model.Product;
import ru.turaev.goods.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/goods")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productService.findById(id);
    }

    @GetMapping("/type")
    public List<Product> getProductsByType(@RequestParam("type") String type) {
        return productService.getProductsByType(type);
    }

    @PutMapping("/{id}")
    public Product changePrice(@PathVariable long id,
                               @RequestParam int newPrice) {
        return productService.changePrice(id, newPrice);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
