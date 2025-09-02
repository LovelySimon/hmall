package com.hmall.cart.listener;

import com.hmall.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CartDeleteListener {
    public final ICartService cartService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue( value = "trade.queue",durable = "true"),
            exchange = @Exchange(name = "trade.topic"),
            key = "order.create"
    ))
    public void ListenOrderStatus(Map<String,Object> msg){
        Set<Long> itemIds = (Set<Long>) msg.get("items");
        Long userId = (Long) msg.get("user");
        cartService.removeByItemIds(itemIds,userId);
    }
}