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
 * �ṩ��Mysql���ݿ⣬���ݿ��ַ�������Ĵ���ʹ֮��ͻ��ṩ�ı�����ƥ�䣬 ���޸��䷵�����ݵĸ�ʽΪ�ͻ���ָ����ʽ��
 * 
 * @author vagasnail
 * 
 * 2009-9-29 ����03:57:17
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
	 * Mysql���ݿ�İ�װ��Ŀ¼��
	 */
	public static String BASEDIR = "basedir";

	private Map<String, String> variables = null;

	private Map<String, MysqlCharset> charsets = null;

	private Connection conn;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * �޸�mysql���ݿ�ı��룬�Լ��������ݵı�����ַ�����ʽΪ�ͻ�ָ�����롣
	 * 
	 * @param clientCharset
	 *            �ͻ�ָ������
	 */
	public void handleDatabaseCharacter(String clientCharset) {
		boolean isEqual = jerqueCharset(clientCharset);
		if (isEqual) {
			return;
		} else {
			// �����ݿ����ת��Ϊ�ͻ��˱���
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
	 * �������ݿ⣬Ԥ����
	 */
	public void databaseDump() {

	}

	/**
	 * �ж��ͻ�ָ�����ַ����Ƿ������ݿ��ڲ��Ĵ洢���ַ���ƥ��
	 * 
	 * @param clientCharset
	 * @return ���ƥ�䷵��true,���򷵻�false��
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
	 * ���ͻ��趨���ַ���ת��ΪMysql���ݿ��ר�гƺ�
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
			throw new RuntimeException("���Ĳ�����Latin1�ַ����洢����ѡ�������ַ�����ʽ��");
		} else if (clientCharset.equalsIgnoreCase("gbk")
				|| clientCharset.equalsIgnoreCase("gb18030")) {
			return "gbk";
		} else if (clientCharset.equalsIgnoreCase("gb2312")) {
			return "gb2312";
		} else {
			log
					.error("This characters don't support chinese characters,so you should a select chinese characters.");
			throw new RuntimeException("���ַ���֧�������ַ�����ѡ�������ַ�����ʽ��");
		}
	}
	/**
	 * ȡ�ñ�Ҫ��Mysql������
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
	 * �õ�Mysql֧�ֵ������ַ�����Ϣ
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
	 * ��ʾMysql���ַ���
	 * @author vagasnail
	 *
	   2009-9-29   ����04:20:25
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
