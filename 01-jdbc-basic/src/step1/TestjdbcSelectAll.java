package step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*jdk1.8에  -> ojdbc6 드라이버 넣었음 오라클드라이버를 쓸수있다 - getConnection 연결   oracleServerXE는 항상 켜져있음
 *  Data Source Explor 이랑 자바 APP 별개의 프로세스 에서 실행한다 ( 독립적 )
 *  
 *  JDBC - 다양한 데이터베이스를 사용할수있게 표준화 함 EX) Oracle ,  MSsql ,  MariaDB 
 *  
 *  ojdbc는 오라클 전용 드라이버 를 씀 
 *   다른 종류의 데이터베이스를 쓰려면 url과 그 종류의 드라이버를 로드해서 연결객체 메서드 선언체 는 같음 
 *   
 *   하나의 데이터베이스 인터페이스로 다양한 데이터베이스를 제어할수있다 
 */
public class TestjdbcSelectAll {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver"; // 드라이버 : 개발자가 데이터베이스를 제어하기 위한 필요한 sql를 실행을 잘할수있도록 도와줌
		String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";// url : 유일한 자원 위치 , 데이터베이스 서버 위치를 정해줘야 서버에 접속 , @아이피 :포트:데이터베이스 이름 필수 
																
		try {
			Class.forName(driver); // class loading , spring - 리플렉션 API 기본 작업이 클래스 로딩 - Static , driver 가 로드 되도록 구현되어 있음 
			System.out.println("oracle jdbc driver loading");
			Connection con =  DriverManager.getConnection(dbUrl, "scott", "tiger"); // 드라이버 로딩 , 연결한걸 써먹는다 
			System.out.println(con); // oracle db connection
			System.out.println("oracle db connection");
			
			// 말을 준비
			String sql = "select id,password,name,address from member";
//			con.createStatement() createStatement 를 써도되지만 prepareStatement가 더 빠르다 
			PreparedStatement pstmt = con.prepareStatement(sql); // 미리 만들어둔 sql문  preparedStatement 가 속도가 더 빠르다
			ResultSet rs = pstmt.executeQuery(); // 실행문을 질의한다. 결과의 집합으로 반환한다.
			
			while(rs.next()) { // 커서가 next 조회한 다음 행이 존재하면 true 아니면 false 
				String id = rs.getString(1); // 조회 컬럼 순서 첫번째 id 
				String password = rs.getString(2);
				String name = rs.getString(3);
				String address = rs.getString(4);
				System.out.println(id + " " + password + " " + name + " " + address );
			}
			// 역순으로 닫아줌 
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
