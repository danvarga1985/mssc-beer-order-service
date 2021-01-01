package com.danvarga.msscbeerorderservice.web.mappers;

import com.danvarga.msscbeerorderservice.domain.BeerOrderLine;
import com.danvarga.msscbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}

