package com.fussballtippspiel.bl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fussballtippspiel.beans.User;
import com.fussballtippspiel.dao.UserDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class signupServlet
 */
@WebServlet("/signupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
			Connection conn = DBUtils.getConnection();
			User user = new User();
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setRole(request.getParameter("role"));
			boolean rs = UserDaoImpl.addUsers(conn, user);
			if(rs == true) {
				//if successfully signup then logged in the user
				RequestDispatcher dispatcher = request.getRequestDispatcher("login");
				dispatcher.forward(request, response);

			}
			else {
				response.sendRedirect("signup.jsp?error=1");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
