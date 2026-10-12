package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email
    @JsonAlias({"Email", "email"})
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonAlias({"Password", "password"})
    private String password;

    @NotBlank(message = "Token is required")
    @JsonAlias({"Token", "token"})
    private String token;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String email, String password, String token) {
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
