<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//    HttpSession session = request.getSession(false);
    if (session != null) {
        session.invalidate();
    }
    response.sendRedirect("signin.jsp");
%>
