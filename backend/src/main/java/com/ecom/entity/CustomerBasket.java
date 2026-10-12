package com.ecom.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_baskets")
public class CustomerBasket {

    @Id
    @Column(nullable = false, length = 64)
    private String id;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BasketItem> items = new ArrayList<>();

    public CustomerBasket() {
    }

    public CustomerBasket(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
        if (items != null) {
            items.forEach(item -> item.setBasket(this));
        }
    }

    public void addItem(BasketItem item) {
        items.add(item);
        item.setBasket(this);
    }
}
