package com.ecom.controller;

import com.ecom.dto.response.ApiResponse;
import com.ecom.dto.response.PaginationResponse;
import com.ecom.dto.response.ProductResponse;
import com.ecom.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/Products")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"", "/get-all"})
    public ResponseEntity<PaginationResponse<ProductResponse>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, name = "Sort") String sort,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "6") int pageSize) {

        PaginationResponse<ProductResponse> response = productService.getAllProducts(
                search, categoryId, sort, pageNumber, pageSize
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/{id}", "/get-by-id/{id}"})
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/add-Product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> addProduct(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("newPrice") BigDecimal newPrice,
            @RequestParam(value = "oldPrice", required = false) BigDecimal oldPrice,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {

        productService.addProduct(name, description, newPrice, oldPrice, categoryId, photos);
        return ResponseEntity.ok(new ApiResponse(200, "Product added successfully"));
    }

    @PutMapping(value = "/update-Product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestParam("id") Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("newPrice") BigDecimal newPrice,
            @RequestParam(value = "oldPrice", required = false) BigDecimal oldPrice,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {

        productService.updateProduct(id, name, description, newPrice, oldPrice, categoryId, photos);
        return ResponseEntity.ok(new ApiResponse(200, "Product updated successfully"));
    }

    @DeleteMapping("/delete-Product/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse(200, "Product deleted successfully"));
    }
}
