<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Game</title>
</head>
<body>
	<%
    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
    %> 
	<ul style="display:inline-block; margin: 0; padding: 0;">
     <li style="display:inline-block;"><a href = "managerHome.jsp">Home</a>&nbsp;&nbsp;</li>
     <li style="display:inline-block;"><a href = "all-games">Games</a>&nbsp;&nbsp;</li>
     <li style="display:inline-block;"><a href = "user-ranks">Ranks</a>&nbsp;&nbsp;</li>
     <li style="display:inline-block;"><a href = "logout">Logout</a>&nbsp;&nbsp;</li>  	  
	</ul>
    </br><br>
    <%
     if (request.getParameter("error") != null && request.getParameter("error").equals("1")) { 
          %>
            <p style="color: red;">There is some problem in creating game.</p>
          <% 
     	}else if(request.getParameter("success") != null && request.getParameter("success").equals("1")){ 
     	  %>
            <p style="color: green;">Game is created Successfully.</p>
          <% 
     	}
     %>
	<form action="create" method="post">
		<input type="text" name="team_1" placeholder="Team 1 Name" required></br></br>
		<input type="text" name="team_2" placeholder="Team 2 Name" required></br></br>
		<input type="datetime-local" name="kickoff_time" placeholder="KickOff time" required></br></br>
		
		<button>Add Game</button>
	</form>
    
	
	<%
    } 
    else {
       // Session not exists , routing to login page
       response.sendRedirect("login.jsp");
   }
%>
	
</body>
</html>