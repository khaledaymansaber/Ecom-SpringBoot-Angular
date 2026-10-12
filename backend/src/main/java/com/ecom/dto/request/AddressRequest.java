package com.ecom.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank(message = "First name is required")
    @JsonAlias({"FirstName", "firstName"})
    private String firstName;

    @NotBlank(message = "Last name is required")
    @JsonAlias({"LastName", "lastName"})
    private String lastName;

    @NotBlank(message = "Street is required")
    @JsonAlias({"Street", "street"})
    private String street;

    @NotBlank(message = "City is required")
    @JsonAlias({"City", "city"})
    private String city;

    @NotBlank(message = "State is required")
    @JsonAlias({"State", "state"})
    private String state;

    @NotBlank(message = "Zip code is required")
    @JsonAlias({"ZipCode", "zipCode"})
    private String zipCode;

    public AddressRequest() {
    }

    public AddressRequest(String firstName, String lastName, String street, String city, String state, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
