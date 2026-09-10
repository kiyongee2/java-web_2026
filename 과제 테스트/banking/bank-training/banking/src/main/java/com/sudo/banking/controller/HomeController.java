package com.sudo.banking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/test")
    public String test(){
        return "<h2>테스트 페이지입니다.</h2>";
    }
}
