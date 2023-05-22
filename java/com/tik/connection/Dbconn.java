package com.tik.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconn {
	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce" , "root" ,"" );
			System.out.println("Database connected succussfully");
		}
		return connection;
	}
	
}
