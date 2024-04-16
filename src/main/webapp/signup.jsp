<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup Page</title>
</head>
<body>
<h2>Sign Up Form</h2>
	<%
       if (request.getParameter("error") != null && request.getParameter("error").equals("1")) { 
         %>
           <p style="color: red;">User already exists.</p>
         <% 
    	}else if(request.getParameter("success") != null && request.getParameter("success").equals("1")){ 
    	  %>
            <p style="color: green;">User is signed up. Login to proceed futher</p>
          <% 
    	}
    %>
	<form action="signup" method="post">
        <input type="text" name="username" placeholder="Username" required><br></br>
        <input type="password" name="password" placeholder ="Password" required><br></br>
        <select name="role" required>
        	<option selected disbled>Select Role</option>
            <option value="M">Manager</option>
            <option value="U">User</option>
        </select>
		</br></br>
        <input type="submit" value="Sign Up">
	</form>

</body>
</html>