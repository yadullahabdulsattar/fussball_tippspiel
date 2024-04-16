package com.fussballtippspiel.beans;

public class GameToBet {
	private int id;
	private int user_id;
	private int game_id;
	private int team1Score;
	private int team2Score;
	private int points;
	
	public GameToBet() {
		super();
	}
	
	public GameToBet(int id, int user_id, int game_id, int team1Score, int team2Score, int points) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.game_id = game_id;
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		this.points = points;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getTeam1Score() {
		return team1Score;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	public int getTeam2Score() {
		return team2Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
