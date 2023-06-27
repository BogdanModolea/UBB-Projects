<%@ page import="Domain.User" %>
<%@ page import="DB.DBManager" %>
<%@ page import="Domain.Photo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vote</title>
    <style>
        #photoID {
            display: none;
        }

        #currentVotes {
            display: none;
        }

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

        .photoData {
            background-color: azure;
        }
    </style>
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
            <input type="range" min="1" max="10" value="5" id="pickVote" name="pickVote">
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
