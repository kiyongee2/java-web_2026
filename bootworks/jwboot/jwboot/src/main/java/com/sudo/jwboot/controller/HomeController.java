package com.sudo.jwboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home(){
        return "home";
    }

    @GetMapping("/time")
    public String time() {
        return "/pages/time";  // 파일: templates/pages/time.html
    }
}
