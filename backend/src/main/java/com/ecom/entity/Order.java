package com.ecom.entity;

import com.ecom.entity.enums.OrderStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "buyer_email", nullable = false)
    private String buyerEmail;

    @Column(name = "sub_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal subTotal;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delivery_method_id")
    private DeliveryMethod deliveryMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    public Order() {
    }

    public Order(String buyerEmail, BigDecimal subTotal, Address shippingAddress, DeliveryMethod deliveryMethod, List<OrderItem> orderItems) {
        this.buyerEmail = buyerEmail;
        this.subTotal = subTotal;
        this.shippingAddress = shippingAddress;
        this.deliveryMethod = deliveryMethod;
        this.orderItems = orderItems;
        if (orderItems != null) {
            orderItems.forEach(item -> item.setOrder(this));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        if (orderItems != null) {
            orderItems.forEach(item -> item.setOrder(this));
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        BigDecimal shippingCost = deliveryMethod != null ? deliveryMethod.getPrice() : BigDecimal.ZERO;
        return (subTotal != null ? subTotal : BigDecimal.ZERO).add(shippingCost);
    }
}
