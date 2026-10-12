package com.ecom.service;

import com.ecom.dto.request.CategoryRequest;
import com.ecom.dto.response.CategoryResponse;
import com.ecom.entity.Category;
import com.ecom.exception.BadRequestException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found Category id=" + id));
        return mapToResponse(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BadRequestException("Category with name '" + request.getName() + "' already exists");
        }
        Category category = new Category(request.getName(), request.getDescription());
        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    public CategoryResponse updateCategory(CategoryRequest request) {
        if (request.getId() == null) {
            throw new BadRequestException("Category ID must be provided for update");
        }
        Category category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getId()));

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("No category found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }
}
