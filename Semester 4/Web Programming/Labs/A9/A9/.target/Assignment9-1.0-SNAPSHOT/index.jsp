<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        html, body {
            height: 100%;
        }

        html {
            display: table;
            margin: auto;
        }

        body {
            display: table-cell;
            vertical-align: middle;
            background-color: lightcyan;
            font-family: "Arial", sans-serif;
        }

        .button {
            border-radius: 8px;
            background-color: lightskyblue;
        }
    </style>
</head>
<body>
<h3>Login</h3>
<%
    if(session.getAttribute("fail") != null && session.getAttribute("fail").equals(true)) {
%>
    <p>Login failed, please try again.</p>
<%
    }
%>
<form action="LoginController" method="post">
    Enter username : <input type="text" name="username"> <br>
    Enter password : <input type="password" name="password"> <br>
    <input class="button" type="submit" value="Login"/>
</form>
</body>
</html>