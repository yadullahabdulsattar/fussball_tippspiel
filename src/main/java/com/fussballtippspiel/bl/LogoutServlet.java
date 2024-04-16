package com.fussballtippspiel.bl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.dao.UserDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class logoutServlet
 */
@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null) {
			if(session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
				try {
					Connection conn = DBUtils.getConnection();
					UserDaoImpl.destroyMgrSessionStatus(conn,(String) session.getAttribute("username"));
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			 }
			session.invalidate();
		}
		
		response.sendRedirect("login.jsp?success=1");
	}
}
