package step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
	jdk1.8에 ojdbc6 드라이버를 넣었음 오라클 드라이버를 쓸수있다
	- getConnection 연결 or oracleServerXE는 항상 켜져있음
	Data Source Explor 이랑 자바 APP  별개의 프로세스 에서 실행 ( 독립적 )
	
	JDBC - 다양한 데이터베이스를 사용 할수있다 표준화되어있다 ex) Oracle , MSsql , MariaDB
	ojdbc는 오라클 전용 드라이버 사용
	다른 종류의 데이터베이스를 사용하려면 url 과 그 종류 의 드라이버를 로드
	연결 객체 메서드 선언체는 같음
	하나의 데이터베이스 인터페이스로 다양한 데이터베이스 제어 가능
*/
public class TestjdbcSelectAll {
	public static void main(String[] args) {
		
	String driver = "oracle.jdbc.OracleDriver";
	String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	try {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(dbUrl,"scott","tiger");
		String sql ="select * from member2";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			String id = rs.getString("id");
			String password = rs.getString("password");
			String name = rs.getString("name");
			String address = rs.getString("address");
			System.out.println("id : " + id + " password : " + password + " name : " + name + " address : " + address);
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
		}
	}
}
