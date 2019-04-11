package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

public class Utils {
	private String driver;
	private String url;
	private String username;
	private String password;
	private BasicDataSource dataPool;
	
	public Utils() {
		Properties prop = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		try {
			prop.load(in);
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			dataPool = new BasicDataSource();
			dataPool.setDriverClassName(driver);
			dataPool.setUrl(url);
			dataPool.setUsername(username);
			dataPool.setPassword(password);
			dataPool.setInitialSize(3);
			dataPool.setMaxActive(10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConn() throws SQLException {
		Connection conn = dataPool.getConnection();
		return conn;
	}
	
	public void close(Connection conn,PreparedStatement state,ResultSet rs) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(state!=null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
/*	public static void main(String[] args) {
		Utils util = new Utils();
		try {
			System.out.println(util.getConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
