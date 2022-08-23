package ru.turaev.goods.mapper.impl;

import org.springframework.stereotype.Component;
import ru.turaev.goods.dto.GoodsAndQuantityDto;
import ru.turaev.goods.mapper.GoodsAndQuantityMapper;
import ru.turaev.goods.model.GoodsAndQuantity;

@Component
public class GoodsAndQuantityMapperImpl implements GoodsAndQuantityMapper {
    @Override
    public GoodsAndQuantityDto toDto(GoodsAndQuantity goodsAndQuantity) {
        GoodsAndQuantityDto goodsAndQuantityDto = new GoodsAndQuantityDto();
        goodsAndQuantityDto.setQuantity(goodsAndQuantity.getQuantity());
        goodsAndQuantityDto.setProductId(goodsAndQuantity.getProduct().getId());
        return goodsAndQuantityDto;
    }
}
