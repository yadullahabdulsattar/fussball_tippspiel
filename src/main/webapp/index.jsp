<%
  if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
	 // Manager is logged in, routing to Manager Home Page            
     response.sendRedirect("managerHome.jsp");
  }
  else if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("U")) {
        // User is logged in, routing to Manager Home Page            
        response.sendRedirect("userHome.jsp");
  }
  else {
        // Session not exists , routing to login page
        response.sendRedirect("login.jsp");
  }
%>