package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ActiveAccountRequest {

    @NotBlank(message = "Email is required")
    @Email
    @JsonAlias({"Email", "email"})
    private String email;

    @NotBlank(message = "Token is required")
    @JsonAlias({"Token", "token"})
    private String token;

    public ActiveAccountRequest() {
    }

    public ActiveAccountRequest(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
