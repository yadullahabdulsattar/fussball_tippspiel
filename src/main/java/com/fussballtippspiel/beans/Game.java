package com.fussballtippspiel.beans;

import java.time.LocalDateTime;

public class Game {
	private int id;
	private String team1;
	private String team2;
	private int team1Score;
	private int team2Score;
	private LocalDateTime kickoffTime;
	boolean isActive;
	boolean isUpdated;

	
	public Game() {}
	
	public Game(int id , String team1, String team2, int team1Score, int team2Score, LocalDateTime kickoffTime, boolean isActive, boolean isUpdated) {
		super();
		this.id = id;
		this.team1 = team1;
		this.team2 = team2;
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		this.kickoffTime = kickoffTime;
		this.isActive = isActive;
		this.isUpdated = isUpdated;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
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

	public LocalDateTime getKickoffTime() {
		return kickoffTime;
	}

	public void setKickoffTime(LocalDateTime kickoffTime) {
		this.kickoffTime = kickoffTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isUpdated() {
		return isUpdated;
	}

	public void isUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	
}
