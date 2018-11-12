package com.revolut.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
	private final String JDBC_DRIVER = "org.h2.Driver";
	private final static String DB_URL = "jdbc:h2:~/revolut";
	private final static String USER = "sa";
	private final static String PASS = "";

	private Statement statement = null;
	private static boolean dbInitialized;
	
	private DBInitializer() {
		try {
			Class.forName(JDBC_DRIVER);

			Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement();
			
			String dropAccountTable = "DROP TABLE IF EXISTS ACCOUNT;";
			
			String dropAccountTransactionTable = "DROP TABLE IF EXISTS ACCOUNT_TRANSACTION;";
			
			String createAccountTable = "CREATE TABLE IF NOT EXISTS ACCOUNT(\n" + "ID INT PRIMARY KEY"
					+ " \n" + " ,VALUE INT  \n" + ");";
			
			String createTransactionLogTable = "CREATE TABLE IF NOT EXISTS ACCOUNT_TRANSACTION\n" + "(\n"
					+ "ID  INT PRIMARY KEY, \n" + "FROM_ACCOUNT INT,\n" + "TO_ACCOUNT INT,\n"
					+ "VALUE INT,\n" + "FOREIGN KEY (FROM_ACCOUNT) REFERENCES ACCOUNT(ID),\n"
					+ "FOREIGN KEY (TO_ACCOUNT) REFERENCES ACCOUNT(ID)\n" + ");\n" + "";
			
			String initializeFirstAccountData = "INSERT INTO ACCOUNT(ID, VALUE) VALUES(1,1000000);";
			
			String initializeSecondAccountData = "INSERT INTO ACCOUNT(ID, VALUE) VALUES(2,1000000);";
			
			statement.executeUpdate(dropAccountTransactionTable);
			statement.executeUpdate(dropAccountTable);
			statement.executeUpdate(createAccountTable);
			statement.executeUpdate(createTransactionLogTable);
			statement.executeUpdate(initializeFirstAccountData);
			statement.executeUpdate(initializeSecondAccountData);

			statement.close();
			connection.close();
			
			dbInitialized=true;
		} catch (ClassNotFoundException exception) {
			dbInitialized=false;
			System.out.println("Please add driver for H2 DB, as the application can't find it in the Classpath");
		} catch (SQLException exception) {
			dbInitialized=false;
			System.out.println("there is a problem with DB connection properties, please check it");
		}
	}
	
	public static void initializeDB() {
		if(!dbInitialized) {
			new DBInitializer();
		}
	}
	
	public static Connection openConnection() {
		initializeDB();
		try {
			return DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			System.out.println("Can not open connection");
		}
		return null;
	}

}
