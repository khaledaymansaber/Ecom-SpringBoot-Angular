package com.ecom.dto.response;

import com.ecom.entity.Address;

public class UserProfileResponse {

    private Long id;
    private String displayName;
    private String userName;
    private String email;
    private Address address;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Long id, String displayName, String userName, String email, Address address) {
        this.id = id;
        this.displayName = displayName;
        this.userName = userName;
        this.email = email;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
