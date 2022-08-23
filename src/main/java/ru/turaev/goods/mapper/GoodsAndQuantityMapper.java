package ru.turaev.goods.mapper;

import ru.turaev.goods.dto.GoodsAndQuantityDto;
import ru.turaev.goods.model.GoodsAndQuantity;

public interface GoodsAndQuantityMapper {
    GoodsAndQuantityDto toDto(GoodsAndQuantity goodsAndQuantity);
}
