package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.dto.DebitingInvoiceDTO;
import ru.turaev.goods.exception.DebitingInvoiceNotFoundException;
import ru.turaev.goods.exception.IncorrectDebitingInvoiceException;
import ru.turaev.goods.model.*;
import ru.turaev.goods.repository.DebitingInvoiceRepository;
import ru.turaev.goods.service.AccountingService;
import ru.turaev.goods.service.DebitingInvoiceService;
import ru.turaev.goods.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitingInvoiceServiceImpl implements DebitingInvoiceService {
    private final DebitingInvoiceRepository debitingInvoiceRepository;
    private final ProductService productService;
    private final AccountingService accountingService;

    @Override
    public DebitingInvoice findById(long id) {
        log.info("Trying to find debiting invoice with id = {}", id);
        DebitingInvoice debitingInvoice = debitingInvoiceRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new DebitingInvoiceNotFoundException("Debiting invoice with id = " + id + " was not found"));
        log.info("The debiting invoice with id = {} was found", id);
        return debitingInvoice;
    }

    @Override
    public DebitingInvoice findUnconfirmedInvoiceById(long id) {
        DebitingInvoice debitingInvoice = findById(id);
        if (debitingInvoice.isConfirmed()) {
            throw new IncorrectDebitingInvoiceException("The debiting invoice with id = " + id + " has already been confirmed");
        }
        return debitingInvoice;
    }

    @Transactional
    @Override
    public DebitingInvoice add(DebitingInvoiceDTO debitingInvoiceDTO) {
        log.info("Trying to add a new debiting invoice");
        DebitingInvoice debitingInvoice = new DebitingInvoice();
        debitingInvoice.setStorehouseId(debitingInvoiceDTO.getStorehouseId());

        List<GoodsAndQuantity> goodsAndQuantities = debitingInvoiceDTO.getGoodsAndQuantitiesDTO()
                .stream()
                .map(goodsAndQuantityDTO -> {
                    GoodsAndQuantity goodsAndQuantity = new GoodsAndQuantity();
                    goodsAndQuantity.setProduct(productService.findById(goodsAndQuantityDTO.getProductId()));
                    goodsAndQuantity.setQuantity(goodsAndQuantityDTO.getQuantity());
                    return goodsAndQuantity;
                }).collect(Collectors.toList());

        debitingInvoice.setGoodsAndQuantities(goodsAndQuantities);
        debitingInvoice.setInvoiceDate(debitingInvoiceDTO.getInvoiceDate());
        debitingInvoice.setInvoiceTime(debitingInvoiceDTO.getInvoiceTime());
        debitingInvoice.setConfirmed(false);
        debitingInvoice.setDeleted(false);

        if (!isDebitingInvoiceCorrect(debitingInvoice)) {
            throw new IncorrectDebitingInvoiceException("This debiting invoice is incorrect. Check the quantity of products");
        }

        debitingInvoiceRepository.save(debitingInvoice);
        log.info("The debiting invoice with id = {} was added", debitingInvoice.getId());
        return debitingInvoice;
    }

    @Override
    public List<DebitingInvoice> getAllUnconfirmedInvoices() {
        return debitingInvoiceRepository.findByIsConfirmedIsFalseAndIsDeletedIsFalse();
    }

    @Transactional
    @Override
    public DebitingInvoice confirmInvoice(long id) {
        log.info("Trying to confirm the debiting invoice with id = {}", id);
        DebitingInvoice debitingInvoice = findUnconfirmedInvoiceById(id);
        saveConfirmedInvoice(debitingInvoice);
        log.info("The debiting invoice with id = {} has been confirmed", debitingInvoice.getId());
        return debitingInvoice;
    }

    @Transactional
    @Override
    public List<DebitingInvoice> confirmAllInvoices() {
        List<DebitingInvoice> unconfirmedList = getAllUnconfirmedInvoices();
        if (unconfirmedList.isEmpty()) {
            throw new DebitingInvoiceNotFoundException("There are no unconfirmed debiting invoices");
        }

        return unconfirmedList.stream()
                .map(debitingInvoice -> confirmInvoice(debitingInvoice.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DebitingInvoice deleteInvoice(long id) {
        log.info("Trying to delete the debiting invoice with id = {}", id);
        DebitingInvoice debitingInvoice = findUnconfirmedInvoiceById(id);
        debitingInvoice.setConfirmed(false);
        debitingInvoice.setDeleted(true);
        log.info("The debiting invoice with id = {} has been deleted", id);
        return debitingInvoice;
    }

    @Transactional
    public void saveConfirmedInvoice(DebitingInvoice debitingInvoice) {
        if (!isDebitingInvoiceCorrect(debitingInvoice)) {
            throw new IncorrectDebitingInvoiceException("This adding invoice is incorrect. Check the quantity of products");
        }
        for (GoodsAndQuantity goodsAndQuantity : debitingInvoice.getGoodsAndQuantities()) {
            Accounting accounting =
                    accountingService.findAccountingByProductIdAndStorehouseId(goodsAndQuantity.getProduct().getId(), debitingInvoice.getStorehouseId());
            accounting.setQuantity(accounting.getQuantity() - goodsAndQuantity.getQuantity());
        }
        debitingInvoice.setConfirmed(true);
        debitingInvoice.setDeleted(false);
    }

    public boolean isDebitingInvoiceCorrect(DebitingInvoice invoice) {
        if (invoice.getGoodsAndQuantities().isEmpty()) {
            return false;
        }
        for (GoodsAndQuantity goodsAndQuantity : invoice.getGoodsAndQuantities()) {
            Product product = goodsAndQuantity.getProduct();

            Accounting accounting;
            try {
                accounting = accountingService.findAccountingByProductIdAndStorehouseId(product.getId(), invoice.getStorehouseId());
            } catch (Exception e) {
                return false;
            }

            if (accounting.getQuantity() < goodsAndQuantity.getQuantity() || goodsAndQuantity.getQuantity() <= 0) {
                return false;
            }
        }
        return true;
    }
}
