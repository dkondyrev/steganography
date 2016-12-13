package ru.nsu.fit.bro.rest;

import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bro.algorithms.Coder;
import ru.nsu.fit.bro.algorithms.Decoder;
import ru.nsu.fit.bro.rest.model.StenographyImageResponse;
import ru.nsu.fit.bro.rest.model.StenographyMessageResponse;

import java.io.IOException;
import java.util.Base64;

/**
 * Класс реаизует REST API к сервису онлайн стеганографии
 */
@RestController
@RequestMapping("/rest/stenography")
public class StenographyRestController {

    /**
     * Метод предоставлет rest service для кодирования изображений.
     * @param data изображение в формате base64.
     * @param message сообщение которое необходимо закодировать
     * @param key ключ для кодировани
     * @return возвращается закодирование изоражение в json {"image":"закодирование сообщение в base64"}
     * @throws IOException
     */
    @RequestMapping(value = "/encode", method = RequestMethod.POST)
    StenographyImageResponse encode(@RequestBody String data,
                                    @RequestHeader(value="message") String message,
                                    @RequestHeader(value="key") long key) throws IOException {

        byte[] image = Base64.getMimeDecoder().decode(data);
        Coder coder = new Coder();
        byte[] codedImage = coder.code(message, image, key);

        return new StenographyImageResponse(Base64.getEncoder().encodeToString(codedImage));
    }

    /**
     * Метод предоставлет rest service для раскодирования изображений.
     * @param data изображение в формате base64.
     * @param key ключ используемый при кодировании
     * @return возвращается закодирование сообщение в json {"message":"..."}
     * @throws IOException
     */
    @RequestMapping(value = "/decode", method = RequestMethod.POST)
    StenographyMessageResponse decode(@RequestBody String data,
                                      @RequestHeader(value="key") long key) throws IOException {

        byte[] image = Base64.getDecoder().decode(data);
        Decoder decoder = new Decoder();
        String message = decoder.decode(image, key);

        return new StenographyMessageResponse(message);
    }
}
