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
			Connection con = DriverManager.getConnection(DbConfig.URL,"scott","tiger");
			String sql = "insert into member2(id,password,name,address) values(?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "css");
			pstmt.setString(2, "a");
			pstmt.setString(3, "서상수");
			pstmt.setString(4, "수지");
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) 
				System.out.println("삽입 완료");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
