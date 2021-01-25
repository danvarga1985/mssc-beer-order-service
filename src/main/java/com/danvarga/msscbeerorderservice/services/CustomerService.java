package com.danvarga.msscbeerorderservice.services;

import com.danvarga.brewery.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerPagedList listCustomers(Pageable pageable);
}
