package ru.turaev.goods.dto;

import lombok.Data;

@Data
public class GoodsAndQuantityDto {
    private long productId;
    private int quantity;
}
