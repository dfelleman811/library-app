package com.revature.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// This class will define the methods needed to create a connection to our DB
// we are going to make ConnectionUtil using the Singleton Design Pattern
// to ensure that only one instance of the class exists throughout the program
public class ConnectionUtil {

	private static ConnectionUtil cu;
	private static Properties dbProps;
	
	// private constructor
	private ConnectionUtil() {
		// initialize the Properties object to hold our db credentials
		dbProps = new Properties();
		
		// Stream the credentials from our connection.properties file to this Object
		InputStream props = ConnectionUtil.class.getClassLoader().getResourceAsStream("connection.properties");
		
		try {
			dbProps.load(props);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// public getter to return us an instance of this class -> a ConnectionUtil
	public static synchronized ConnectionUtil getConnectionUtil() {
		// first check if an instance does not already exists
		if (cu == null) {
			cu = new ConnectionUtil();
		}
		// otherwise return the existing instance
		return cu;
	}
	
	// Method to actually establish a connection to the db
	public Connection getConnection() {
		
		Connection conn = null;
		
		// if you're getting Driver not found or something similar - here's a hot fix
		// this is forcing our PostgreeSQL Driver to load
		try {
			Class.forName(dbProps.getProperty("driver"));
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		// get our credentials from the ConnectionUtil's properties object that we created in the constructor
		String url = dbProps.getProperty("url");
		String username = dbProps.getProperty("username");
		String password = dbProps.getProperty("password");
		
		// use those credentials and the DriverManager to connect to our db instance
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	// the below code is just to manually test that we can make a connection
	// don't put this in your project or leave it there
	public static void main(String[] args) {
		
		Connection connection = getConnectionUtil().getConnection();
		
		if (connection != null) {
			System.out.println("Connection Successful");
		} else {
			System.out.println("Something went wrong");
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
}
