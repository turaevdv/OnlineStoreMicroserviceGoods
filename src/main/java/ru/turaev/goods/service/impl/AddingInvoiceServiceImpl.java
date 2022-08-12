package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.model.*;
import ru.turaev.goods.repository.AddingInvoiceRepository;
import ru.turaev.goods.service.AccountingService;
import ru.turaev.goods.service.AddingInvoiceService;
import ru.turaev.goods.service.ProductService;

@Service
@RequiredArgsConstructor
public class AddingInvoiceServiceImpl implements AddingInvoiceService {
    private final AddingInvoiceRepository addingInvoiceRepository;
    private final AccountingService accountingService;
    private final ProductService productService;

    @Transactional
    @Override
    public AddingInvoice save(AddingInvoice addingInvoice) {
        for (GoodsAndQuantity goodsAndQuantity : addingInvoice.getGoodsAndQuantities()) {
            Product product = goodsAndQuantity.getProduct();
            try {
                Accounting accounting = accountingService.findAccountingByProductIdAndStorehouseId(product.getId(), addingInvoice.getStorehouseId());
                accounting.setQuantity(accounting.getQuantity() + goodsAndQuantity.getQuantity());
            } catch (Exception e) {
                Product persistentProduct = product;
                if (product.getId() != 0) {
                    try {
                        persistentProduct = productService.findById(product.getId());
                    } catch (Exception ex) {
                        persistentProduct.setId(0);
                    }
                }
                Accounting accounting = Accounting.builder()
                        .product(persistentProduct)
                        .storehouseId(addingInvoice.getStorehouseId())
                        .quantity(goodsAndQuantity.getQuantity())
                        .build();
                accountingService.save(accounting);
            }
        }
        return addingInvoiceRepository.save(addingInvoice);
    }
}
