package com.danvarga.msscbeerorderservice.sm.actions;

import com.danvarga.brewery.model.events.AllocateOrderRequest;
import com.danvarga.brewery.model.events.DeallocateOrderRequest;
import com.danvarga.msscbeerorderservice.config.JmsConfig;
import com.danvarga.msscbeerorderservice.domain.BeerOrder;
import com.danvarga.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.danvarga.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.danvarga.msscbeerorderservice.repositories.BeerOrderRepository;
import com.danvarga.msscbeerorderservice.services.BeerOrderManagerImpl;
import com.danvarga.msscbeerorderservice.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeallocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;


    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER);

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
                    DeallocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent Deallocation Request for order id: " + beerOrderId);
        }, () -> log.error("Beer Order Not Found!"));

    }

}
