package com.danvarga.msscbeerorderservice.services;

import com.danvarga.msscbeerorderservice.bootstrap.BeerOrderLoader;
import com.danvarga.msscbeerorderservice.domain.Customer;
import com.danvarga.msscbeerorderservice.repositories.BeerOrderRepository;
import com.danvarga.msscbeerorderservice.repositories.CustomerRepository;
import com.danvarga.brewery.model.BeerOrderDto;
import com.danvarga.brewery.model.BeerOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    private final List<String> beerUpcs = new ArrayList<>(3);

    public TastingRoomService(CustomerRepository customerRepository, BeerOrderService beerOrderService) {
        this.customerRepository = customerRepository;
        this.beerOrderService = beerOrderService;

        beerUpcs.add(BeerOrderLoader.BEER_1_UPC);
        beerUpcs.add(BeerOrderLoader.BEER_2_UPC);
        beerUpcs.add(BeerOrderLoader.BEER_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeTastingRoomOrder(){

        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(BeerOrderLoader.TASTING_ROOM);

        if (customerList.size() == 1){ //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");

            customerList.forEach(customer -> log.debug(customer.toString()));
        }
    }

    private void doPlaceOrder(Customer customer) {
        String beerToOrder = getRandomBeerUpc();

        BeerOrderLineDto beerOrderLine = BeerOrderLineDto.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(6)) //todo externalize value to property
                .build();

        List<BeerOrderLineDto> beerOrderLineSet = new ArrayList<>();
        beerOrderLineSet.add(beerOrderLine);

        BeerOrderDto beerOrder = BeerOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .beerOrderLines(beerOrderLineSet)
                .build();

        BeerOrderDto savedOrder = beerOrderService.placeOrder(customer.getId(), beerOrder);

    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size() -0));
    }
}