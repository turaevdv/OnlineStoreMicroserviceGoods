package ru.turaev.goods.dto;

import lombok.Data;

@Data
public class GoodsAndQuantityDTO {
    private long productId;
    private int quantity;
}
