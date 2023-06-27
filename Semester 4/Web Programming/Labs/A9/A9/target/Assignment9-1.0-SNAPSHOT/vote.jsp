<%@ page import="Domain.User" %>
<%@ page import="DB.DBManager" %>
<%@ page import="Domain.Photo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vote</title>
</head>
<body>
<%  User user = (User) session.getAttribute("user");
    if(user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
    <br>
    <h3>See all photos and vote</h3>
<%
    int userID = user.getId();

    DBManager dbm = new DBManager();
    for(Photo photo: dbm.getAllPhotos()){
%>
<form method="post" action="VoteController" class="photoData">
    <img width="200" height="200" src="imgs/<%=photo.getUrl()%>">
    <p>Author: <%=dbm.getUserName(photo.getuserID())%></p>
    <%
        if(photo.getuserID() != userID){
    %>
            <input type="text" id="photoID" name="photoID" value="<%=photo.getId()%>">
            <input type="text" id="currentVotes" name="currentVotes" value="<%=photo.getVotes()%>">
            <label for="pickVote">Choose your vote (between 1 and 10):</label>
            <input type="text" value="5" id="pickVote" name="pickVote">
            <input class="button" type="submit" value="Vote">



    <%
        }
        else {
    %>
            <p>You cannot vote for your own photo.</p>
    <%
        }
    %>
</form>
<br>
<%
    }
%>
<button class="button" onclick="document.location = 'home.jsp'">Go back</button>
<br><br>
</body>
</html>
