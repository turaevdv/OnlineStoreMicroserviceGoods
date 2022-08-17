package ru.turaev.goods.service;

import ru.turaev.goods.dto.AccountingAndQuantityDto;
import ru.turaev.goods.model.Accounting;

import java.util.List;

public interface AccountingService {
    Accounting findAccountingById(long id);
    Accounting findAccountingByProductIdAndStorehouseId(long productId, long storehouseId);
    List<Accounting> findAllAccountingByProductId(long productId);
    List<Accounting> findAllAccountingByStorehouseId(long storehouseId);
    List<Accounting> findAllAccounting();
    Accounting save(Accounting accounting);
    int bookingGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos);
    void returnGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos);
}
