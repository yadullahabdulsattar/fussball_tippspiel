package com.fussballtippspiel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fussballtippspiel.beans.Game;
import com.fussballtippspiel.beans.GameScore;
import com.fussballtippspiel.beans.GameToBet;
import com.fussballtippspiel.beans.UserGame;
import com.fussballtippspiel.utils.CommonUtils;

public class GameDaoImpl {
	
	//Method to insert new game in db
	public static boolean addGames(Connection conn , Game game) throws SQLException {
			PreparedStatement ps = conn.prepareStatement(Queries.QRY_INSERT_GAMES);
			ps.setString(1, game.getTeam1());
			ps.setString(2, game.getTeam2());
			ps.setObject(3, game.getKickoffTime());
			int rs = ps.executeUpdate();
			if(rs > 0) {
				return true;
			}
		return false;
	}
	
	//method to fetch all games and return list of games
	public static List<Game> fetchAllGames(Connection conn) throws SQLException {
		List<Game> listOfGames = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_ALL_GAMES);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
//			listOfGames.add(new Game(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getInt(5), (LocalDateTime) rs.getObject(6), rs.getBoolean(7), rs.getBoolean(8),rs.getInt(9),rs.getInt(10),rs.getInt(11),rs.getInt(12)));
			listOfGames.add(new Game(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getInt(5), (LocalDateTime) rs.getObject(6), rs.getBoolean(7), rs.getBoolean(8)));
		}
		return listOfGames;
		
	}
	
	//method to fetch a game edit score by admin
	public static Game fetchGameToUpdate(Connection conn, int id) throws SQLException{
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_SINGLE_GAME);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return new Game(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getInt(5), (LocalDateTime) rs.getObject(6), rs.getBoolean(7), rs.getBoolean(8));
		}
		
		return null;	
	}
	
	//method to fetch game for user to bet
	public static GameToBet fetchGameToBet(Connection conn,int user_id, int game_id) throws SQLException{
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_SINGLE_GAME_TO_BET);
		ps.setInt(1, user_id);
		ps.setInt(2, game_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			return new GameToBet(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4), rs.getInt(5), rs.getInt(6));
		}
		
		return null;	
	}
	
	//method to update game score
	public static boolean UpdateGame(Connection conn, Game singleGameObj) throws SQLException{
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_UPDATE_GAME_SCORE);
		ps.setInt(1, singleGameObj.getTeam1Score());
		ps.setInt(2, singleGameObj.getTeam2Score());
		ps.setInt(3, 1);
		ps.setInt(3, singleGameObj.getId());
//		//going to call this method to update points for user
//		fetchAllPredictedScores(conn,new GameScore(singleGameObj.getTeam1Score(),singleGameObj.getTeam2Score()),singleGameObj.getId());
//		//going to update game activity , if the final score is updated
//		updateSingleGameActivity(conn,singleGameObj.getId());
		int rs = ps.executeUpdate();
		if(rs > 0) {
			return true;
		}
	    
		return false;
	}
	
	public static void gameOver(Connection conn, int gameId) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_SINGLE_GAME);
		ps.setInt(1, gameId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			//going to update game status
			updateGameStatus(conn,gameId);
			//going to call this method to update points for user
			fetchAllPredictedScores(conn,new GameScore(rs.getInt(4),rs.getInt(5)),gameId);
			//going to update game activity , if the final score is updated
			updateSingleGameActivity(conn,gameId);
		}
	}
	
	public static void ifTimeReachedOverTheGame(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_ALL_GAMES);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			if(CommonUtils.isGameTimeOver((LocalDateTime) rs.getObject(6))>=90 && rs.getInt(8)==0 ) {
				//going to update game status
				updateGameStatus(conn,rs.getInt(1));
				//going to call this method to update points for user
				fetchAllPredictedScores(conn,new GameScore(rs.getInt(4),rs.getInt(5)),rs.getInt(1));
				//going to update game activity , if the final score is updated
				updateSingleGameActivity(conn,rs.getInt(1));
			}
		}
	}
	
	
	public static void updateGameStatus(Connection conn, int gameId) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_UPDATE_GAME_STATUS);
		ps.setInt(1, gameId);
		ps.executeUpdate();
	}
	
	//method to fetch all scores predicted by user
	private static void fetchAllPredictedScores(Connection conn ,GameScore actualGameScore, int gameId) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_PREDICTED_SCORES);
		ps.setInt(1, gameId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			GameScore predictedScore = new GameScore();
//			predictedScore.setUser_id(rs.getInt(1));
			predictedScore.setTeam_1_score(rs.getInt(2));
			predictedScore.setTeam_2_score(rs.getInt(3));
			//going to calculate points according to prediction for each user
			int gainedPoints = CommonUtils.calculatePoints(predictedScore, actualGameScore);
			//Query to fetch user total points
			PreparedStatement ps2 = conn.prepareStatement(Queries.QRY_TO_FETCH_USER_POINTS);
			//Query to update user points for that game
			PreparedStatement ps3 = conn.prepareStatement(Queries.QRY_TO_UPDATE_USER_GAME_POINTS);
			ps2.setInt(1, rs.getInt(1));
			ResultSet rs2 = ps2.executeQuery();
			ps3.setInt(1, gainedPoints);
			ps3.setInt(2,  rs.getInt(1));
			ps3.setInt(3,  gameId);
			ps3.executeUpdate();
			while(rs2.next()) {
				//Query to update user points
				PreparedStatement ps4 = conn.prepareStatement(Queries.QRY_TO_UPDATE_USER_POINTS);
				ps4.setInt(1, gainedPoints+rs2.getInt(1));
				ps4.setInt(2,rs.getInt(1));
				ps4.executeUpdate();
			}
		}
		
	}
	
	//method to add bet for game
	public static boolean addBet(Connection conn , GameToBet singGameToBet) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_IF_USR_ALREDY_BET);
		PreparedStatement ps1;
		ps.setInt(1, singGameToBet.getUser_id());
		ps.setInt(2, singGameToBet.getGame_id());
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			ps1 = conn.prepareStatement(Queries.QRY_TO_BET_GAME);
			ps1.setInt(1, singGameToBet.getUser_id());
			ps1.setInt(2, singGameToBet.getGame_id());
			ps1.setInt(3, singGameToBet.getTeam1Score());
			ps1.setInt(4, singGameToBet.getTeam2Score());
		}
		else {
			ps1 = conn.prepareStatement(Queries.QRY_TO_UPDATE_BET);
			ps1.setInt(1, singGameToBet.getTeam1Score());
			ps1.setInt(2, singGameToBet.getTeam2Score());
			ps1.setInt(3, singGameToBet.getUser_id());
			ps1.setInt(4, singGameToBet.getGame_id());
		}

		int rs1 = ps1.executeUpdate();
		if(rs1 > 0) {
			return true;
		}
		return false;
	}

	//method to update game activity
	public static void updateGameActivity(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_ALL_GAMES);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			//check if game need to be update
			if(CommonUtils.isNeedToUpdateGameAcivity((LocalDateTime) rs.getObject(6))) {
		        PreparedStatement ps1 = conn.prepareStatement(Queries.QRY_TO_UPDATE_GAME_ACTIVITY);
				ps1.setInt(1, rs.getInt(1));
				ps1.executeUpdate();
			}
		}
	}
	
	//this method will be called when uppdating the game's final score
	public static void updateSingleGameActivity(Connection conn , int gameId) throws SQLException {
		 PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_UPDATE_GAME_ACTIVITY);
		 ps.setInt(1,gameId);
		 ps.executeUpdate();
	}

	public static Boolean isUserAlreadyBet(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<UserGame> fetchUserGame(Connection conn,int user_id) throws SQLException {
		List<UserGame> listOfUserGames = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_ALL_USER_GAMES);
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
//			listOfGames.add(new Game(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4), rs.getInt(5), (LocalDateTime) rs.getObject(6), rs.getBoolean(7), rs.getBoolean(8),rs.getInt(9),rs.getInt(10),rs.getInt(11),rs.getInt(12)));
			listOfUserGames.add(new UserGame(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
		}
		return listOfUserGames;
		
	}

}
