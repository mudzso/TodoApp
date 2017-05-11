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
    <title>index</title>
    <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous">

    </script>
    <script src="todo.js"></script>
    <style>
        ul {
            display: inline-block;
            border: 1px solid coral;
            color: #333;
            font-family: Cantarell;
            padding: 20px;
            margin: 0px;
            margin-right: 30px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<%
    if(session.getAttribute("UserID") == null){
        response.sendRedirect("./login.jsp");
    }
%>

<ul id="backlog">

</ul>
<ul id="active">

</ul>
<ul id="done">

</ul>
<hr>
<input id="Text" type="text" />
<button id="btn">Send</button>
</body>
</html>
