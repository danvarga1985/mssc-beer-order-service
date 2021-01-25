package com.danvarga.msscbeerorderservice.web.mappers;

import com.danvarga.brewery.model.CustomerDto;
import com.danvarga.msscbeerorderservice.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
