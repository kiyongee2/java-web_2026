package users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	static String url = "jdbc:mysql://localhost:3306/javadb";
	static String username = "javauser";
	static String password = "pwjava";
	
	public static void main(String[] args) {
		
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			System.out.println(conn + " 접속 성공!!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
