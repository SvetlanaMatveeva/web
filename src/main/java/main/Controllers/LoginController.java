package main.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/web_login")
    public String loginPage() {
        return "web_login";
    }
}
