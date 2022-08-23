package ru.turaev.goods.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.turaev.goods.dto.DebitingInvoiceDto;
import ru.turaev.goods.dto.GoodsAndQuantityDto;
import ru.turaev.goods.mapper.DebitingInvoiceMapper;
import ru.turaev.goods.mapper.GoodsAndQuantityMapper;
import ru.turaev.goods.model.DebitingInvoice;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DebitingInvoiceMapperImpl implements DebitingInvoiceMapper {
    private final GoodsAndQuantityMapper goodsAndQuantityMapper;

    @Override
    public DebitingInvoiceDto toDto(DebitingInvoice debitingInvoice) {
        DebitingInvoiceDto debitingInvoiceDto = new DebitingInvoiceDto();
        debitingInvoiceDto.setStorehouseId(debitingInvoice.getStorehouseId());
        debitingInvoiceDto.setInvoiceDate(debitingInvoice.getInvoiceDate());
        debitingInvoiceDto.setInvoiceTime(debitingInvoice.getInvoiceTime());
        List<GoodsAndQuantityDto> goodsAndQuantityDtos = debitingInvoice.getGoodsAndQuantities()
                .stream()
                .map(goodsAndQuantityMapper::toDto)
                .collect(Collectors.toList());
        debitingInvoiceDto.setGoodsAndQuantities(goodsAndQuantityDtos);
        return debitingInvoiceDto;
    }
}
