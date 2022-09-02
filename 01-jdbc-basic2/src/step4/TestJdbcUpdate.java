package step4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import common.DbConfig;

public class TestJdbcUpdate {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			Class.forName(DbConfig.DRIVER);
			Connection con = DriverManager.getConnection(DbConfig.URL, "scott", "tiger");
			String sql = "update member2 set address = ? where id=?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			System.out.print("아이디 입력 > ");
			String id = scanner.nextLine();
			System.out.print("설정할 주소 > ");
			String address = scanner.nextLine();
			pstmt.setString(1, address);
			pstmt.setString(2, id);
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("정상적으로 수정되었습니다.");
			}
			pstmt.close();
			con.close();
			scanner.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
