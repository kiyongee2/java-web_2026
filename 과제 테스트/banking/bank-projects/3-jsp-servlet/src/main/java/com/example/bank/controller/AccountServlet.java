package com.example.bank.controller;

import com.example.bank.service.BankService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 컨트롤러(Controller) — 요청을 받아 Service를 호출하고, JSP(View)로 넘긴다.
 * 이것이 Model 2 MVC 패턴이다: Servlet(제어) + JSP(화면) + DAO(데이터).
 *
 * - doGet  : 계좌 목록을 조회해 accounts.jsp 로 forward (화면 표시)
 * - doPost : 폼의 action 파라미터로 기능을 구분해 처리하고, 목록으로 redirect (PRG)
 */
@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        // 세션에 담아둔 결과/오류 메시지(flash)를 request로 옮기고 세션에서는 제거
        moveFlash(session, req, "message");
        moveFlash(session, req, "error");

        req.setAttribute("accounts", service.getAllAccounts());  // Model → View 로 전달
        req.getRequestDispatcher("/WEB-INF/views/accounts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");   // 한글 파라미터 처리
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        try {
            switch (action == null ? "" : action) {
                case "create" -> {
                    var a = service.createAccount(
                            req.getParameter("owner"),
                            parseAmount(req.getParameter("initialBalance")));
                    session.setAttribute("message", a.getAccountNumber() + " 계좌가 개설되었습니다.");
                }
                case "deposit" -> {
                    String no = req.getParameter("no");
                    service.deposit(no, parseAmount(req.getParameter("amount")));
                    session.setAttribute("message", no + " 계좌에 입금 완료.");
                }
                case "withdraw" -> {
                    String no = req.getParameter("no");
                    service.withdraw(no, parseAmount(req.getParameter("amount")));
                    session.setAttribute("message", no + " 계좌에서 출금 완료.");
                }
                case "transfer" -> {
                    service.transfer(
                            req.getParameter("from"),
                            req.getParameter("to"),
                            parseAmount(req.getParameter("amount")));
                    session.setAttribute("message", "이체 완료.");
                }
                case "delete" -> {
                    String no = req.getParameter("no");
                    service.closeAccount(no);
                    session.setAttribute("message", no + " 계좌가 해지되었습니다.");
                }
                default -> session.setAttribute("error", "알 수 없는 요청입니다.");
            }
        } catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }

        // PRG 패턴: 처리 후 목록 화면으로 redirect (새로고침 중복 제출 방지)
        resp.sendRedirect(req.getContextPath() + "/accounts");
    }

    private void moveFlash(HttpSession session, HttpServletRequest req, String key) {
        Object v = session.getAttribute(key);
        if (v != null) {
            req.setAttribute(key, v);
            session.removeAttribute(key);
        }
    }

    private long parseAmount(String s) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("금액을 숫자로 올바르게 입력하세요.");
        }
    }
}
