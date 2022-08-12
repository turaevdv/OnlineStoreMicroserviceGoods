package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.turaev.goods.model.Accounting;
import ru.turaev.goods.repository.AccountingRepository;
import ru.turaev.goods.service.AccountingService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {
    private final AccountingRepository accountingRepository;

    @Override
    public Accounting findAccountingByProductIdAndStorehouseId(long productId, long storehouseId) {
        return accountingRepository.findAccountingByProductIdAndStorehouseId(productId, storehouseId)
                .orElseThrow(() -> new RuntimeException("На данном складе такого товара нет"));
    }

    @Override
    public List<Accounting> findAllAccountingByProductId(long productId) {
        return accountingRepository.findAllByProductId(productId);
    }

    @Override
    public List<Accounting> findAllAccountingByStorehouseId(long storehouseId) {
        return accountingRepository.findAllByStorehouseId(storehouseId);
    }

    @Override
    public List<Accounting> findAllAccounting() {
        return accountingRepository.findAll();
    }

    @Override
    public Accounting save(Accounting accounting) {
        return accountingRepository.save(accounting);
    }
}
