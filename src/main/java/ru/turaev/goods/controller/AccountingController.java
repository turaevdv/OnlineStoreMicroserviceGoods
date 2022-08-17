package ru.turaev.goods.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.turaev.goods.dto.AccountingAndQuantityDto;
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

    @PostMapping("/booking-goods")
    public int bookingGoods(@RequestBody List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
        return accountingService.bookingGoods(accountingAndQuantityDtos);
    }

    @PostMapping("/return-goods")
    public void returnGoods(@RequestBody List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
        accountingService.returnGoods(accountingAndQuantityDtos);
    }
}
