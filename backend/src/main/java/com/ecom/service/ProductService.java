package com.ecom.service;

import com.ecom.dto.response.PaginationResponse;
import com.ecom.dto.response.PhotoResponse;
import com.ecom.dto.response.ProductResponse;
import com.ecom.entity.Category;
import com.ecom.entity.Photo;
import com.ecom.entity.Product;
import com.ecom.exception.BadRequestException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.PhotoRepository;
import com.ecom.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PhotoRepository photoRepository;
    private final ImageService imageService;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          PhotoRepository photoRepository,
                          ImageService imageService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.photoRepository = photoRepository;
        this.imageService = imageService;
    }

    @Transactional(readOnly = true)
    public PaginationResponse<ProductResponse> getAllProducts(
            String search,
            Long categoryId,
            String sort,
            int pageNumber,
            int pageSize) {

        // Adjust 1-based pageNumber to Spring Data 0-based
        int pageIndex = Math.max(0, pageNumber - 1);
        int size = (pageSize <= 0) ? 6 : pageSize;

        Sort sorting = switch (sort != null ? sort : "") {
            case "PriceAsn" -> Sort.by(Sort.Direction.ASC, "newPrice");
            case "PriceDes" -> Sort.by(Sort.Direction.DESC, "newPrice");
            default -> Sort.by(Sort.Direction.ASC, "name");
        };

        Pageable pageable = PageRequest.of(pageIndex, size, sorting);

        String trimmedSearch = (search != null && !search.isBlank()) ? search.trim() : null;
        Long validCategoryId = (categoryId != null && categoryId > 0) ? categoryId : null;

        Page<Product> page = productRepository.searchProducts(validCategoryId, trimmedSearch, pageable);

        List<ProductResponse> productResponses = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PaginationResponse<>(
                pageNumber,
                page.getTotalElements(),
                size,
                productResponses
        );
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found Product id=" + id));
        return mapToResponse(product);
    }

    public ProductResponse addProduct(String name, String description, BigDecimal newPrice, BigDecimal oldPrice, Long categoryId, List<MultipartFile> photos) {
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Product name cannot be empty");
        }
        if (newPrice == null) {
            throw new BadRequestException("Product price is required");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Product product = new Product(name, description, oldPrice, newPrice, category);
        Product savedProduct = productRepository.save(product);

        if (photos != null && !photos.isEmpty()) {
            List<String> imageNames = imageService.saveImages(photos);
            for (String imageName : imageNames) {
                Photo photo = new Photo(imageName, savedProduct);
                savedProduct.addPhoto(photo);
            }
            savedProduct = productRepository.save(savedProduct);
        }

        return mapToResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, String name, String description, BigDecimal newPrice, BigDecimal oldPrice, Long categoryId, List<MultipartFile> photos) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (name != null && !name.isBlank()) {
            product.setName(name);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (newPrice != null) {
            product.setNewPrice(newPrice);
        }
        if (oldPrice != null) {
            product.setOldPrice(oldPrice);
        }
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
            product.setCategory(category);
        }

        if (photos != null && !photos.isEmpty()) {
            // Delete old photo files from disk
            for (Photo oldPhoto : product.getPhotos()) {
                imageService.deleteImage(oldPhoto.getImageName());
            }
            product.getPhotos().clear();

            List<String> imageNames = imageService.saveImages(photos);
            for (String imageName : imageNames) {
                Photo photo = new Photo(imageName, product);
                product.addPhoto(photo);
            }
        }

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Delete photo files
        for (Photo photo : product.getPhotos()) {
            imageService.deleteImage(photo.getImageName());
        }

        productRepository.delete(product);
    }

    public ProductResponse mapToResponse(Product product) {
        List<PhotoResponse> photoResponses = product.getPhotos().stream()
                .map(photo -> new PhotoResponse(photo.getId(), photo.getImageName()))
                .collect(Collectors.toList());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getOldPrice(),
                product.getNewPrice(),
                product.getCategory() != null ? product.getCategory().getName() : "",
                product.getCategory() != null ? product.getCategory().getId() : null,
                photoResponses
        );
    }
}
