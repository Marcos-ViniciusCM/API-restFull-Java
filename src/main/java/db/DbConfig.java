package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import exception.DbException;

public class DbConfig {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/marcos";
	private static String user = "root";
	private static String pass = "Marcos@123";
	
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			System.out.println("Db Connectred!");
		}catch(Exception e){
			throw new DbException(e.getMessage(),e.getCause());
		}
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
				System.out.println("Db Closed Connection");
			}catch(Exception e) {
				throw new DbException(e.getMessage(),e.getCause());
			}
		}
	}
	
	public static void closePreparedStatement(PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
				System.out.println("PreparedStatement Closed ");
			}catch(Exception e) {
				throw new DbException(e.getMessage(),e.getCause());
			}
		}
	}
	
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
				System.out.println("ResultSet Closed ");
			}catch(Exception e) {
				throw new DbException(e.getMessage(),e.getCause());
			}
		}
	}

}
