package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.dto.AccountingAndQuantityDto;
import ru.turaev.goods.exception.AccountingNotFoundException;
import ru.turaev.goods.exception.IncorrectOrderException;
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
    public Accounting findAccountingById(long id) {
        log.info("Trying to find accounting with id = {}", id);
        Accounting accounting = accountingRepository.findById(id)
                .orElseThrow(() -> new AccountingNotFoundException("Accounting with id = " + id + " not found"));
        log.info("The accounting with id = {} was found", id);
        return accounting;
    }

    @Override
    public Accounting findAccountingByProductIdAndStorehouseId(long productId, long storehouseId) {
        log.info("Trying to find accounting with productId = {} and storehouseId = {}", productId, storehouseId);
        Accounting accounting = accountingRepository.findAccountingByProductIdAndStorehouseId(productId, storehouseId)
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

    @Transactional
    @Override
    public int bookingGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
        log.info("Trying to books goods");
        int price = 0;
        for (AccountingAndQuantityDto accountingAndQuantityDto : accountingAndQuantityDtos) {
            Accounting accounting = findAccountingById(accountingAndQuantityDto.getAccountingId());
            if (accounting.getQuantity() < accountingAndQuantityDto.getQuantity()) {
                throw new IncorrectOrderException("Accounting with id = " + accountingAndQuantityDto.getAccountingId() + " has " + accounting.getQuantity() + " goods");
            }
            price += accountingAndQuantityDto.getQuantity() * accounting.getProduct().getPrice();
            accounting.setQuantity(accounting.getQuantity() - accountingAndQuantityDto.getQuantity());
            log.info("The quantity of goods decreased by {}", accountingAndQuantityDto.getQuantity());

        }
        log.info("The goods are booked. Order amount = {}", price);
        return price;
    }

    @Transactional
    @Override
    public void returnGoods(List<AccountingAndQuantityDto> accountingAndQuantityDtos) {
        log.info("Return goods");
        for (AccountingAndQuantityDto accountingAndQuantityDto : accountingAndQuantityDtos) {
            Accounting accounting = findAccountingById(accountingAndQuantityDto.getAccountingId());
            accounting.setQuantity(accounting.getQuantity() + accountingAndQuantityDto.getQuantity());
            log.info("The quantity of goods increased by {}", accountingAndQuantityDto.getQuantity());
        }
        log.info("The goods are returned");
    }
}
