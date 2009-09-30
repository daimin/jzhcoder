package net.hncu.jzhcoder.db;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 总结对于
 * @author vagasnail
 *
   2009-9-28   下午06:06:51
 */
public class MysqlTest {

	public static Connection conn = null;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost:3306/my_db?useUnicode=true&characterEncoding=GBK",
							"root", "2536");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
//		getServerCharacter();
//		insert();
//		query();
		getAllVariables();
	}

	static void getServerCharacter() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show variables like 'character_set_server'");
		while(rs.next()){
			System.out.println(rs.getString(2));
		}
		conn.close();
	}
	
	static void insert() throws SQLException{
		
		getStatement().executeUpdate("insert into my_tb values('哥敏哥')");
		conn.close();
	}
	
	static Statement getStatement() throws SQLException{
		return conn.createStatement();
	}
	static void query() throws SQLException, UnsupportedEncodingException{
		ResultSet rs = getStatement().executeQuery("select * from my_tb");
		while(rs.next()){
			System.out.println(new String(rs.getBytes(1),"UTF-8"));;
		}
		conn.close();
	}
	static void getAllVariables() throws SQLException, UnsupportedEncodingException{
		ResultSet rs = getStatement().executeQuery("show variables");
		while(rs.next()){
			System.out.println(rs.getString(1) + " = " + rs.getString(2) );;
		}
		conn.close();
	}
}
