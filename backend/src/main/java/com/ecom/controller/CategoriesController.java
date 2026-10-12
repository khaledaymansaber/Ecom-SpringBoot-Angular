package com.ecom.controller;

import com.ecom.dto.request.CategoryRequest;
import com.ecom.dto.response.ApiResponse;
import com.ecom.dto.response.CategoryResponse;
import com.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Categories")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"", "/get-all"})
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping({"/{id}", "/get-by-id/{id}"})
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody CategoryRequest categoryDTO) {
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(new ApiResponse(200, "item has been added"));
    }

    @PutMapping("/update-category")
    public ResponseEntity<ApiResponse> updateCategory(@Valid @RequestBody CategoryRequest categoryDTO) {
        categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(new ApiResponse(200, "item has been updated"));
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse(200, "item has been deleted"));
    }
}
