package com.fussballtippspiel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fussballtippspiel.beans.Rank;

public class RankDaoImpl {
	//method will be called to fetch ranks of user
	public static List<Rank> fetchUserRanks(Connection conn) throws SQLException {
		List<Rank> listOfUserRanks = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(Queries.QRY_FETCH_USERS_ACC_RANKS);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			listOfUserRanks.add(new Rank(rs.getString(1),rs.getInt(2)));
		}
		return listOfUserRanks;
		
	}
}
