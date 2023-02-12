package com.deepak.orderservice.service;

import com.deepak.orderservice.dto.InventoryResponse;
import com.deepak.orderservice.dto.OrderLineItemsDto;
import com.deepak.orderservice.dto.OrderRequest;
import com.deepak.orderservice.model.Order;
import com.deepak.orderservice.model.OrderLineItems;
import com.deepak.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        log.info("order request:{}",orderRequest);

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(orderLineItem -> orderLineItem.getSkucode())
                .toList();
        log.info("sku codes for ordering: {}",skuCodes);

        //call inventory service & place order only when product is availabe in stock.
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://InventoryService/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)//to be able to read response.
                .block();//this method makes the call synchronusly.

        log.info("inventoryResponses {}",inventoryResponses);
        boolean allproductsInStock = Arrays.stream(inventoryResponses)
                .allMatch(inventoryResponse -> inventoryResponse.isInStock());

        if(allproductsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Sorry! Product is out of stock");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkucode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
