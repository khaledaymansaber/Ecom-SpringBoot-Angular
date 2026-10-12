package com.ecom.dto.response;

import java.time.LocalDateTime;

public class WishlistResponse {

    private Long id;
    private ProductResponse product;
    private LocalDateTime addedAt;

    public WishlistResponse() {
    }

    public WishlistResponse(Long id, ProductResponse product, LocalDateTime addedAt) {
        this.id = id;
        this.product = product;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
}
