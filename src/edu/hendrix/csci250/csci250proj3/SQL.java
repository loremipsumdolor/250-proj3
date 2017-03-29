package edu.hendrix.csci250.csci250proj3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	private Connection c;
	private Statement stmt;
	
	public SQL() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:epdb.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
	}
	
	public Course getCourse(int fastSearchCode) {
		Course courseData = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM courses WHERE fast_search IN ('" + fastSearchCode + "') LIMIT 1;" );
			while ( rs.next() ) {
				courseData = new Course(rs.getString("name"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courseData;
	}
}
