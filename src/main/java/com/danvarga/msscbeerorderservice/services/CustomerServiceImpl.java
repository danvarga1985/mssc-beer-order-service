package com.danvarga.msscbeerorderservice.services;

import com.danvarga.brewery.model.CustomerPagedList;
import com.danvarga.msscbeerorderservice.domain.Customer;
import com.danvarga.msscbeerorderservice.repositories.CustomerRepository;
import com.danvarga.msscbeerorderservice.web.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerPagedList listCustomers(Pageable pageable) {

        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return new CustomerPagedList(customerPage
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList()),
                PageRequest.of(customerPage.getPageable().getPageNumber(),
                        customerPage.getPageable().getPageSize()),
                customerPage.getTotalElements());
    }
}
