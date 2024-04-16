package com.fussballtippspiel.bl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fussballtippspiel.beans.Rank;
import com.fussballtippspiel.dao.RankDaoImpl;
import com.fussballtippspiel.utils.DBUtils;

/**
 * Servlet implementation class Ranks
 */
@WebServlet("/UserRanks")
public class UserRanks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			if(session != null && session.getAttribute("role") != null) {
			Connection conn = DBUtils.getConnection();
			//fetch user ranks according to points
			List<Rank> listOfUserRanks = RankDaoImpl.fetchUserRanks(conn);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			//links to show for admin
			if(session.getAttribute("role").equals("M")) {
				out.println("<ul style=\"display:inline-block; margin: 0; padding: 0;\">\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"managerHome.jsp\">Home</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"createGame.jsp\">Create Games</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"all-games\">Games</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"logout\">Logout</a>&nbsp;&nbsp;</li>  	  \r\n"
			    		+ "	</ul>\r\n");
			}else {
				//links to show for user
				out.println("<ul style=\"display:inline-block; margin: 0; padding: 0;\">\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"userHome.jsp\">Home</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"all-games\">Game</a>&nbsp;&nbsp;</li>\r\n"
			    		+ "     <li style=\"display:inline-block;\"><a href = \"logout\">Logout</a>&nbsp;&nbsp;</li>  	  \r\n"
			    		+ "	</ul>\r\n");
			}
			//display all user ranks
		    out.println("<h2>ALL USER RANKS</h2>");
		    if(listOfUserRanks.size()>0) {
		    int rank =1;
		    out.println("<table style='border-collapse: collapse; width: 80%; text-align=center;'>");
		    out.println("<thead>");
		    out.println("<tr>");
		    out.println("<th style='border: 1px solid black; padding: 8px;'>User Name</th>");
		    out.println("<th style='border: 1px solid black; padding: 8px;'>Points</th>");
		    out.println("<th style='border: 1px solid black; padding: 8px;'>Rank</th>");
		    out.println("</tr>");
		    out.println("</thead>");
			out.println("<tbody>");
			for(Rank singleUserRank : listOfUserRanks) {
			   out.println("<tr>");
			   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleUserRank.getUserName()+"</td>");
			   out.println("<td style='border: 1px solid black; padding: 8px;'>"+singleUserRank.getPoints()+"</td>");
			   out.println("<td style='border: 1px solid black; padding: 8px;'>"+rank+++"</td>");

			   out.println("</tr>");
		    }
			out.println("</tbody></table>");
		    }else {
			   out.println("<p>No User Exists !!</p>");
		    }
			}
			else {
				response.sendRedirect("login.jsp");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
