package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @JsonAlias({"UserName", "userName", "username"})
    private String userName;

    @NotBlank(message = "Display name is required")
    @JsonAlias({"DisplayName", "displayName", "displayname"})
    private String displayName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonAlias({"Email", "email"})
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @JsonAlias({"Password", "password"})
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String userName, String displayName, String email, String password) {
        this.userName = userName;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
}
