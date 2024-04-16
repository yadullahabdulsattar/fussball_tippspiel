package com.fussballtippspiel.beans;

public class Rank {
	private String userName ;
	private int points;
	public Rank() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Rank(String userName, int points) {
		super();
		this.userName = userName;
		this.points = points;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
