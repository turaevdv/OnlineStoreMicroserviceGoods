package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.turaev.goods.model.Accounting;
import ru.turaev.goods.service.AccountingService;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounting")
@RequiredArgsConstructor
public class AccountingController {
    private final AccountingService accountingService;

    @GetMapping("/{productId}/{storehouseId}")
    public Accounting findAccountingByProductIdAndStorehouseId(@PathVariable long productId,
                                                               @PathVariable long storehouseId) {
        return accountingService.findAccountingByProductIdAndStorehouseId(productId, storehouseId);
    }

    @GetMapping("/{productId}")
    public List<Accounting> findAllAccountingByProductId(@PathVariable long productId) {
        return accountingService.findAllAccountingByProductId(productId);
    }

    @GetMapping("/by-storehouse/{storehouseId}")
    public List<Accounting> findAllAccountingByStorehouseId(@PathVariable long storehouseId) {
        return accountingService.findAllAccountingByStorehouseId(storehouseId);
    }

    @GetMapping
    public List<Accounting> findAllAccounting() {
        return accountingService.findAllAccounting();
    }
}
