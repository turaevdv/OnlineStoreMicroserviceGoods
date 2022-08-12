package ru.turaev.goods.service;

import ru.turaev.goods.dto.DebitingInvoiceDTO;
import ru.turaev.goods.model.DebitingInvoice;

import java.util.List;

public interface DebitingInvoiceService {
    DebitingInvoice findById(long id);

    DebitingInvoice findUnconfirmedInvoiceById(long id);

    DebitingInvoice add(DebitingInvoiceDTO debitingInvoiceDTO);

    List<DebitingInvoice> getAllUnconfirmedInvoices();

    DebitingInvoice confirmInvoice(long id);

    DebitingInvoice deleteInvoice(long id);

    List<DebitingInvoice> confirmAllInvoices();
}
