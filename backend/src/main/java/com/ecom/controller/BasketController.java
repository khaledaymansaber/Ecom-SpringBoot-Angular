package com.ecom.controller;

import com.ecom.dto.response.ApiResponse;
import com.ecom.entity.CustomerBasket;
import com.ecom.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerBasket> getBasketById(@PathVariable String id) {
        CustomerBasket basket = basketService.getBasket(id);
        return ResponseEntity.ok(basket);
    }

    @PostMapping
    public ResponseEntity<CustomerBasket> updateBasket(@RequestBody CustomerBasket basket) {
        CustomerBasket updated = basketService.updateBasket(basket);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBasketPath(@PathVariable String id) {
        basketService.deleteBasket(id);
        return ResponseEntity.ok(new ApiResponse(200, "Basket deleted"));
    }

    // Support query param deletion: DELETE /api/Basket?id=... (used in Angular BasketService)
    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteBasketQuery(@RequestParam("id") String id) {
        return deleteBasketPath(id);
    }
}
