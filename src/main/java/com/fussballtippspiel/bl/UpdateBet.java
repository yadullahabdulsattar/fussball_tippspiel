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

import com.fussballtippspiel.beans.GameToBet;
import com.fussballtippspiel.dao.GameDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class UpdateBet
 */
@WebServlet("/UpdateBet")
public class UpdateBet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    if (session != null && session.getAttribute("role") != null && session.getAttribute("role").equals("U")) {
			//populate singleGameToBetObj with the betting data
	    	GameToBet singleGameToBetObj = new GameToBet();
			singleGameToBetObj.setUser_id((int) session.getAttribute("id"));
			String gameIdString = request.getParameter("game_id");
			singleGameToBetObj.setGame_id(Integer.parseInt(gameIdString));
			String team1Score = request.getParameter("team_1_score");
			singleGameToBetObj.setTeam1Score(Integer.parseInt(team1Score));
			String team2Score = request.getParameter("team_2_score");
			singleGameToBetObj.setTeam2Score(Integer.parseInt(team2Score));
			try {
				Connection conn = DBUtils.getConnection();
				//insert the bet in db
				boolean rs = GameDaoImpl.addBet(conn, singleGameToBetObj);
				if(rs) {
					response.sendRedirect("all-games?success=1");
				}
				else {
					response.sendRedirect("all-games?error=1");
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
