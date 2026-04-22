package earlobe.imgbase64.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/img-encode/**")
    public String home() {
        return "forward:/home";
    }

    @GetMapping("/home")
    public String index() {
        return "index.html";
    }
}
