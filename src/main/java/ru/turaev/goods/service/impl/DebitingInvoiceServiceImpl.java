package ru.turaev.goods.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.turaev.goods.dto.DebitingInvoiceDTO;
import ru.turaev.goods.model.*;
import ru.turaev.goods.repository.DebitingInvoiceRepository;
import ru.turaev.goods.service.AccountingService;
import ru.turaev.goods.service.DebitingInvoiceService;
import ru.turaev.goods.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DebitingInvoiceServiceImpl implements DebitingInvoiceService {
    private final DebitingInvoiceRepository debitingInvoiceRepository;
    private final ProductService productService;
    private final AccountingService accountingService;

    @Override
    public DebitingInvoice findById(long id) {
        return debitingInvoiceRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new RuntimeException("Запроса на списание с id = " + id + " не существует"));
    }

    @Override
    public DebitingInvoice findUnconfirmedInvoiceById(long id) {
        DebitingInvoice debitingInvoice = findById(id);
        if (debitingInvoice.isConfirmed()) {
            throw new RuntimeException("Запрос на списание с id = " + id + " уже подтвержден");
        }
        return debitingInvoice;
    }

    @Transactional
    @Override
    public DebitingInvoice add(DebitingInvoiceDTO debitingInvoiceDTO) {
        //TODO: Проверка на существование склада
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
            //throw new IllegalInvoiceException("Ошибка в количестве товара", HttpStatus.BAD_REQUEST);
            throw new RuntimeException("Запрос на списание с id = " + debitingInvoice.getId() + " некорректен. Возможно, произошла попытка списания большего количества товаров, чем есть на складе");
        }
        return debitingInvoiceRepository.save(debitingInvoice);
    }

    @Override
    public List<DebitingInvoice> getAllUnconfirmedInvoices() {
        return debitingInvoiceRepository.findByIsConfirmedIsFalseAndIsDeletedIsFalse();
    }

    @Transactional
    @Override
    public DebitingInvoice confirmInvoice(long id) {
        DebitingInvoice debitingInvoice = findUnconfirmedInvoiceById(id);
        saveConfirmedInvoice(debitingInvoice);
        return debitingInvoice;
    }

    @Transactional
    @Override
    public List<DebitingInvoice> confirmAllInvoices() {
        List<DebitingInvoice> unconfirmedList = getAllUnconfirmedInvoices();
        if (unconfirmedList.isEmpty()) {
            //throw new IllegalInvoiceException("Накладных для списания нет", HttpStatus.BAD_REQUEST);
            throw new RuntimeException("Накладных для списания нет");
        }

        return unconfirmedList.stream()
                .map(debitingInvoice -> confirmInvoice(debitingInvoice.getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DebitingInvoice deleteInvoice(long id) {
        DebitingInvoice debitingInvoice = findUnconfirmedInvoiceById(id);
        debitingInvoice.setConfirmed(false);
        debitingInvoice.setDeleted(true);
        return debitingInvoice;
    }

    @Transactional
    public void saveConfirmedInvoice(DebitingInvoice debitingInvoice) {
        if (!isDebitingInvoiceCorrect(debitingInvoice)) {
            throw new RuntimeException("Запрос на списание с id = " + debitingInvoice.getId() + " некорректен. Возможно, произошла попытка списания большего количества товаров, чем есть на складе");
            //throw new IllegalInvoiceException("Некорретная накладная. Ошибка в количестве товара", HttpStatus.BAD_REQUEST);
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
