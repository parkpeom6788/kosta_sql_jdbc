package test.step3;
import java.sql.SQLException;
import java.util.Scanner;
import model.MemberDAO;

public class TestInsertMember {
	// 회원 등록을 하는 메서드 테스트
	public static void main(String[] args) {
		try {
			MemberDAO dao =new MemberDAO();
			Scanner scanner = new Scanner(System.in);
			System.out.print("아이디:");
			String id = scanner.nextLine();
			// 아이디를 이미 거른다
			if(dao.findMemberById(id)!=null) {
				System.out.println(" 기존 회원 정보에 아이디가 존재합니다");
			} else {
				// id 가 중복되지 않을 때에 회원가입을 실행한다
				System.out.print("패스워드");
				String password = scanner.nextLine();
				System.out.print("이름:");
				String name = scanner.nextLine();
				System.out.print("주소");
				String address = scanner.nextLine();
				dao.registerMember(id,password,name,address); // insert 
				System.out.println(name +"님 회원가입을 축하합니다");
			}
			scanner.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
