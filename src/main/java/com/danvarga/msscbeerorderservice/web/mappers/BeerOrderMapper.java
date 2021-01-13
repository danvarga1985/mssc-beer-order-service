package com.danvarga.msscbeerorderservice.web.mappers;

import com.danvarga.msscbeerorderservice.domain.BeerOrder;
import com.danvarga.brewery.model.BeerOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    // Customer is an Object on BeerOrder, so the mapping has to be more specific. Target = dto, Source = Customer obj.
    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}