package net.hncu.jzhcoder.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供对Mysql数据库，数据库字符集编码的处理，使之与客户提供的编码相匹配， 并修改其返回数据的格式为客户端指定格式。
 * 
 * @author vagasnail
 * 
 * 2009-9-29 下午03:57:17
 */
public class MysqlHandler {

	private static Log log = LogFactory.getLog(MysqlHandler.class);

	public static String CHARACTER_SET_CLIENT = "character_set_client";
	public static String CHARACTER_SET_CONNECTION = "character_set_connection";
	public static String CHARACTER_SET_DATABASE = "character_set_database";
	public static String CHARACTER_SET_RESULTS = "character_set_results";
	public static String CHARACTER_SET_SERVER = "character_set_server";
	public static String CHARACTER_SET_SYSTEM = "character_set_system";
	/**
	 * Mysql数据库的安装根目录。
	 */
	public static String BASEDIR = "basedir";

	private Map<String, String> variables = null;

	private Map<String, MysqlCharset> charsets = null;

	private Connection conn;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 修改mysql数据库的编码，以及返回数据的编码的字符集格式为客户指定编码。
	 * 
	 * @param clientCharset
	 *            客户指定编码
	 */
	public void handleDatabaseCharacter(String clientCharset) {
		boolean isEqual = jerqueCharset(clientCharset);
		if (isEqual) {
			return;
		} else {
			// 将数据库编码转换为客户端编码
			try {
				Statement stmt = conn.createStatement();
				String cmd = "alter database default character set "
						+ clientCharset
						+ " COLLATE "
						+ getMysqlCharsets().get(clientCharset).DefaultCollation;
				System.out.println(cmd);
				stmt.execute(cmd);
				log.info("Update mysql database's default character to "
						+ clientCharset + " successfull!");
				stmt.execute("set character_set_results=" + clientCharset);
				log
						.info("Update mysql database's variable character_set_results to "
								+ clientCharset + " successfull.");
				try {
				} finally {
					close(stmt);
				}
			} catch (SQLException e) {
				log.fatal(e);
				throw new RuntimeException(e);
			}
		}
	}
	/**
	 * 备份数据库，预留。
	 */
	public void databaseDump() {

	}

	/**
	 * 判定客户指定的字符集是否与数据库内部的存储的字符集匹配
	 * 
	 * @param clientCharset
	 * @return 如果匹配返回true,否则返回false。
	 */
	private boolean jerqueCharset(String clientCharset) {
		clientCharset = changeToLocal(clientCharset);
		Map<String, String> vars = getMysqlVariables();
		if (vars.get(CHARACTER_SET_DATABASE).equalsIgnoreCase(clientCharset)) {
			log
					.info("Database's default character the same as client's character.");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将客户设定的字符集转换为Mysql数据库的专有称呼
	 * 
	 * @param clientCharset
	 * @return
	 */
	private String changeToLocal(String clientCharset) {
		if (clientCharset == null) {
			return null;
		}
		if (clientCharset.equalsIgnoreCase("utf8")
				|| clientCharset.equalsIgnoreCase("utf-8")) {
			return "utf8";
		} else if (clientCharset.equalsIgnoreCase("latin1")
				|| clientCharset.equalsIgnoreCase("ISO8859-1")
				|| clientCharset.equalsIgnoreCase("ISO-8859-1")
				|| clientCharset.equalsIgnoreCase("ISO8859_1")
				|| clientCharset.equalsIgnoreCase("ISO-8859_1")) {
			throw new RuntimeException("中文不能以Latin1字符集存储，请选择中文字符集格式！");
		} else if (clientCharset.equalsIgnoreCase("gbk")
				|| clientCharset.equalsIgnoreCase("gb18030")) {
			return "gbk";
		} else if (clientCharset.equalsIgnoreCase("gb2312")) {
			return "gb2312";
		} else {
			log
					.error("This characters don't support chinese characters,so you should a select chinese characters.");
			throw new RuntimeException("该字符不支持中文字符，请选择中文字符集格式！");
		}
	}
	/**
	 * 取得必要的Mysql变量。
	 * @return
	 */
	private synchronized Map<String, String> getMysqlVariables() {
		if (variables == null) {
			try {
				Statement stmt = null;
				stmt = conn.createStatement();
				ResultSet rs1 = stmt
						.executeQuery("show variables like '%_set_%'");
				variables = new HashMap<String, String>();
				while (rs1.next()) {
					variables.put(rs1.getString(1), rs1.getString(2));
				}
				ResultSet rs2 = stmt
						.executeQuery(" show variables where variable_name='"
								+ BASEDIR + "'");
				rs2.next();
				variables.put(rs2.getString(1), rs2.getString(2));
				try {

				} finally {
					close(rs1);
					close(rs2);
					close(stmt);
				}
			} catch (SQLException e) {
				log.fatal(e);
				throw new RuntimeException(e);
			}
		}
		return variables;

	}

	/**
	 * 得到Mysql支持的中文字符的信息
	 * 
	 * @return
	 */
	private synchronized Map<String, MysqlCharset> getMysqlCharsets() {
		if (charsets == null) {
			charsets = new HashMap<String, MysqlCharset>();
			charsets.put("gbk", new MysqlCharset("gbk",
					"GBK Simplified Chinese", "gbk_chinese_ci"));
			charsets.put("gb2312", new MysqlCharset("gbk",
					"GB2312 Simplified Chinese", "gb2312_chinese_ci"));
			charsets.put("utf8", new MysqlCharset("gbk", "UTF-8 Unicode",
					"utf8_general_ci"));
		}
		return charsets;
	}

	private void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	private void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	private void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 表示Mysql的字符集
	 * @author vagasnail
	 *
	   2009-9-29   下午04:20:25
	 */
	class MysqlCharset {
		String Charset;
		String Description;
		String DefaultCollation;

		public MysqlCharset(String charset, String description,
				String defaultCollation) {
			super();
			Charset = charset;
			Description = description;
			DefaultCollation = defaultCollation;
		}
	}

	public static void main(String[] args) throws SQLException {
		MysqlHandler handler = new MysqlHandler();
		handler.setConnection(MysqlTest.conn);
		handler.handleDatabaseCharacter("gbk");
		handler.close();
		// System.out.println(handler.getMysqlVariables());
	}

}
