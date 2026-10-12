package com.ecom.controller;

import com.ecom.dto.request.OrderRequest;
import com.ecom.dto.response.OrderResponse;
import com.ecom.entity.DeliveryMethod;
import com.ecom.security.UserPrincipal;
import com.ecom.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping({"", "/create"})
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.createOrder(userPrincipal.getEmail(), orderRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersForUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<OrderResponse> orders = orderService.getUserOrders(userPrincipal.getEmail());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id, userPrincipal.getEmail());
        return ResponseEntity.ok(order);
    }

    @GetMapping("/delivery-methods")
    public ResponseEntity<List<DeliveryMethod>> getDeliveryMethods() {
        return ResponseEntity.ok(orderService.getDeliveryMethods());
    }
}
