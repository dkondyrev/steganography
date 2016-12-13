package ru.nsu.fit.bro.rest.model;

/**
 * Класс представляет ответ на запрос к REST API на кодирование изображение
 */
public class StenographyImageResponse {

    private String image;

    public StenographyImageResponse() {
    }

    public StenographyImageResponse(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
