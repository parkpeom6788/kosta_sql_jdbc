package step3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import common.DbConfig;
/*
 * select name , address from member where id = ?; 
 */
public class TestJdbcSelectById { // 키값 검색 
	public static void main(String[] args) {
		try {
			Class.forName(DbConfig.DRIVER);
			Connection con = DriverManager.getConnection(DbConfig.URL,"scott","tiger");
			String sql = "select name , address from member where id = ?";
			
			PreparedStatement pstmt =con.prepareStatement(sql);
			pstmt.setString(1, "java"); // ?에 아이디를 할당 -> 키값이므로 if문 하나로 검색 
			
			ResultSet rs = pstmt.executeQuery(); // select 는 결과중요하므로 executeQuery() , insert , delete , update 는 executeUpdate()
				// primary key 인 id로 검색하므로 있으면 1명이 조회되므로 while 대신 if문이 적합
			
			if(rs.next()) {
				String name = rs.getString("name"); // 컬럼명을 매개변수에 적는다.
				String address = rs.getString("address");
				System.out.println(name + " " + address);
			} else {
				System.out.println("회원이 존재하지 않습니다.");
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
