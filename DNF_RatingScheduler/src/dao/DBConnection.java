package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	
	private String url;
	private String user;
	private String password;
	
	private DBConnection(){}
	
	private static class DBConnection_Singieton{
		private static final DBConnection instance = new DBConnection();
	}
	
	public static DBConnection getInstance(){
		return DBConnection_Singieton.instance;
	}

	public DBConnection initConnection() throws ClassNotFoundException, SQLException {
		if(url == null || user == null || password == null) {
			throw new SQLException("다음의 값들은 필수로 입력되어야합니다. jdbcURL="+url+", user="+user+", password="+password);
		}
		
		Class.forName("org.mariadb.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url,user,password);
		conn.setAutoCommit(false);
		
		this.connection = conn;
		
		return this;
	}

	public Connection getConnection() throws SQLException {
		return connection;
	}

	public DBConnection setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public DBConnection setUser(String user) {
		this.user = user;
		return this;
	}
	
	public DBConnection setPassword(String password) {
		this.password = password;
		return this;
	}
	
	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
}
