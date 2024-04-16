package com.fussballtippspiel.bl;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.dao.GameDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class GameOver
 */
@WebServlet("/GameOver")
public class GameOver extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//verify the required role
	    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
			String gameIdString = request.getParameter("id");
			int gameId = Integer.parseInt(gameIdString);
			Connection conn;
			try {
				conn = DBUtils.getConnection();
				GameDaoImpl.gameOver(conn, gameId);
				response.sendRedirect("all-games");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

	    }else {
	    	response.sendRedirect("login.jsp");
	    }
	}

//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
