package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.turaev.goods.exception.AccountingNotFoundException;
import ru.turaev.goods.model.Accounting;
import ru.turaev.goods.repository.AccountingRepository;
import ru.turaev.goods.service.AccountingService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {
    private final AccountingRepository accountingRepository;

    @Override
    public Accounting findAccountingByProductIdAndStorehouseId(long productId, long storehouseId) {
        log.info("Trying to find accounting with productId = {} and storehouseId = {}", productId, storehouseId);
        Accounting accounting =  accountingRepository.findAccountingByProductIdAndStorehouseId(productId, storehouseId)
                .orElseThrow(() -> new AccountingNotFoundException("No product with id = " + productId + " found in storehouse with id = " + storehouseId));
        log.info("The accounting with id = {} was found", accounting.getId());
        return accounting;
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
        accountingRepository.save(accounting);
        log.info("The accounting with id = {} was saved", accounting.getId());
        return accounting;
    }
}
