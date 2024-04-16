package com.fussballtippspiel.beans;

public class UserGame {
	private int user_id;
	private int game_id;
	private int psTeam1;
	private int psTeam2;
	private int earnedPoints;
	
	public UserGame(int user_id, int game_id, int psTeam1, int psTeam2, int earnedPoints) {
		super();
		this.user_id = user_id;
		this.game_id = game_id;
		this.psTeam1 = psTeam1;
		this.psTeam2 = psTeam2;
		this.earnedPoints = earnedPoints;
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
	public int getPsTeam1() {
		return psTeam1;
	}
	public void setPsTeam1(int psTeam1) {
		this.psTeam1 = psTeam1;
	}
	public int getPsTeam2() {
		return psTeam2;
	}
	public void setPsTeam2(int psTeam2) {
		this.psTeam2 = psTeam2;
	}
	public int getEarnedPoints() {
		return earnedPoints;
	}
	public void setEarnedPoints(int earnedPoints) {
		this.earnedPoints = earnedPoints;
	}

	
	
	
}
