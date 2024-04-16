package com.fussballtippspiel.beans;

public class GameScore {
//	private int user_id;
	private int team_1_score;
	private int team_2_score;
	
	public GameScore() {
		// TODO Auto-generated constructor stub
	}
	
	public GameScore(int team_1_score, int team_2_score) {
		super();
		//this.user_id = user_id;
		this.team_1_score = team_1_score;
		this.team_2_score = team_2_score;
	}
	
//	public int getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}

	public int getTeam_1_score() {
		return team_1_score;
	}

	public void setTeam_1_score(int team_1_score) {
		this.team_1_score = team_1_score;
	}

	public int getTeam_2_score() {
		return team_2_score;
	}

	public void setTeam_2_score(int team_2_score) {
		this.team_2_score = team_2_score;
	}
	
	
}
