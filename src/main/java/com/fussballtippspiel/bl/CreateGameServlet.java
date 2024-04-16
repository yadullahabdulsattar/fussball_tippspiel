package com.fussballtippspiel.bl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
 * Servlet implementation class CreateGameServlet
 */
@WebServlet("/CreateGameServlet")
public class CreateGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("M")) {
			try {
				Connection conn = DBUtils.getConnection();
				//Populate game object with the data to be insert
				Game game = new Game();
				game.setTeam1(request.getParameter("team_1"));
				game.setTeam2(request.getParameter("team_2"));
				String kickoffTimeString = request.getParameter("kickoff_time");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
				game.setKickoffTime(LocalDateTime.parse(kickoffTimeString, formatter));
				//Calling add game method to insert game in db
				boolean rs = GameDaoImpl.addGames(conn, game);
				if(rs == true) {
					//if game is successfully created , redirect back with success
					response.sendRedirect("createGame.jsp?success=1");
				}
				else {
					//if game is not created , redirect back with error
					response.sendRedirect("createGame.jsp?error=1");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }else {
	    	response.sendRedirect("login.jsp");
	    }
	}

}
