package com.fussballtippspiel.bl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.beans.Game;
import com.fussballtippspiel.dao.GameDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class GameUpdate
 */
@WebServlet("/EditGame")
public class EditGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//verify the required role
	    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
			String gameIdString = request.getParameter("id");
			int gameId = Integer.parseInt(gameIdString);
			try {
				Connection conn = DBUtils.getConnection();
				//fetching game of specific game id
				Game singleGameObj = GameDaoImpl.fetchGameToUpdate(conn, gameId);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				//Html form to edit the game score
			    out.println("<h2>Edit Game Score</h2>");
			    if(singleGameObj != null) {
			    	out.println("<form action='update-game' method='post'>");
			    	out.println("<input type='text' name='game_id' value='"+singleGameObj.getId()+"' hidden><br>");
			    	out.println("<label for='team_1_score'>Team 1 Score:</label>");
			    	out.println("<input type='text' name='team_1_score' value='"+singleGameObj.getTeam1Score()+"'  required><br>");
			    	out.println("<label for='team_2_score'>Team 2 Score:</label>");
			    	out.println("<input type='team_2_score' name='team_2_score' value='"+singleGameObj.getTeam2Score()+"' required><br>");
			    	out.println("<button>Update</button>");
			    	out.println("</form>");
			    }
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }else {
				response.sendRedirect("login.jsp");
	    }
		
	}

}
