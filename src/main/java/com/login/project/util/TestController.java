package com.login.project.util;

import com.login.project.auth.login.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/login")
    public String login(Model model, LoginDto.LoginRequest loginRequest) {
        model.addAttribute("loginRequest", loginRequest);
        return "/view/loginForm";
    }

    @GetMapping("/view/main")
    public String main() {
        return "/index";
    }
}
