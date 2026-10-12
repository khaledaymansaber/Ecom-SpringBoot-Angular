package com.ecom.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private int statusCode;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(int statusCode) {
        this.statusCode = statusCode;
        this.message = getDefaultMessage(statusCode);
    }

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message != null ? message : getDefaultMessage(statusCode);
    }

    private String getDefaultMessage(int code) {
        return switch (code) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 500 -> "Internal Server Error";
            default -> "Status " + code;
        };
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
