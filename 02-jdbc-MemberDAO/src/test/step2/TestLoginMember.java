package test.step2;

import java.sql.SQLException;
import java.util.Scanner;

import model.MemberDAO;
import model.MemberVO;
/*
 * 			PreparedStatement 를 이용한 로그인 select 메서드 
 */
public class TestLoginMember {
	public static void main(String[] args) {
		try {
			MemberDAO dao = new MemberDAO();
			Scanner scanner = new Scanner(System.in);
			System.out.print("아이디를 입력하세요:");
			String id = scanner.nextLine();
			System.out.print("패스워드를 입력하세요:");
			String password = scanner.nextLine();
			MemberVO vo = dao.login2(id,password); // Statement 를 이용한 로그인 메서드 테스트
			if(vo==null) { // 아닐때
				System.out.println("로그인 실패..아이디 패스워드를 확인하세요");
			} else {
				System.out.println(vo.getAddress() + "에 사는 " + vo.getName() );
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}		
	}
}
