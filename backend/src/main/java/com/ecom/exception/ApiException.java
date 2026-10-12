package com.ecom.exception;

import com.ecom.dto.response.ApiResponse;

public class ApiException extends ApiResponse {

    private String details;

    public ApiException(int statusCode, String message, String details) {
        super(statusCode, message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
