package com.fussballtippspiel.bl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.beans.Game;
import com.fussballtippspiel.beans.UserGame;
import com.fussballtippspiel.dao.GameDaoImpl;
import com.fussballtippspiel.utils.CommonUtils;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class IndexPageServlet
 */
@WebServlet("/FetchAllGames")
public class FetchAllGames extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("role") != null) {
			try {
				Connection conn = DBUtils.getConnection();
				//If time reached then over the game
				GameDaoImpl.ifTimeReachedOverTheGame(conn);
				//update the game activity , mark game as inactive if kickoff time has reached
				GameDaoImpl.updateGameActivity(conn);
				//fetch all games
				List<Game> listOfGames = GameDaoImpl.fetchAllGames(conn);
				List<UserGame> listOfUserGame = new ArrayList();
				if(session.getAttribute("role").equals("U")) {
					listOfUserGame = GameDaoImpl.fetchUserGame(conn,(int) session.getAttribute("id"));
				}
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println("<h3 style='color:green'>Welcome "+session.getAttribute("username")+"</h3>");
				if(session.getAttribute("role").equals("M")) {
					//links list to show for admin role
					out.println("<ul style=\"display:inline-block; margin: 0; padding: 0;\">\r\n"
				    		+ "     <li style=\"display:inline-block;\"><a href = \"managerHome.jsp\">Home</a>&nbsp;&nbsp;</li>\r\n"
				    		+ "     <li style=\"display:inline-block;\"><a href = \"createGame.jsp\">Craete Games</a>&nbsp;&nbsp;</li>\r\n"
				    		+ "     <li style=\"display:inline-block;\"><a href = \"user-ranks\">Ranks</a>&nbsp;&nbsp;</li>\r\n"
				    		+ "     <li style=\"display:inline-block;\"><a href = \"logout\">Logout</a>&nbsp;&nbsp;</li>  	  \r\n"
				    		+ "	</ul>\r\n");
				}else {
					//links list to show for user role
			    out.println("<ul style=\"display:inline-block; margin: 0; padding: 0;\">\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"userHome.jsp\">Home</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"user-ranks\">Ranks</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"logout\">Logout</a>&nbsp;&nbsp;</li>  	  \r\n"
			    		+ "	</ul>\r\n");
				}
			    out.println("<h2>ALL GAMES</h2>");
			    //Display error or success message if provided in header
			    if (request.getParameter("error") != null && request.getParameter("error").equals("1")) { 
			    	out.println("<p style='color: red;'>There is some issue in updating score</p>");
			    }else if(request.getParameter("success") != null && request.getParameter("success").equals("1")){ 
			            out.println("<p style='color: green;'>Game Score is updated successfully</p>");
			    }
				
			    //if list size is greater than zero , display games in table
			    if(listOfGames.size()>0) {
			    out.println("<table style='border-collapse: collapse; width: 80%; text-align=center;'>");
			    out.println("<thead>");
			    out.println("<tr>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>ID</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Team 1</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Team 2</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Team 1 Score</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Team 2 Score</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Kick Off</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Betting Status</th>");
			    out.println("<th style='border: 1px solid black; padding: 8px;'>Action</th>");
			    if(session.getAttribute("role").equals("U")) {
			    	out.println("<th style='border: 1px solid black; padding: 8px;'>PS Team1</th>");
				    out.println("<th style='border: 1px solid black; padding: 8px;'>PS Team2</th>");
				    out.println("<th style='border: 1px solid black; padding: 8px;'>Earned Points</th>");
			    }
			    out.println("</tr>");
			    out.println("</thead>");
				out.println("<tbody>");
				//iterating each game to show in table
				for(Game singleGame : listOfGames) {
				   out.println("<tr>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getId()+"</td>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getTeam1()+"</td>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getTeam2()+"</td>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getTeam1Score()+"</td>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getTeam2Score()+"</td>");
				   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleGame.getKickoffTime()+"</td>");
				   //Display the betting status according to game activity
				   if(singleGame.isActive()==true)
					   out.println("<td style='border: 1px solid black; padding: 8px;'>Active</td>");
				   else
					   out.println("<td style='border: 1px solid black; padding: 8px;'>Non-Active</td>");
				   //if score is not updated yet by admin
				   if(!singleGame.isUpdated()) {
//					   if(session.getAttribute("role").equals("M") && CommonUtils.timeInMinutes(singleGame.getKickoffTime())>=90) {
//						   out.println("<td style='border: 1px solid black; padding: 8px;'><a href='edit-game?id="+singleGame.getId()+"'>Edit</a></td>");
//					   }else if(session.getAttribute("role").equals("M") && CommonUtils.timeInMinutes(singleGame.getKickoffTime())<90){
//						   out.println("<td style='border: 1px solid black; padding: 8px;'>Game is not completed yet</td>");
//					   }
					   //for admin role display edit button
					   if(session.getAttribute("role").equals("M") && CommonUtils.timeInMinutes(singleGame.getKickoffTime())==0) {
						   out.println("<td style='border: 1px solid black; padding: 8px;'><a href='edit-game?id="+singleGame.getId()+"'>Edit</a>&nbsp;&nbsp;<a href='game-over?id="+singleGame.getId()+"'>Game Over</a></td>");
					   }else if(session.getAttribute("role").equals("M") && CommonUtils.timeInMinutes(singleGame.getKickoffTime())<0){
						   out.println("<td style='border: 1px solid black; padding: 8px;'>Game not yet started</td>");
					   }
					   //for user role display bet button
					   else if(session.getAttribute("role").equals("U") && singleGame.isActive()) {
						   out.println("<td style='border: 1px solid black; padding: 8px;'><a href='bet-game?id="+singleGame.getId()+"'>Bet</a></td>");
					   }else {
						   //if game is not active then user can't bet anymmore
						   out.println("<td style='border: 1px solid black; padding: 8px;'>Can't bet anymore</td>");

					   }
				   }
				   //it will show if final score is updated by admin 
				   else {
					   out.println("<td style='border: 1px solid black; padding: 8px;'>Points Calculated</td>");
				   }
				   //these columns will be shown to user role
				   if(session.getAttribute("role").equals("U")) {
					   if(listOfUserGame.size()>0) {
						   boolean isFlag=false;
						   for(UserGame singleUserGame : listOfUserGame) {
							   if(singleUserGame.getGame_id()==singleGame.getId()) {
								   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleUserGame.getPsTeam1()+"</td>");
								   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleUserGame.getPsTeam2()+"</td>");
								   out.println("<td style='border: 1px solid black; padding: 8px;'>" + (singleUserGame.getEarnedPoints() == -1 ? "--" : singleUserGame.getEarnedPoints()) + "</td>");
								   isFlag=true;
							   }
						   }
						   if(!isFlag) {
							   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
							   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
							   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
						   } 
					   } else {
						   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
						   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
						   out.println("<td style='border: 1px solid black; padding: 8px;'>-</td>");
					   } 
					   

				   }
				   
				   out.println("</tr>");
			    }
				out.println("</tbody></table>");
			   }else {
				   out.println("<p>No Game Exists !!</p>");
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
