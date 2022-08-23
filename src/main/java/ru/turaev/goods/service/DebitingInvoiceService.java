package ru.turaev.goods.service;

import ru.turaev.goods.dto.DebitingInvoiceDto;
import ru.turaev.goods.model.DebitingInvoice;

import java.time.LocalDate;
import java.util.List;

public interface DebitingInvoiceService {
    DebitingInvoice findById(long id);

    DebitingInvoice findUnconfirmedInvoiceById(long id);

    DebitingInvoice add(DebitingInvoiceDto debitingInvoiceDTO);

    List<DebitingInvoice> getAllUnconfirmedInvoices();

    DebitingInvoice confirmInvoice(long id);

    DebitingInvoice deleteInvoice(long id);

    List<DebitingInvoice> confirmAllInvoices();

    List<DebitingInvoiceDto> getDebitingInvoicesByPeriod(long id, LocalDate begin, LocalDate end);
}
