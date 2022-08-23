package ru.turaev.goods.mapper;

import ru.turaev.goods.dto.DebitingInvoiceDto;
import ru.turaev.goods.model.DebitingInvoice;

public interface DebitingInvoiceMapper {
    DebitingInvoiceDto toDto(DebitingInvoice debitingInvoice);
}
