<%-- 루트(/)로 접속하면 계좌 목록 서블릿으로 보낸다 --%>
<% response.sendRedirect(request.getContextPath() + "/accounts"); %>
