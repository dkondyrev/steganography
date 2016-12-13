package ru.nsu.fit.bro.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.nsu.fit.bro.rest.model.StenographyImageResponse;
import ru.nsu.fit.bro.rest.model.StenographyMessageResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.UUID;

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
            StenographyImageResponse re = restTemplate.postForObject(uri, entity, StenographyImageResponse.class);


            byte[] result = Base64.getDecoder().decode(re.getImage());

            File file = new File("images/" + UUID.randomUUID().toString() + ".bmp");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(result);
            fos.close();

            model.addAttribute("String", file.getAbsolutePath());
        } else {
            //тут похорошему нужно сообщать о ошибке
            //return "stenography/error_page";
        }

        return "stenography/coder-result";
    }

    @RequestMapping(value="stenography/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @RequestParam("path") String path) throws IOException {

        File file = new File(path);
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));

        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping(value = "/stenography/decoder-result", method = RequestMethod.POST)
    public String providedDecoderResultPage(@RequestParam("key") String key,
                                            @RequestParam("image") MultipartFile image,
                                            Model model) throws IOException {

        if (!image.isEmpty()) {

            final String uri = "http://localhost:8080/rest/stenography/decode";

            byte[] bytes = image.getBytes();

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("key", key);

            HttpEntity<String> entity = new HttpEntity<String>(Base64.getEncoder().encodeToString(bytes), headers);
            RestTemplate restTemplate = new RestTemplate();
            StenographyMessageResponse re = restTemplate.postForObject(uri, entity, StenographyMessageResponse .class);

            String decodedMessage = re.getMessage();

            model.addAttribute("String", decodedMessage);
        } else {
            //тут похорошему нужно сообщать о ошибке
            //return "stenography/error_page";
        }

        return "stenography/decoder-result";
    }
}
