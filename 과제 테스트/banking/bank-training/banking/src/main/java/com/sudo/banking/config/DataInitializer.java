package com.sudo.banking.config;

import com.sudo.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {
    private final AccountService service;

    @Override
    public void run(String... args) throws Exception {
        service.createAccount("장그래", 100000);
        service.createAccount("오상식", 50000);
        System.out.println("... 샘플 계좌 생성 완료: 110-0001(장그래), 110-0002(오상식)");
    }
}
