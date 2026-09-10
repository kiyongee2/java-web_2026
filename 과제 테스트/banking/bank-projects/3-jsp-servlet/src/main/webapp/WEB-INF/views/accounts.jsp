<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.bank.model.Account" %>
<%
    // 서블릿이 request에 담아 forward 한 값들을 꺼낸다
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    String message = (String) request.getAttribute("message");
    String error   = (String) request.getAttribute("error");
    String ctx     = request.getContextPath();   // 앱 기본 경로 (예: /bank)
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>은행 계좌 관리 (JSP + Servlet)</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="wrap">
    <h1>🏦 은행 계좌 관리 <small>JSP + Servlet + H2</small></h1>

    <%-- 결과/오류 메시지 (스크립트릿 + 표현식) --%>
    <% if (message != null) { %><div class="msg ok"><%= message %></div><% } %>
    <% if (error   != null) { %><div class="msg err"><%= error %></div><% } %>

    <%-- 계좌 목록 : 스크립트릿 for 문으로 반복 --%>
    <h2>계좌 목록</h2>
    <table>
        <thead>
        <tr><th>계좌번호</th><th>예금주</th><th class="r">잔액</th><th class="c">관리</th></tr>
        </thead>
        <tbody>
        <% for (Account a : accounts) { %>
            <tr>
                <td><%= a.getAccountNumber() %></td>
                <td><%= a.getOwner() %></td>
                <td class="r"><%= String.format("%,d", a.getBalance()) %>원</td>
                <td class="c">
                    <form action="<%= ctx %>/accounts" method="post"
                          onsubmit="return confirm('정말 해지하시겠습니까?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="no" value="<%= a.getAccountNumber() %>">
                        <button class="danger">해지</button>
                    </form>
                </td>
            </tr>
        <% } %>
        <% if (accounts.isEmpty()) { %>
            <tr><td colspan="4" class="empty">등록된 계좌가 없습니다.</td></tr>
        <% } %>
        </tbody>
    </table>

    <%-- 기능 폼들 : 숨은 action 값으로 서블릿에서 기능 구분 --%>
    <div class="cards">
        <div class="card">
            <h3>➕ 계좌 개설</h3>
            <form action="<%= ctx %>/accounts" method="post">
                <input type="hidden" name="action" value="create">
                <label>예금주 <input name="owner" required></label>
                <label>초기 입금액 <input name="initialBalance" type="number" value="0" min="0"></label>
                <button>개설</button>
            </form>
        </div>

        <div class="card">
            <h3>💰 입금</h3>
            <form action="<%= ctx %>/accounts" method="post">
                <input type="hidden" name="action" value="deposit">
                <label>계좌번호 <input name="no" placeholder="110-0001" required></label>
                <label>입금액 <input name="amount" type="number" value="10000" min="1" required></label>
                <button>입금</button>
            </form>
        </div>

        <div class="card">
            <h3>💸 출금</h3>
            <form action="<%= ctx %>/accounts" method="post">
                <input type="hidden" name="action" value="withdraw">
                <label>계좌번호 <input name="no" placeholder="110-0001" required></label>
                <label>출금액 <input name="amount" type="number" value="10000" min="1" required></label>
                <button>출금</button>
            </form>
        </div>

        <div class="card">
            <h3>🔄 이체</h3>
            <form action="<%= ctx %>/accounts" method="post">
                <input type="hidden" name="action" value="transfer">
                <label>보내는 계좌 <input name="from" placeholder="110-0001" required></label>
                <label>받는 계좌 <input name="to" placeholder="110-0002" required></label>
                <label>이체 금액 <input name="amount" type="number" value="10000" min="1" required></label>
                <button>이체</button>
            </form>
        </div>
    </div>

    <p class="foot">Model 2 MVC — Servlet(Controller) + JSP(View) + DAO(Model) · DB: H2</p>
</div>
</body>
</html>
