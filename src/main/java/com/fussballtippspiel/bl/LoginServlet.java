package com.fussballtippspiel.bl;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.beans.User;
import com.fussballtippspiel.dao.UserDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class loginServlet
 */
@SuppressWarnings("unused")
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;      
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			 Connection conn = DBUtils.getConnection();
			 String username = request.getParameter("username");
			 String pwd = request.getParameter("password");
			 User user = UserDaoImpl.fetchUsers(conn, username, pwd);
			 if(user != null) {
				 if(user.getRole().equals("M")) {
					 boolean rs = UserDaoImpl.fetchMgrSessionStatus(conn);
					 if(!rs) {
						 UserDaoImpl.updateMgrSessionStatus(conn,username);
					 }else {
						 response.sendRedirect("login.jsp?error=2");
					 }
				 }
				 HttpSession session = request.getSession();
		         session.setAttribute("id", user.getId());
		         session.setAttribute("username", user.getUsername());
		         session.setAttribute("role", user.getRole());
		         if(session.getAttribute("role").equals("M")){
		        	 response.sendRedirect("managerHome.jsp");
		         }
		         else {
		        	 response.sendRedirect("userHome.jsp");  	
		         }
			 }else {
				 response.sendRedirect("login.jsp?error=1");
			 }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
