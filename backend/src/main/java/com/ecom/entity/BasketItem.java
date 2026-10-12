package com.ecom.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "basket_items")
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long internalId;

    /**
     * The product ID – Angular sends this as "id" in the JSON.
     * Maps to the "item_id" column in the database.
     */
    @Column(name = "item_id", nullable = false)
    @JsonProperty("id")
    @JsonAlias({"id", "itemId"})
    private Long itemId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String category;

    @Column
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private CustomerBasket basket;

    public BasketItem() {
    }

    public BasketItem(Long itemId, String name, BigDecimal price, int quantity, String category, String image, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
        this.description = description;
    }

    @JsonIgnore
    public Long getInternalId() {
        return internalId;
    }

    @JsonIgnore
    public void setInternalId(Long internalId) {
        this.internalId = internalId;
    }

    @JsonProperty("id")
    @JsonAlias({"id", "itemId"})
    public Long getItemId() {
        return itemId;
    }

    @JsonProperty("id")
    @JsonAlias({"id", "itemId"})
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @JsonIgnore
    public Long getId() {
        return itemId;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.itemId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerBasket getBasket() {
        return basket;
    }

    public void setBasket(CustomerBasket basket) {
        this.basket = basket;
    }
}
