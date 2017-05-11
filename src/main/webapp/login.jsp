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
    <title>Login</title>
</head>
<body>
<form action = "UserServlet" method="post">
    <input type="text" name="Name" id="Name">
    <input type="password" name="Password" id="Password">
    <button type="submit">Submit</button>
</form>
<p>You don't have an account yet?<br />Click <a href="./register.jsp">here</a> to register.</p>

</body>
</html>
