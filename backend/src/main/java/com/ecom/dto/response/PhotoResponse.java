package com.ecom.dto.response;

public class PhotoResponse {

    private Long id;
    private String imageName;

    public PhotoResponse() {
    }

    public PhotoResponse(Long id, String imageName) {
        this.id = id;
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
