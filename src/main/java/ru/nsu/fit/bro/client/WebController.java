package ru.nsu.fit.bro.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.bro.rest.model.StenographyImageResponse;

import java.io.*;
import java.util.Base64;

@Controller
public class WebController {

    @RequestMapping("/stenography")
    public String providedStenographyPage(Model model) {
        return "stenography";
    }

    @RequestMapping("/stenography/coder")
    public String providedCoderPage(Model model) {

        return "stenography/coder";
    }

    @RequestMapping("/stenography/decoder")
    public String providedDecoderPage(Model model) {
        return "stenography/decoder";
    }

    @RequestMapping(value = "/stenography/coder-result", method = RequestMethod.POST)
    public String providedCoderResultPage(@RequestParam("message") String message,
                                          @RequestParam("number") String number,
                                          @RequestParam("image") MultipartFile image,
                                          Model model) throws IOException {
        if (!image.isEmpty()) {

            final String uri = "http://localhost:8080/rest/stenography/encode";

            byte[] bytes = image.getBytes();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("message", message);
            headers.add("key", number);

            HttpEntity<String> entity = new HttpEntity<String>(Base64.getEncoder().encodeToString(bytes), headers);
            RestTemplate restTemplate = new RestTemplate();
            StenographyImageResponse response = restTemplate.postForObject(uri, entity, StenographyImageResponse.class);

            byte[] result = Base64.getDecoder().decode(response.getImage());
            FileOutputStream fos = new FileOutputStream("image.bmp");
            fos.write(result);
            fos.close();
        } else {
            //return "Вам не удалось загрузить " + name + " потому что файл пустой.";
        }

        return "stenography/coder-result";
    }

    @RequestMapping("/stenography/decoder-result")
    public String providedDecoderResultPage(Model model) {
        return "stenography/decoder-result";
    }
}
