package com.danvarga.msscbeerorderservice.services.listeners;

import com.danvarga.brewery.model.events.ValidateOrderResult;
import com.danvarga.msscbeerorderservice.config.JmsConfig;
import com.danvarga.msscbeerorderservice.services.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult result) throws InterruptedException {
        final UUID beerOrderId = result.getOrderId();

        log.debug("Validation Result from Order Id: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, result.getIsValid());
    }
}
