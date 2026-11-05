package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public static Connection getconection() throws ClassNotFoundException, SQLException
	{
		String url="jdbc:mysql://localhost:3306/bc25-2025";
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection=DriverManager.getConnection(url,"root","root");
		return connection;
		
	}
}
