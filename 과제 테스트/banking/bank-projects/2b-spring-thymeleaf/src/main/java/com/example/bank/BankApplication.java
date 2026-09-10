package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 시작점. 실행 후 브라우저에서 http://localhost:8080 접속.
 */
@SpringBootApplication
public class BankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
        System.out.println("\n>>> 은행 웹앱 시작! 브라우저에서 http://localhost:8080 접속\n");
    }
}
