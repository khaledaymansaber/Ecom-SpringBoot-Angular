package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {

    @NotBlank(message = "Basket ID is required")
    @JsonAlias({"BasketId", "basketId"})
    private String basketId;

    @NotNull(message = "Delivery method ID is required")
    @JsonAlias({"DeliveryMethodId", "deliveryMethodId"})
    private Long deliveryMethodId;

    @NotNull(message = "Shipping address is required")
    @Valid
    @JsonAlias({"ShippingAddress", "shippingAddress"})
    private AddressRequest shippingAddress;

    public OrderRequest() {
    }

    public OrderRequest(String basketId, Long deliveryMethodId, AddressRequest shippingAddress) {
        this.basketId = basketId;
        this.deliveryMethodId = deliveryMethodId;
        this.shippingAddress = shippingAddress;
    }

    public String getBasketId() {
        return basketId;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public AddressRequest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressRequest shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
