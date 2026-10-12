package com.ecom.service;

import com.ecom.dto.request.AddressRequest;
import com.ecom.dto.request.OrderRequest;
import com.ecom.dto.response.OrderItemResponse;
import com.ecom.dto.response.OrderResponse;
import com.ecom.entity.*;
import com.ecom.entity.enums.OrderStatus;
import com.ecom.exception.BadRequestException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.BasketRepository;
import com.ecom.repository.DeliveryMethodRepository;
import com.ecom.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;

    public OrderService(OrderRepository orderRepository,
                        BasketRepository basketRepository,
                        DeliveryMethodRepository deliveryMethodRepository) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.deliveryMethodRepository = deliveryMethodRepository;
    }

    public OrderResponse createOrder(String buyerEmail, OrderRequest request) {
        CustomerBasket basket = basketRepository.findById(request.getBasketId())
                .orElseThrow(() -> new BadRequestException("Basket not found with id: " + request.getBasketId()));

        if (basket.getItems() == null || basket.getItems().isEmpty()) {
            throw new BadRequestException("Cannot create an order with an empty basket");
        }

        DeliveryMethod deliveryMethod = deliveryMethodRepository.findById(request.getDeliveryMethodId())
                .orElseThrow(() -> new BadRequestException("Delivery method not found with id: " + request.getDeliveryMethodId()));

        // Create order items from basket items
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.ZERO;

        for (BasketItem item : basket.getItems()) {
            OrderItem orderItem = new OrderItem(
                    item.getItemId(),
                    item.getName(),
                    item.getImage(),
                    item.getPrice(),
                    item.getQuantity()
            );
            orderItems.add(orderItem);
            subTotal = subTotal.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Create shipping address
        AddressRequest addrReq = request.getShippingAddress();
        Address shippingAddress = new Address(
                addrReq.getFirstName(),
                addrReq.getLastName(),
                addrReq.getStreet(),
                addrReq.getCity(),
                addrReq.getState(),
                addrReq.getZipCode()
        );

        Order order = new Order(
                buyerEmail,
                subTotal,
                shippingAddress,
                deliveryMethod,
                orderItems
        );
        order.setStatus(OrderStatus.PAYMENT_RECEIVED);

        Order savedOrder = orderRepository.save(order);

        // Clean up basket after successful checkout
        basketRepository.deleteById(basket.getId());

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(String buyerEmail) {
        return orderRepository.findByBuyerEmailOrderByOrderDateDesc(buyerEmail).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id, String buyerEmail) {
        Order order = orderRepository.findByIdAndBuyerEmail(id, buyerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethodRepository.findAll();
    }

    private OrderResponse mapToResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setBuyerEmail(order.getBuyerEmail());
        response.setOrderDate(order.getOrderDate());
        response.setShippingAddress(order.getShippingAddress());
        response.setDeliveryMethod(order.getDeliveryMethod());
        response.setShippingPrice(order.getDeliveryMethod() != null ? order.getDeliveryMethod().getPrice() : BigDecimal.ZERO);
        response.setSubTotal(order.getSubTotal());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus());

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProductItemId(),
                        item.getProductName(),
                        item.getMainImage(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
        response.setOrderItems(itemResponses);

        return response;
    }
}
