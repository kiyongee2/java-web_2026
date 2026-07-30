package users.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import users.dto.Users;

public class UserDAO {
	// MySQL에 연결
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
	
	// 회원 목록
	public List<Users> getUserList(){
		String sql = "SELECT * FROM users";
		List<Users> userList = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
				user.setUserId(rs.getString("user_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				
				userList.add(user);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	// 회원 생성
	public void addUser(Users user) {
		String sql = "insert into users(user_id, password, name)\r\n"
				+ "values (?, ?, ?)";
		
		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원 상세 조회
	public Users getUser(int id){
		String sql = "SELECT * FROM users WHERE id = ?";
		Users user = new Users();
		
		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, id);
			
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					user.setId(rs.getInt("id"));
					user.setUserId(rs.getString("user_id"));
					user.setPassword(rs.getString("password"));
					user.setName(rs.getString("name"));
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// 회원 수정
	public void updateUser(Users user) {
		String sql = "update users set user_id = ?, password = ?, "
				+ "name = ? where id = ?";
		
		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setInt(4, user.getId());
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 회원 삭제
	public void deleteUser(int id) {
		String sql = "delete from users where id = ?";
		
		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
