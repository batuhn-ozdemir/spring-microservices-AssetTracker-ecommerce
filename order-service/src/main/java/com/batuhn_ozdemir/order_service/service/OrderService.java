package com.batuhn_ozdemir.order_service.service;

import com.batuhn_ozdemir.order_service.client.InventoryClient;
import com.batuhn_ozdemir.order_service.dto.InventoryResponse;
import com.batuhn_ozdemir.order_service.dto.OrderLineItemsDto;
import com.batuhn_ozdemir.order_service.dto.OrderRequest;
import com.batuhn_ozdemir.order_service.event.OrderPlacedEvent;
import com.batuhn_ozdemir.order_service.model.Order;
import com.batuhn_ozdemir.order_service.model.OrderLineItems;
import com.batuhn_ozdemir.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItems().stream()
                .map(OrderService::mapToDto)
                .toList();

        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        List<InventoryResponse> inventoryResponseList = inventoryClient.checkStock(skuCodes);

        boolean allProductsInStock = inventoryResponseList.stream()
                .allMatch(InventoryResponse::getInStock);

        if (allProductsInStock && !inventoryResponseList.isEmpty()) {
            orderRepository.save(order);
            for (OrderLineItemsDto item : orderRequest.getOrderLineItems()) {
                inventoryClient.reduceStock(item.getSkuCode(), item.getQuantity());
            }
            rabbitTemplate.convertAndSend("internal.exchange", "internal.notification.routing-key", new OrderPlacedEvent(order.getOrderNumber()));
        } else {
            throw new IllegalArgumentException("Order failed: One or more requested items are out of stock.");
        }
    }

    private static OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
