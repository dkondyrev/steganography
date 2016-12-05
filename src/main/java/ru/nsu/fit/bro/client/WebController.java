package ru.nsu.fit.bro.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class WebController {

    @RequestMapping("/stenography")
    public String greeting(Model model) {

        return "stenography";
    }

}
