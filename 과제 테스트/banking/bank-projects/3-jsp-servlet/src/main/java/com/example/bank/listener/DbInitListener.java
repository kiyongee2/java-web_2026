package com.example.bank.listener;

import com.example.bank.dao.Db;
import com.example.bank.service.BankService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 서버(웹앱)가 시작될 때 한 번 실행되는 리스너.
 * 테이블을 만들고, 계좌가 없으면 샘플 2개를 넣어 둔다.
 */
@WebListener
public class DbInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Db.init();
        BankService service = new BankService();
        if (service.getAllAccounts().isEmpty()) {
            service.createAccount("홍길동", 100000); // 110-0001
            service.createAccount("김철수", 50000);  // 110-0002
            System.out.println(">>> 샘플 계좌 생성: 110-0001(홍길동), 110-0002(김철수)");
        }
    }
}
