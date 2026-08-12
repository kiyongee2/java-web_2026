package com.sudo.jwboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExamController {

    @GetMapping("/exam01")
    public String requestMethod(Model model){
        model.addAttribute("data1", "Model 예제");
        return "pages/view01";
    }
}
