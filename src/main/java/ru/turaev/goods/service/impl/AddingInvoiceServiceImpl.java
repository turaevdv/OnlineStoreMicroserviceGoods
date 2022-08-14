package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.exception.StorehouseNotFoundException;
import ru.turaev.goods.model.*;
import ru.turaev.goods.repository.AddingInvoiceRepository;
import ru.turaev.goods.restconsumer.StorehouseRestConsumer;
import ru.turaev.goods.service.AccountingService;
import ru.turaev.goods.service.AddingInvoiceService;
import ru.turaev.goods.service.ProductService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddingInvoiceServiceImpl implements AddingInvoiceService {
    private final AddingInvoiceRepository addingInvoiceRepository;
    private final AccountingService accountingService;
    private final ProductService productService;
    private final StorehouseRestConsumer storehouseRestConsumer;

    @Transactional
    @Override
    public AddingInvoice save(AddingInvoice addingInvoice) {
        log.info("Trying to save an adding invoice");

        if(!storehouseRestConsumer.isStorehouseExist(addingInvoice.getStorehouseId())) {
            throw new StorehouseNotFoundException("Storehouse with id = " + addingInvoice.getStorehouseId() + " doesn't exist");
        }

        for (GoodsAndQuantity goodsAndQuantity : addingInvoice.getGoodsAndQuantities()) {
            Product product = goodsAndQuantity.getProduct();
            try {
                Accounting accounting = accountingService.findAccountingByProductIdAndStorehouseId(product.getId(), addingInvoice.getStorehouseId());
                accounting.setQuantity(accounting.getQuantity() + goodsAndQuantity.getQuantity());
                log.info("{} products were added to the accounting with id = {}", goodsAndQuantity.getQuantity(), accounting.getId());
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
                log.info("A new accounting with id = {} was saved", accounting.getId());
            }
        }
        addingInvoiceRepository.save(addingInvoice);
        log.info("The adding invoice with id = {} was saved", addingInvoice.getId());
        return addingInvoice;
    }
}
