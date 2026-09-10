package com.example.bank.controller;

import com.example.bank.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 화면(뷰) 컨트롤러.
 *
 * [REST 버전과의 차이]
 * - @RestController 는 데이터(JSON)를 반환했지만,
 *   @Controller 는 "뷰 이름(templates 폴더의 html)"을 반환한다 → 서버가 HTML을 그려서 응답.
 * - 폼 전송 후에는 redirect(:/) 로 목록 화면을 다시 불러온다(PRG 패턴: 새로고침 중복 제출 방지).
 * - 결과/오류 메시지는 RedirectAttributes 의 flash 속성으로 다음 화면에 한 번 전달한다.
 */
@Controller
public class AccountViewController {

    private final AccountService service;

    public AccountViewController(AccountService service) {
        this.service = service;
    }

    /** 첫 화면 → 계좌 목록으로 이동 */
    @GetMapping("/")
    public String home() {
        return "redirect:/accounts";
    }

    /** 계좌 목록 화면 → templates/accounts.html */
    @GetMapping("/accounts")
    public String list(Model model) {
        model.addAttribute("accounts", service.getAllAccounts());
        return "accounts"; // templates/accounts.html
    }

    /** 계좌 개설 */
    @PostMapping("/accounts/create")
    public String create(@RequestParam String owner,
                         @RequestParam long initialBalance,
                         RedirectAttributes ra) {
        try {
            var account = service.createAccount(owner, initialBalance);
            ra.addFlashAttribute("message", account.getAccountNumber() + " 계좌가 개설되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    /** 입금 (계좌번호·금액을 폼 파라미터로 받음) */
    @PostMapping("/accounts/deposit")
    public String deposit(@RequestParam String no,
                          @RequestParam long amount,
                          RedirectAttributes ra) {
        try {
            service.deposit(no, amount);
            ra.addFlashAttribute("message", no + " 계좌에 " + amount + "원 입금 완료.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    /** 출금 */
    @PostMapping("/accounts/withdraw")
    public String withdraw(@RequestParam String no,
                           @RequestParam long amount,
                           RedirectAttributes ra) {
        try {
            service.withdraw(no, amount);
            ra.addFlashAttribute("message", no + " 계좌에서 " + amount + "원 출금 완료.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    /** 이체 */
    @PostMapping("/accounts/transfer")
    public String transfer(@RequestParam String from,
                           @RequestParam String to,
                           @RequestParam long amount,
                           RedirectAttributes ra) {
        try {
            service.transfer(from, to, amount);
            ra.addFlashAttribute("message", from + " → " + to + " " + amount + "원 이체 완료.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    /** 계좌 해지 */
    @PostMapping("/accounts/{no}/delete")
    public String delete(@PathVariable String no, RedirectAttributes ra) {
        try {
            service.closeAccount(no);
            ra.addFlashAttribute("message", no + " 계좌가 해지되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }
}
