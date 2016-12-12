package ru.nsu.fit.bro.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String providedCoderResultPage(@RequestParam("name") String name, Model model) {
        return "stenography/coder-result";
    }

//    <form name="send" enctype="multipart/form-data" action="/stenography/coder-result.html" method="post" onsubmit="return validate_form ( );">
//    <p>
//    <b>Поле сообщения:</b><br />
//    <textarea id="crypto_text" rows="2" style="width: 98%; resize: none"></textarea><br />
//    </p>
//    <p><br/>
//    <b>Случайное число:</b><br/>
//    <label for="random_number_field"></label><input id="random_number_field" type="text" maxlength="25" size="40" /><br />
//    <input type="button" onclick="random_number()" value="Сгенерировать число"/>
//    </p>
//    <center>
//    <p><br/>
//    <b>Здесь должна быть ваша картинка. В нее мы зашифруем ваше послание:</b><br/>
//    <input id="file_with_img" type="file" accept="image/*" style="color:grey;" onchange="loadImage(this)">
//    </input><br/><br/>
//    <img id="myimage"/><br/><br/>
//    </p>
//    <p>
//    <input id="send" type="submit" value="Зашифровать послание" />
//    </p>
//    </center>
//    </form>
    @RequestMapping("/stenography/decoder-result")
    public String providedDecoderResultPage(Model model) {
        return "stenography/decoder-result";
    }
}
