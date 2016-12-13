package ru.nsu.fit.bro.rest.model;

/**
 * Класс представляет ответ на запрос к REST API на раскодирование изображение
 */
public class StenographyMessageResponse {

    private String message;

    public StenographyMessageResponse() {
    }

    public StenographyMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
