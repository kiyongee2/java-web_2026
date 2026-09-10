package com.sudo.banking.controller;

import com.sudo.banking.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountViewController {
    private final AccountService service;

    public AccountViewController(AccountService service){
        this.service = service;
    }

    // 첫 화면 -> 계좌 목록으로 이동
    @GetMapping("/")
    public String home(){
        return "redirect:/accounts";
    }

    // 계좌 목록 화면
    @GetMapping("/accounts")
    public String list(Model model){
        model.addAttribute("accounts", service.getAllAccounts());
        return "accounts"; //templates/accounts.html
    }

    // 계좌 개설
    @PostMapping("/accounts/create")
    public String create(@RequestParam String owner,
                         @RequestParam long initialBalance,
                         RedirectAttributes ra){
        try{
            var account = service.createAccount(owner, initialBalance);
            ra.addFlashAttribute("message",
                    account.getAccountNumber() + " 계좌가 개설되었습니다.");
        }catch (Exception e){
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }

    // 입금(계좌번호. 금액을 폼 파라미터로 받음)
    @PostMapping("/accounts/deposit")
    public String deposit(@RequestParam String no,
                         @RequestParam long amount,
                         RedirectAttributes ra){
        try{
            service.deposit(no, amount);
            ra.addFlashAttribute("message", no + " 계좌에 " + amount + "원 입금 완료.");
        }catch (Exception e){
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/accounts";
    }
}
