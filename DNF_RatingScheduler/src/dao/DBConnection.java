package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	
	private DBConnection(){
		try {
			connection = initConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static class DBConnection_Singieton{
		private static final DBConnection instance = new DBConnection();
	}
	
	public static DBConnection getInstance(){
		return DBConnection_Singieton.instance;
	}

	private Connection initConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/DNF_RatingScheduler","root","123");
		conn.setAutoCommit(false);
		return conn;
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}
	
}
