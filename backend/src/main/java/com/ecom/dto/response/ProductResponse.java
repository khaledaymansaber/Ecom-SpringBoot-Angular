package com.ecom.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String categoryName;
    private Long categoryId;
    private List<PhotoResponse> photos = new ArrayList<>();

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, String description, BigDecimal oldPrice, BigDecimal newPrice, String categoryName, Long categoryId, List<PhotoResponse> photos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<PhotoResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoResponse> photos) {
        this.photos = photos;
    }
}
