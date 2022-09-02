package test.step4;

import java.sql.SQLException;
import java.util.Scanner;

import model.MemberDAO;

public class TestDeleteMember {
	public static void main(String[] args) {
		try {
			MemberDAO dao = new MemberDAO();
			Scanner scanner = new Scanner(System.in);
			System.out.println("삭제할 회원 아이디를 입력하세여:");
			// 삭제를 못하면 0 반환
			String id = scanner.nextLine();
			int result = dao.deleteMemberById(id);	
			if(result == 1) {
				System.out.println(id +"아이디에 해당하는 회원정보를 삭제했습니다.");
			} else if(result == 0) {
				System.out.println(id +"아이디에 해당하는 회원이 없습니다");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
