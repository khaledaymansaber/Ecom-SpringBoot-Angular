package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {

    @NotBlank(message = "Display name is required")
    @JsonAlias({"DisplayName", "displayName"})
    private String displayName;

    public UpdateProfileRequest() {
    }

    public UpdateProfileRequest(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
