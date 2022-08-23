package ru.turaev.goods.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DebitingInvoiceDto {
    private List<GoodsAndQuantityDto> goodsAndQuantities;
    private long storehouseId;
    private LocalDate invoiceDate;
    private LocalTime invoiceTime;
}
