<%--
  Created by IntelliJ IDEA.
  User: mudzso
  Date: 2017.05.11.
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action = "UserServlet" method="get">
    <input type="text" name="Name" id="Name">
    <input type="password" name="Password" id="Password">
    <button type="submit">Regist</button>
</form>
<p>Already have an account? click <a href="./login.jsp">here</a></p>
</body>
</html>
