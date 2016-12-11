package ru.nsu.fit.bro.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/stenography")
    public String greeting(Model model) {

        return "stenography";
    }
}
