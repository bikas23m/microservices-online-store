package com.bikas.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.bikas.orderservice.dto.InventoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bikas.orderservice.dto.OrderLineItemsDto;
import com.bikas.orderservice.dto.OrderRequest;
import com.bikas.orderservice.model.Order;
import com.bikas.orderservice.model.OrderLineItems;
import com.bikas.orderservice.repository.OrderRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepo orderRepository;
	private final WebClient.Builder webClientBuilder;
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLineItemList(orderLineItems);

		List<String> skuCodes = order.getOrderLineItemList().stream()
				.map(OrderLineItems::getSkuCode)
				.toList();

		//call inventory service and place order if product is I stock.
		InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
				.retrieve()
				.bodyToMono(InventoryResponse[].class)
				.block();

		boolean allProductInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

		if(allProductInStock){
			orderRepository.save(order);
		}else{
			throw new IllegalArgumentException("Product is not in stock, please try again later.");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

		return OrderLineItems.builder().price(orderLineItemsDto.getPrice()).quantity(orderLineItemsDto.getQuantity())
				.skuCode(orderLineItemsDto.getSkuCode()).build();
	}
}
