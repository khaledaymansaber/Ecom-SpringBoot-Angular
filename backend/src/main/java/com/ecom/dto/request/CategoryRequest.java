package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

    @JsonAlias({"Id", "id"})
    private Long id;

    @NotBlank(message = "Category name is required")
    @JsonAlias({"Name", "name"})
    private String name;

    @JsonAlias({"Description", "description"})
    private String description;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
