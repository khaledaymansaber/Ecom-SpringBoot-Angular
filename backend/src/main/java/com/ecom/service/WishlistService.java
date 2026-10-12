package com.ecom.service;

import com.ecom.dto.response.WishlistResponse;
import com.ecom.entity.AppUser;
import com.ecom.entity.Product;
import com.ecom.entity.WishlistItem;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public WishlistService(WishlistRepository wishlistRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           ProductService productService) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<WishlistResponse> getUserWishlist(String userEmail) {
        AppUser user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return wishlistRepository.findByUserId(user.getId()).stream()
                .map(item -> new WishlistResponse(
                        item.getId(),
                        productService.mapToResponse(item.getProduct()),
                        item.getAddedAt()
                ))
                .collect(Collectors.toList());
    }

    public WishlistResponse addToWishlist(String userEmail, Long productId) {
        AppUser user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

        if (wishlistRepository.existsByUserIdAndProductId(user.getId(), productId)) {
            return wishlistRepository.findByUserId(user.getId()).stream()
                    .filter(w -> w.getProduct().getId().equals(productId))
                    .findFirst()
                    .map(item -> new WishlistResponse(
                            item.getId(),
                            productService.mapToResponse(item.getProduct()),
                            item.getAddedAt()
                    ))
                    .orElse(null);
        }

        WishlistItem item = new WishlistItem(user, product);
        WishlistItem saved = wishlistRepository.save(item);

        return new WishlistResponse(
                saved.getId(),
                productService.mapToResponse(saved.getProduct()),
                saved.getAddedAt()
        );
    }

    public void removeFromWishlist(String userEmail, Long productId) {
        AppUser user = userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        wishlistRepository.deleteByUserIdAndProductId(user.getId(), productId);
    }

    @Transactional(readOnly = true)
    public boolean isInWishlist(String userEmail, Long productId) {
        AppUser user = userRepository.findByEmailIgnoreCase(userEmail).orElse(null);
        if (user == null) {
            return false;
        }
        return wishlistRepository.existsByUserIdAndProductId(user.getId(), productId);
    }
}
