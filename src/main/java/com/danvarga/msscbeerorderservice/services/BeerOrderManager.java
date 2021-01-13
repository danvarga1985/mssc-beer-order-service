package com.danvarga.msscbeerorderservice.services;

import com.danvarga.msscbeerorderservice.domain.BeerOrder;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
