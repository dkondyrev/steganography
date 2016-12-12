package ru.nsu.fit.bro.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/stenography/coder-result")
    public String providedCoderResultPage(Model model) {
        return "stenography/coder-result";
    }

    @RequestMapping("/stenography/decoder-result")
    public String providedDecoderResultPage(Model model) {
        return "stenography/decoder-result";
    }
}
