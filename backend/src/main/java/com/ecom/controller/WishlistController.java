package com.ecom.controller;

import com.ecom.dto.response.ApiResponse;
import com.ecom.dto.response.WishlistResponse;
import com.ecom.security.UserPrincipal;
import com.ecom.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getUserWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userPrincipal.getEmail()));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponse> addToWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(userPrincipal.getEmail(), productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> removeFromWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId) {
        wishlistService.removeFromWishlist(userPrincipal.getEmail(), productId);
        return ResponseEntity.ok(new ApiResponse(200, "Removed from wishlist"));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Map<String, Boolean>> checkWishlist(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long productId) {
        boolean inWishlist = wishlistService.isInWishlist(userPrincipal.getEmail(), productId);
        return ResponseEntity.ok(Map.of("inWishlist", inWishlist));
    }
}
