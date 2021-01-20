package com.danvarga.msscbeerorderservice.sm;

import com.danvarga.msscbeerorderservice.domain.BeerOrder;
import com.danvarga.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.danvarga.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.danvarga.msscbeerorderservice.repositories.BeerOrderRepository;
import com.danvarga.msscbeerorderservice.services.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public void preStateChange(State<BeerOrderStatusEnum, BeerOrderEventEnum> state, Message<BeerOrderEventEnum> message,
                               Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition,
                               StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {

        log.debug("Pre-state change");

        Optional.ofNullable(message)
                .flatMap(msg ->
                        Optional.ofNullable((String) msg.getHeaders()
                                .getOrDefault(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER, " ")))
                .ifPresent(beerOrderId -> {
                    log.debug("Saving state for beer order id: " + beerOrderId + "Status: " + state.getId());

                    BeerOrder beerOrder = beerOrderRepository.getOne(UUID.fromString(beerOrderId));
                    beerOrder.setOrderStatus(state.getId());
                    // Hibernate by default does lazy write, but in this case timing is tight -> saveAndFlush.
                    beerOrderRepository.saveAndFlush(beerOrder);
                });

    }
}
