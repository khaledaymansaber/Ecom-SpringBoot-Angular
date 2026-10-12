package com.ecom.dto.response;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private Long productItemId;
    private String productName;
    private String mainImage;
    private BigDecimal price;
    private int quantity;

    public OrderItemResponse() {
    }

    public OrderItemResponse(Long id, Long productItemId, String productName, String mainImage, BigDecimal price, int quantity) {
        this.id = id;
        this.productItemId = productItemId;
        this.productName = productName;
        this.mainImage = mainImage;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Long productItemId) {
        this.productItemId = productItemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
