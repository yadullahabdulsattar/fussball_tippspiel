package com.fussballtippspiel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fussballtippspiel.beans.User;

public class UserDaoImpl {
	//method to fetch users
	public static User fetchUsers(Connection conn , String username , String password) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_USERS);
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return new User(rs.getInt("id"),rs.getString("username") , rs.getString("password") , rs.getString("role"));
		}
		return null;
		
	}
	
	//method to add users
	public static boolean addUsers(Connection conn , User user) throws SQLException {
//		if(user.getRole().equals("M") && isManagerExists(conn)) {
//			return false;
//		}
		if(!isUserExists(conn , user.getUsername())) {
			PreparedStatement ps = conn.prepareStatement(Queries.QRY_INSERT_USERS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			int rs = ps.executeUpdate();
			if(rs > 0) {
				return true;
			}
		}
		return false;
	}
	
	//method to check if user already exists or not
	public static boolean isUserExists(Connection conn , String username) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_EXISTS_USER);
		ps.setString(1, username);  
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;		
	}
	
	//method to check if manager exists or not
	public static boolean isManagerExists(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_EXISTS_MANAGER);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public static boolean fetchMgrSessionStatus(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_FETCH_MGR_SESSION_STS);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
				return true;
		}
		return false;
	}
	
	public static void updateMgrSessionStatus(Connection conn , String username) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_STRT_MGR_SESSION_STS);
		ps.setString(1, username);
		ps.executeUpdate();
	}
	
	public static void destroyMgrSessionStatus(Connection conn , String username) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_TO_DSTRY_MGR_SESSION_STS);
		ps.setString(1, username);
		ps.executeUpdate();
	}
}
