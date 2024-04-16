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

import com.fussballtippspiel.beans.GameToBet;
import com.fussballtippspiel.dao.GameDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class BetGame
 */
@WebServlet("/BetGame")
public class BetGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		//check session for a required role
	    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("U")) {
			String gameIdString = request.getParameter("id");
			int gameId = Integer.parseInt(gameIdString);
			try {
				Connection conn = DBUtils.getConnection();
				//Fetch the game to be bet on
				GameToBet singleGameToBetObj = GameDaoImpl.fetchGameToBet(conn, (int) session.getAttribute("id"),gameId);
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				//Html form to be bet on game
			    out.println("<h2>Bet On Game</h2>");
			    out.println("<form action='update-bet' method='post'>");
			    out.println("<input type='text' name='game_id' value='"+gameId+"' hidden><br>");
				//if user has already placed bet this form will be displayed with pre-fill values
			    if(singleGameToBetObj != null) {
				    out.println("<label for='team_1_score'>Team 1 Score:</label>");
				    out.println("<input type='text' name='team_1_score' value='"+singleGameToBetObj.getTeam1Score()+"' required><br>");
				    out.println("<label for='team_2_score'>Team 1 Score:</label>");
				    out.println("<input type='team_2_score' name='team_2_score' value='"+singleGameToBetObj.getTeam2Score()+"' required><br>");
				}else {
					//else display this form
					out.println("<label for='team_1_score'>Team 1 Score:</label>");
				    out.println("<input type='text' name='team_1_score' value='0' required><br>");
					out.println("<label for='team_2_score'>Team 2 Score:</label>");
			    	out.println("<input type='team_2_score' name='team_2_score' value='0' required><br>");
				}
			    
			    out.println("<button>Bet</button>");
			    out.println("</form>");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }else {
				response.sendRedirect("login.jsp");
	    }
		
	}
}
