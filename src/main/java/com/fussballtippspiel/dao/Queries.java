package com.fussballtippspiel.dao;

public class Queries {
	public static final String QRY_FETCH_USERS = "Select * from users where username = ? and password = ?";
	public static final String QRY_EXISTS_USER = "Select * from users where username = ?";
	public static final String QRY_EXISTS_MANAGER = "Select role from users where role = 'M' ";
	public static final String QRY_INSERT_USERS = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
	public static final String QRY_INSERT_GAMES = "INSERT INTO games (team_1, team_2, kickoff_time) VALUES (?, ?, ?)";
	public static final String QRY_FETCH_ALL_GAMES = "SELECT id,team_1,team_2,team_1_score,team_2_score,kickoff_time,is_active,is_updated FROM games;";
//	public static final String QRY_FETCH_ALL_GAMES = "SELECT gm.id, gm.team_1, gm.team_2, gm.team_1_score, gm.team_2_score, gm.kickoff_time, gm.is_active, gm.is_updated, ug.team_1_score, ug.team_2_score, ug.points,ug.user_id\r\n"
//			+ "FROM games gm LEFT JOIN user_games ug ON gm.id = ug.game_id";
	public static final String QRY_FETCH_ALL_USER_GAMES = "Select user_id,game_id,team_1_score,team_2_score,points from user_games where user_id = ?;";
	public static final String QRY_TO_FETCH_SINGLE_GAME = "SELECT id,team_1,team_2,team_1_score,team_2_score,kickoff_time,is_active,is_updated FROM games where id = ?;";
	//public static final String QRY_TO_UPDATE_GAME = "Update games set team_1_score = ? ,team_2_score =?, is_updated = 1 where id =?";
	public static final String QRY_TO_UPDATE_GAME_SCORE = "Update games set team_1_score = ? ,team_2_score =? where id =?";
	public static final String QRY_TO_UPDATE_GAME_STATUS = "Update games set is_updated = 1 where id =?";
	public static final String QRY_TO_UPDATE_GAME_ACTIVITY = "Update games set is_active = 0 where id =?";
	public static final String QRY_TO_FETCH_PREDICTED_SCORES = "select user_id , team_1_score, team_2_score from user_games where game_id = ?";
	public static final String QRY_TO_FETCH_USER_POINTS = "select points from users where id = ?";
	public static final String QRY_TO_UPDATE_USER_POINTS = "update users set points = ? where id = ?";
	public static final String QRY_TO_UPDATE_USER_GAME_POINTS = "update user_games set points = ? where user_id = ? and game_id= ?";
	public static final String QRY_FETCH_USERS_ACC_RANKS = "select username , points from users where role != 'M' order by points desc;";
	public static final String QRY_TO_FETCH_SINGLE_GAME_TO_BET = "SELECT id,user_id,game_id,team_1_score,team_2_score,points FROM user_games where user_id = ? and game_id =?;";
	public static final String QRY_TO_BET_GAME= "INSERT INTO user_games(user_id, game_id, team_1_score,team_2_score) VALUES (? , ? ,?, ?)";
	public static final String QRY_TO_UPDATE_BET= "update user_games set team_1_score=? , team_2_score=? where user_id = ? and game_id =?";
	public static final String QRY_TO_FETCH_IF_USR_ALREDY_BET= "select * from user_games where user_id =? and game_id = ?";
	public static final String QRY_TO_STRT_MGR_SESSION_STS= "update users set is_active = 1 where username = ?";
	public static final String QRY_TO_DSTRY_MGR_SESSION_STS= "update users set is_active = 0 where username = ?";
	public static final String QRY_TO_FETCH_MGR_SESSION_STS= "select is_active from users where role ='M' and is_active = 1";


}
