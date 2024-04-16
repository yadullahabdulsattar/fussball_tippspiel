<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home Page</title>
</head>
<body>
<%
    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("U")) {
    %>   	
     	<p>Welcome User</p>
     	<ul>
     	  <li><a href = "all-games">All Game</a></li>
     	  <li><a href = "user-ranks">Ranks</a></li>
     	  <li><a href = "logout">Logout</a></li>
 
     	  
     	</ul>
    <%
    } 
    else {
       // Session not exists , routing to login page
       response.sendRedirect("login.jsp");
   }
%>
 
</body>
</html>