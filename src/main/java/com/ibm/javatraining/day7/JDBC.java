package com.ibm.javatraining.day7;

import java.sql.*;

public class JDBC {

	private static String url = "jdbc:postgresql://localhost:5432/javatraining";
	private static String uname = "postgres";
	private static String password = "password";
	
//	JDBC () {
//		try {
//			Connection con = DriverManager.getConnection(url, uname, password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
	
	public static void main(String[] args) {
		try {
		    Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
		    System.err.println("PostgreSQL JDBC Driver not found! Add it to your classpath.");
		    e.printStackTrace();
		    return;
		}

		
		try {
			Connection con = DriverManager.getConnection(url, uname, password);
			String sql = "select * from student where studentid = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, 1);;
			ResultSet rs = ps.executeQuery();
			rs.next();
			String name = rs.getString("firstname");
			System.out.println("Name of student is: " + name);
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	
}
