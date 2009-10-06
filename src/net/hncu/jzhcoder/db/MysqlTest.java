package net.hncu.jzhcoder.db;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 总结对于
 * 
 * @author vagasnail
 * 
 * 2009-9-28 下午06:06:51
 */
public class MysqlTest {
	private static Log log = LogFactory.getLog(MysqlTest.class);
	public static JDBCInfo info;

	public static Connection conn = null;
	static {
		try {
			info = new JDBCInfo();
			info.setUsername("root");
			info.setPassword("2536");
			info.setDriver("com.mysql.jdbc.Driver");
			info.setUrl("jdbc:mysql://localhost:3306/");
			info.setDatabase("my_db");
			info.setUseUnicode(true);
			info.setCharacterEncoding("GBK");
			Class.forName(info.getDriver());
			conn = DriverManager.getConnection(info.getUrl()
					+ info.getDatabase() + "?useUnicode=" + info.isUseUnicode()
					+ "&characterEncoding=" + info.getCharacterEncoding(), info
					.getUsername(), info.getPassword());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException,
			UnsupportedEncodingException {
		// getServerCharacter();
		// insert();
		// query();
//		dump();
		System.out.println(info.getHostname());
	}

	static void getServerCharacter() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt
				.executeQuery("show variables like 'character_set_server'");
		while (rs.next()) {
			System.out.println(rs.getString(2));
		}
		conn.close();
	}

	static void insert() throws SQLException {

		getStatement().executeUpdate("insert into my_tb values('哥敏哥')");
		conn.close();
	}

	static void dump() {
		MysqlHandler handler = new MysqlHandler(info);
		handler.setConnection(conn);
		final String baseDir = handler.getVariables().get(handler.getBaseDir())
				+ "bin" + File.separator;

		new Thread() {
			@Override
			public void run() {
				try {
					

					String[] cmdArray = null;
			        if (System.getProperty("os.name").matches(".*[Ww]indows.*"))   
			        {   
			        	String _baseDir = analyseBaseDir(baseDir);
						String cmd = _baseDir
						+ "mysqldump --default-character-set=gbk -hlocalhost -uroot -p2536 -B my_db"
						+ " > D:\\backup.sql";
			        	cmdArray = new String[] { "cmd", "/c", cmd };   
			        }   
			        else  
			        {   
						String cmd = baseDir
						+ "mysqldump --default-character-set=gbk -hlocalhost -uroot -p2536 -B my_db"
						+ " > backup.sql";
			        	cmdArray = new String[] { "sh", "-c", cmd };   
			        }  
			        log.info(Arrays.toString(cmdArray));
//					log.info(System.getProperty("userdir"));
					Process p = Runtime.getRuntime().exec(cmdArray);
//					 if (p.waitFor() != 0) {   
//			                if (p.exitValue() == 1)//p.exitValue()==0表示正常结束，1：非正常结束   
//			                    System.err.println("命令执行失败!");   
//			         }   
					p.waitFor();
					p.destroy();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}
/**
 * 分析系统路径，并作相应的处理。
 * @param baseDir
 * @return
 */
	private static String analyseBaseDir(String baseDir){
		String[] dirs = baseDir.split("\\\\");
//		for(int i = 0 ; i < dirs.length;i++){
//			dirs[i] = dirs[i].trim();
//		}
		StringBuilder newStr = new StringBuilder();
//		System.out.println(Arrays.toString(dirs));
		if(baseDir != null){
			for(int i = 0 ;i < dirs.length;i++ ){			
				if(dirs[i].contains(" ")){
					
					dirs[i] = "\"" + dirs[i] + "\"";
					
				}
				newStr.append(dirs[i] + File.separator);
			}
		}
//		System.out.println(newStr.toString());
		return newStr.toString();
	}
	
	static Statement getStatement() throws SQLException {
		return conn.createStatement();
	}

	static void query() throws SQLException, UnsupportedEncodingException {
		ResultSet rs = getStatement().executeQuery("select * from my_tb");
		while (rs.next()) {
			System.out.println(new String(rs.getBytes(1), "UTF-8"));
			;
		}
		conn.close();
	}
}
