package step2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import common.DbConfig;

public class TestJdbcInsert {
	public static void main(String[] args) {
		try {
			Class.forName(DbConfig.DRIVER);
			Connection con = DriverManager.getConnection(DbConfig.URL,"scott","tiger"); // 요청시 마다 연결 -> 매번일어남 
			System.out.println("db connection");
			// 먼저 시스템클라이언트에서 실행해보고 확인이 되면 넣는다 
			String insertSql = "insert into member(id,password,name,address) values(?,?,?,?)"; // 속도를 빨리하기 위해 물음표 사용
			PreparedStatement pstmt = con.prepareStatement(insertSql); // 준비된 말에 질의문을 넣어줌
			pstmt.setString(1,"aop"); // 첫번째 물음표에 정보 할당 
			pstmt.setString(2,"1234"); 
			pstmt.setString(3, "깍두기");
			pstmt.setString(4, "구성");

			int result = pstmt.executeUpdate(); // DML 실행  영향을 준 ROW 수  ,  또 등록시 unique constraint (SCOTT.SYS_C009959) violated 
			System.out.println("insert count : " + result);
			
			// 항상 close 역순
			pstmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
