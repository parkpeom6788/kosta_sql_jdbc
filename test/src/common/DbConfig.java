package common;
public interface DbConfig {
	String DRIVER = "oracle.jdbc.OracleDriver"; 
	String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
//	String URL = "jdbc:oracle:thin:@192.168.0.15:1521:xe";  강사님 IP
	String User = "scott";
	String PASS = "tiger";
}