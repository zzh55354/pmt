package com.pmt.mattress;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class DBAccess {
	private static String dbPath;
	private static String username;
	private static String password;
	
	DBAccess() {
		readPropertiesFile();
	}
	
	public void readPropertiesFile() {
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("/Users/zzh/eclipse-workspace/MyProject/src/com/pmt/mattress/config.properties");
			prop.load(input);
			dbPath = prop.getProperty("dbPath");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void saveMattressPrice(HashMap <MattressSize, Double> map, String ID) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs= null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("INSERT INTO MATTRESSES_PRICE(Mattress_ID,Twin, Txl, Full, Queen, King, Cking) VALUES (?,?, ?, ?, ?, ?, ?)");
			myStmt.setString(1, ID);
			myStmt.setDouble(2, map.get(MattressSize.TWIN));
			myStmt.setDouble(3, map.get(MattressSize.TWINXL));
			myStmt.setDouble(4, map.get(MattressSize.FULL));
			myStmt.setDouble(5, map.get(MattressSize.QUEEN));
			myStmt.setDouble(6, map.get(MattressSize.KING));
			myStmt.setDouble(7, map.get(MattressSize.CKING));
			myStmt.executeUpdate();
			//myRs.next();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
	}
	public double getFoamPrice(String name) {
		double returnValue = 0;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("select BF_Price from FOAMS where foam_name = ?");
			myStmt.setString(1, name);
			myRs = myStmt.executeQuery();
			myRs.next();
			returnValue = myRs.getDouble("BF_Price");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		return returnValue;
	}
	
	public HashMap<MattressSize, Double> getOutercoverPrice(String name) {
		HashMap<MattressSize, Double> map = new HashMap<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection(dbPath, username, password);
			myStmt = myConn.prepareStatement("select Twin,Txl,Full,Queen,King,Cking from OUTER_COVERS where Cover_name = ?");
			myStmt.setString(1, name);
			myRs = myStmt.executeQuery();
			myRs.next();
			map.put(MattressSize.TWIN, myRs.getDouble("Twin"));
			map.put(MattressSize.TWINXL, myRs.getDouble("Txl"));
			map.put(MattressSize.FULL, myRs.getDouble("Full"));
			map.put(MattressSize.QUEEN, myRs.getDouble("Queen"));
			map.put(MattressSize.KING, myRs.getDouble("King"));
			map.put(MattressSize.CKING, myRs.getDouble("Cking"));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			connectionClose(myConn, myStmt,myRs);
		}
		
		return map;
	}
	
	public void connectionClose(Connection conn, PreparedStatement ps, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
		if(ps != null) {
			try {
				ps.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch(SQLException e) { e.printStackTrace();}
		}
	}
}	
	

