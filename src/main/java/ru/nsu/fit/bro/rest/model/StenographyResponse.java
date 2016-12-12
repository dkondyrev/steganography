package ru.nsu.fit.bro.rest.model;

public class StenographyResponse {

    private String image;

    public StenographyResponse() {
    }

    public StenographyResponse(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
