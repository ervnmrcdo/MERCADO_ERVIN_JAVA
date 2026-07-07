package com.ibm.javatraining.day7;

import java.sql.*;
import java.util.Scanner;

import org.postgresql.util.PSQLException;

public class JDBC {

	private static String url = "jdbc:postgresql://localhost:5432/javatraining";
	private static String uname = "postgres";
	private static String password = "password";
	String insertSql = "insert into student (email, firstname, lastname, password) values (?, ?, ?, ?)";
	String querySql = "select firstname, lastname, email, dateadded, dateupdated from student where (studentid = ? or email like ? or firstname like ? or lastname like ?)";
	String emailQuerySql = "select studentid, firstname, lastname, email, dateadded, dateupdated, password from student where (email = ?)";
	String updateSql = "update student set password = ?, dateupdated = current_timestamp where (studentid = ?)";	
	
	private static Connection con;

	private void connectToDatabase() {
		try {
			this.con = DriverManager.getConnection(url, uname, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void closeConnection () {
		try {
			System.out.println("\nSee you next time!");
			con.close();
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void addNewStudent () {
		String firstname, lastname, email, password;
		Scanner scanner = new Scanner(System.in); 

		System.out.print("Enter new user Email Address: ");
		email = scanner.nextLine().strip();
		
		//verify if email exists in database
		try {			
			PreparedStatement checkEmail = con.prepareStatement(emailQuerySql);
			checkEmail.setString(1, email);
			ResultSet rs = checkEmail.executeQuery();
			
				if (!rs.next()) {
					
					System.out.print("\nEnter new user First Name: ");
					firstname = scanner.nextLine().strip();
					
					System.out.print("Enter new user Last Name: ");
					lastname = scanner.nextLine().strip();

					System.out.print("Enter new user Password: ");
					password = scanner.nextLine().strip();
					
					try {			
						PreparedStatement ps = con.prepareStatement(this.insertSql);
						ps.setString(1, email);
						ps.setString(2, firstname);
						ps.setString(3, lastname);
						ps.setString(4, password);
						ps.execute();

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Email exists in database.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		

	}
	
	private void viewUser() {
		String queryString;
		Scanner scanner = new Scanner(System.in); 
		boolean emptyQuery = true;
		
		System.out.print("Enter queryString (first/lastname or email): ");
		queryString = scanner.nextLine().strip();
		
		try {			
			PreparedStatement ps = con.prepareStatement(querySql);
			try {
			    ps.setInt(1, Integer.parseInt(queryString));
			} catch (NumberFormatException e) {
			    ps.setInt(1, -1); 
			}
			ps.setString(2, "%" + queryString + "%");
			ps.setString(3, "%" + queryString + "%");
			ps.setString(4, "%" + queryString + "%");

			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
					emptyQuery = false;
					String firstname = rs.getString("firstname");
					String lastname = rs.getString("lastname");
					String email = rs.getString("email");
					String dateCreated = rs.getString("dateadded");
					String lastUpdated = rs.getString("dateupdated");

					System.out.println("\nName: " + firstname + " " +  lastname);
					System.out.println("Email: " + email);
					System.out.println("Date Created: " + dateCreated);
					System.out.println("Last Updated: " + lastUpdated) ;
			}						

			if (emptyQuery) {
				System.out.println("Query did not match anything form database");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	private void updateUser() {
		String accountEmail;
		Scanner scanner = new Scanner(System.in); 
		
		System.out.print("\nEnter email of account: ");
		accountEmail = scanner.nextLine().strip();
		
		try {			
			PreparedStatement ps = con.prepareStatement(this.emailQuerySql);
			ps.setString(1, accountEmail);
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					System.out.print("Enter password for verification: ");
					String x = scanner.nextLine();
					String realPassword = rs.getString("password");
					
					if (x.equals(realPassword)) {
						System.out.print("Enter new password: ");
						String newPassword = scanner.nextLine();
						int studentid = rs.getInt("studentid");
						PreparedStatement updatePs = con.prepareStatement(this.updateSql);
						updatePs.setString(1, newPassword);
						updatePs.setInt(2, studentid);
						updatePs.execute();		
						System.out.println("Password Updated");
						return;
					}
					System.out.println("Incorrect Password");
					
				} else {
					System.out.println("Email does not exist in database.");
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteUser() {
		String accountEmail;
		Scanner scanner = new Scanner(System.in); 
		
		System.out.print("\nEnter email of account to be deleted: ");
		accountEmail = scanner.nextLine().strip();
		
		try {			
			PreparedStatement ps = con.prepareStatement(emailQuerySql);
			ps.setString(1, accountEmail);
			ResultSet rs = ps.executeQuery();
			
			if (rs != null) {
				if (rs.next()) {
					System.out.print("Enter password for verification: ");
					String x = scanner.nextLine();
					String realPassword = rs.getString("password");
					
					if (x.equals(realPassword)) {
						System.out.print("Type \"delete\" to fully delete your account: ");
						String deleteStatement = scanner.nextLine();
						
						if(deleteStatement.equals("delete")) {
							int studentid = rs.getInt("studentid");
							String updateSql = "delete from student where (studentid = ?)";
							PreparedStatement updatePs = con.prepareStatement(updateSql);
							updatePs.setInt(1, studentid);
							updatePs.execute();		
							System.out.println("Account succesfully deleted");
							return;
						} else {
							System.out.println("Account was not deleted. \"delete\" did not match");							
							return;
						}
					}
					System.out.println("Incorrect Password");
					return;
					
				} else {
					System.out.println("Email does not exist in database.");
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void showMenu() {
		System.out.println("\n===MENU===");
		System.out.println("[A]dd");
		System.out.println("[V]iew");
		System.out.println("[U]pdate");		
		System.out.println("[D]elete");		
		System.out.println("[Q]uit");
	}
		
	public void runGUI() {
		this.connectToDatabase();
		while (con != null) {
			this.showMenu();
			
			Scanner scanner = new Scanner(System.in);
			System.out.print("Make a selection: ");
			String selection = scanner.nextLine();
			
			if (selection.strip().equalsIgnoreCase("a"))this.addNewStudent();
			if (selection.strip().equalsIgnoreCase("v"))this.viewUser();
			if (selection.strip().equalsIgnoreCase("u"))this.updateUser();
			if (selection.strip().equalsIgnoreCase("d"))this.deleteUser();
			if (selection.strip().equalsIgnoreCase("q"))this.closeConnection();
		}
	}
	
	
	public static void main(String[] args) {
		JDBC sqlConnection = new JDBC();
		sqlConnection.runGUI();
	}
	
	
	
	
}
