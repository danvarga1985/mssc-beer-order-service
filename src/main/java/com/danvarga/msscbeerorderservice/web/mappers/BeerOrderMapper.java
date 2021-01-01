package com.danvarga.msscbeerorderservice.web.mappers;

import com.danvarga.msscbeerorderservice.web.model.BeerOrderDto;
import com.danvarga.msscbeerorderservice.domain.BeerOrder;
import org.mapstruct.Mapper;

import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}