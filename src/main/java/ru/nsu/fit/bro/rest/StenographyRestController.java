package ru.nsu.fit.bro.rest;

import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.bro.algorithms.Coder;
import ru.nsu.fit.bro.rest.model.StenographyResponse;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/rest/stenography")
public class StenographyRestController {

    @RequestMapping(value = "/encode", method = RequestMethod.POST)
    StenographyResponse encode(@RequestBody String data,
                               @RequestHeader(value="message") String message,
                               @RequestHeader(value="key") long key) throws IOException {
        byte[] image = Base64.getDecoder().decode(data);
        Coder coder = new Coder();
        byte[] codedImage = coder.code(message, image, key);
        return new StenographyResponse(Base64.getEncoder().encodeToString(codedImage));
    }





}
