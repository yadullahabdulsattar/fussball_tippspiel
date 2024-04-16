<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
    <h2>Login Form</h2>
    <%
       if (request.getParameter("error") != null && request.getParameter("error").equals("1")) { 
       %>
         <p style="color: red;">Invalid User Name or Password.</p>
       <% 
    }else if(request.getParameter("error") != null && request.getParameter("error").equals("2")){ 
        %>
        <p style="color: green;">Manager is already logged in.</p>
      <%
   }else if(request.getParameter("success") != null && request.getParameter("success").equals("1")){ 
        %>
         <p style="color: green;">User is logged out.</p>
       <%
    }
    %>
	<form action = "login" method="post">
		<input type ="username" name = "username" placeholder= 'Username'>
		<input type="password" name ="password" placeholder='Password'>
		<button>LOGIN</button>
	</form>
	
	<p>Not Registered ? <a href="signup.jsp">Signup</a></p>

</body>
</html>