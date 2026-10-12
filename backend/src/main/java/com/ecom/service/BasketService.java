package com.ecom.service;

import com.ecom.entity.BasketItem;
import com.ecom.entity.CustomerBasket;
import com.ecom.repository.BasketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Transactional(readOnly = true)
    public CustomerBasket getBasket(String id) {
        return basketRepository.findById(id).orElseGet(() -> new CustomerBasket(id));
    }

    public CustomerBasket updateBasket(CustomerBasket basket) {
        if (basket.getId() == null || basket.getId().isBlank()) {
            return null;
        }

        if (basket.getItems() != null) {
            for (BasketItem item : basket.getItems()) {
                item.setBasket(basket);
            }
        }

        return basketRepository.findById(basket.getId())
                .map(existing -> {
                    existing.getItems().clear();
                    if (basket.getItems() != null) {
                        for (BasketItem item : basket.getItems()) {
                            BasketItem newItem = new BasketItem(
                                    item.getItemId(),
                                    item.getName(),
                                    item.getPrice(),
                                    item.getQuantity(),
                                    item.getCategory(),
                                    item.getImage(),
                                    item.getDescription()
                            );
                            existing.addItem(newItem);
                        }
                    }
                    return basketRepository.save(existing);
                })
                .orElseGet(() -> basketRepository.save(basket));
    }

    public boolean deleteBasket(String id) {
        if (id != null && basketRepository.existsById(id)) {
            basketRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
