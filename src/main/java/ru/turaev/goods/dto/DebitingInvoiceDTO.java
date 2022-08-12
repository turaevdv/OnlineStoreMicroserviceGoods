package ru.turaev.goods.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DebitingInvoiceDTO {
    private List<GoodsAndQuantityDTO> goodsAndQuantitiesDTO;
    private long storehouseId;
    private LocalDate invoiceDate;
    private LocalTime invoiceTime;
}
