package users;

import java.util.List;

import users.dao.UserDAO;
import users.dto.Users;

public class TestUsers {

	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		
		// 회원 생성
		/*Users newUser = new Users();
		newUser.setUserId("sudo");
		newUser.setPassword("su1234");
		newUser.setName("김기용");
		
		dao.addUser(newUser);*/
		
		// 회원 검색
		/*Users findUser = dao.getUser(4);
		System.out.println(findUser);*/
		
		// 회원 삭제
		//dao.deleteUser(1);
		
		// 회원 수정
		/*Users updateUser = new Users();
		updateUser.setId(5);
		updateUser.setUserId("sudo");
		updateUser.setPassword("su1234");
		updateUser.setName("이정훈");
		
		dao.updateUser(updateUser);*/
		
		//회원 목록 출력
		List<Users> userList = dao.getUserList();
		for(int i = 0; i < userList.size(); i++) {
			Users user = userList.get(i);
			System.out.println(user);
		}
	}
}

