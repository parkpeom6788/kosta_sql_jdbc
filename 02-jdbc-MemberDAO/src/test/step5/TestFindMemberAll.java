package test.step5;

import java.sql.SQLException;
import java.util.ArrayList;
import model.MemberDAO;
import model.MemberVO;
public class TestFindMemberAll {
	public static void main(String[] args) {
		try {
			MemberDAO dao = new MemberDAO();
			ArrayList<MemberVO> list = dao.findMemberList();
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i).getId()+" "+list.get(i).getPassword()+" "+list.get(i).getPassword()
						+" " +list.get(i).getName()+" "+list.get(i).getAddress());
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
	}
}
