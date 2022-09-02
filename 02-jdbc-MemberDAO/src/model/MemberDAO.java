package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.DbConfig;

/*
 * DAO : Data Access Object 데이터베이스 연동 로직을 정의한 객체
 */
public class MemberDAO {
	MemberVO member = null;

	public MemberDAO() throws ClassNotFoundException, SQLException {
		Class.forName(DbConfig.DRIVER);
	}
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DbConfig.URL, DbConfig.User, DbConfig.PASS);
	}
	public void closeAll(PreparedStatement pstmt, Connection con) throws SQLException {
		if (pstmt != null)
			pstmt.close();
		if (con != null)
			con.close();
	}
	// 메 메서드마다 닫아주므로 미리 메서드 하나로 만들어줌
	public void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		// 역순으로 끊어준다
		if (rs != null)
			;
		closeAll(pstmt, con); // 재사용
	}

	public MemberVO findMemberById(String id) throws SQLException {
		String sql = null;

		// Connection은 메서드마다 만든다 why? 여러 사용자가 접속시 공유하면 안되므로
		Connection con = DriverManager.getConnection(DbConfig.URL, DbConfig.User, DbConfig.PASS);
		sql = "select * from member where id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			id = rs.getString(1);
			String password = rs.getString(2);
			String name = rs.getString(3);
			String address = rs.getString(4);
			member = new MemberVO(id, password, name, address);
		}
		closeAll(rs, pstmt, con);
		return member;
	}

	/*
	 * 매개변수로 전달받은 id에 해당하는 회원 정보가 oracle database 의 member2 table에 존재하지 않으면 null 을
	 * 반환 존재하면 회원정보를 MemberVO 객체로 반환
	 */
	public MemberVO findMemberById2(String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO memberVO = null;
		try {
			con = DriverManager.getConnection(DbConfig.URL, DbConfig.User, DbConfig.PASS);
			String sql = "select name,address from member where id=?";
//			String sql = "select name,address from member2 where id="+id; 로 하면 전체 sql문을 전체 구문을 해석하는 속도측면 느려짐
			pstmt = con.prepareStatement(sql);

			// PreparedStatement 는 메모리에 올려주는 ?물음표에 대해 바인딩
			pstmt.setString(1, id); // 첫번째 ? 에 대해 전달받은 id 를 할당
			rs = pstmt.executeQuery();

			// primary key로 검색했으므로 if 가 적합 ( 한명 있거나 아니면 없거나 이므로 )
			if (rs.next()) {
				String name = rs.getString(1); // 1은 select 한 컬럼 순서
				String address = rs.getString(2); // 2 는 select 한 컬럼 순서 address
				/*
				 * 컬럼명 자체로 조회할수있다. <- 이방법을 선호함 왜? 컬럼명이 많으면 순서헷갈 String name =
				 * rs.getString("name"); String address = rs.getString("address");
				 */
				memberVO = new MemberVO(id, null, name, address); // 조회만 하므로 null 넣어도 상관없다
				// 실제 db에 값을 넣을때는 값을 넣어줘야함
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return memberVO;
	}
	/*
	 * PreparedStatement 를 이용한 로그인 select 메서드
	 */
	public MemberVO login(String id, String password) throws SQLException {
		MemberVO vo = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(DbConfig.URL, DbConfig.User, DbConfig.PASS);
			String sql = "select address,name from member where id = ? and  password =?";
			// prepareStatement 속도가 더 빠르다 . 보안성도 좋다
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			if (rs.next())
				vo = new MemberVO(id, password, rs.getString("name"), rs.getString("address"));
		} finally {
			closeAll(rs, pstmt, con);
		}
		return vo;
	}
	/*
	 * Statement 방식으로 로그인 select 하는 메서드 성능과 보안성을 높이기 위해 PreparedStatement 사용을 권장한다
	 */
	public MemberVO login2(String id, String password) throws SQLException {
		MemberVO vo = null;
		ResultSet rs = null;
//		PreparedStatement pstmt = null;
		Statement stmt = null;
		Connection con = null;
		try {
//			con = DriverManager.getConnection(DbConfig.URL, DbConfig.User, DbConfig.PASS);
			con = getConnection(); // 메서드를 따로 만들어서 호출
			stmt = con.createStatement();
			String sql = "select address,name from member2 where id = '" + id + "' and password = '" + password + "'";
			rs = stmt.executeQuery(sql); // 실행할때 sql를 준다 (물음표,바인딩을 못함)
			if (rs.next())
				vo = new MemberVO(id, password, rs.getString("name"), rs.getString("address"));
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
		return vo;
	}
	// 회원가입
	public void registerMember(String id, String password, String name, String address) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			String sql = "insert into member (id,password,name,address) values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, address);
			pstmt.executeUpdate(); // insert delete update 시에는 executeUpdate()를 이용한다.
		} finally {
			closeAll(pstmt, con);
		}
	}
	public int deleteMemberById(String id) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "delete from member where id = ?";
			con = getConnection();
			pstmt =  con.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} finally {
			closeAll(pstmt, con);
		}
		return result;
	}
	
	public ArrayList<MemberVO> findMemberList() throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		try {
			String sql = "select * from member ";
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String address = rs.getString("address");
				MemberVO member = new MemberVO(id, password, name, address);
				list.add(member);
			}
		} finally {
			closeAll(rs, pstmt, con);
		}
		return list;
	}
}
