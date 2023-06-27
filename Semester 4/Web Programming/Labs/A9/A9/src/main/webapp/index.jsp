<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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