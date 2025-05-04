package main.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index_login() {
        return "web_login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

