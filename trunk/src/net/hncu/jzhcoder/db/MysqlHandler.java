package net.hncu.jzhcoder.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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

	public static final String CHARACTER_SET_CLIENT = "character_set_client";
	public static final String CHARACTER_SET_CONNECTION = "character_set_connection";
	public static final String CHARACTER_SET_DATABASE = "character_set_database";
	public static final String CHARACTER_SET_RESULTS = "character_set_results";
	public static final String CHARACTER_SET_SERVER = "character_set_server";
	public static final String CHARACTER_SET_SYSTEM = "character_set_system";
	/**
	 * Mysql���ݿ�İ�װ��Ŀ¼��
	 */
	private String baseDir;

	private Map<String, String> variables = null;

	private Map<String, MysqlCharset> charsets = null;

	private Connection conn;

	private JDBCInfo info;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public MysqlHandler(JDBCInfo info) {
		this.info = info;
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
	public void databaseDump(String distPath) {
		String mysqlBinPath = getBaseDir() + "bin"
				+ File.separator;
		new Thread(new DumpData(mysqlBinPath, distPath)).start();

	}

	/**
	 * ��������
	 * 
	 * @param srcFile
	 */
	public void databaseImport(String srcFile) {
		String mysqlBinPath = getBaseDir() + "bin"
				+ File.separator;
		new Thread(new ImportData(mysqlBinPath, srcFile)).start();
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
	 * ȡ�����ݿ�İ�װĿ¼
	 */
	public synchronized String getBaseDir() {
		if (baseDir == null || baseDir.length() < 0) {
			try {
				Class.forName(info.getDriver());
				Connection priConn = DriverManager.getConnection(info.getUrl()
						+ "mysql", info.getUsername(), info.getPassword());
				ResultSet rs = priConn.createStatement()
				.executeQuery("show variables where variable_name='basedir'");
				rs.next();
				baseDir = rs.getString(2);
				try{
				}finally{
					rs.close();
					priConn.close();
				}
				return baseDir;
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}


		}
		return baseDir;


	}

	/**
	 * ȡ�õ�ǰ���ݿ��Mysql������
	 * 
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
				try {

				} finally {
					close(rs1);
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
	 * 
	 * @author vagasnail
	 * 
	 * 2009-9-29 ����04:20:25
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

	class DumpData implements Runnable {
		String binPath;
		String distPath;

		DumpData(String binPath, String distPath) {
			this.binPath = binPath;
			this.distPath = distPath;
		}

		@Override
		public void run() {
			try {
				String[] cmdArray = null;
				if (System.getProperty("os.name").matches(".*[Ww]indows.*")) {
					String _baseDir = analyseBaseDir(binPath);
					String cmd = _baseDir
							+ "mysqldump --default-character-set="
							+ MysqlHandler.this.getMysqlVariables().get(
									CHARACTER_SET_DATABASE) + " -h"
							+ info.getHostname() + " -u" + info.getUsername()
							+ " -p" + info.getPassword() + " -B "
							+ info.getDatabase() + " > " + distPath
							+ info.getDatabase() + "_bak.sql";
					cmdArray = new String[] { "cmd", "/c", cmd };
				} else {
					String cmd = binPath
							+ "mysqldump --default-character-set="
							+ MysqlHandler.this.getMysqlVariables().get(
									CHARACTER_SET_DATABASE) + " -h"
							+ info.getHostname() + " -u" + info.getUsername()
							+ " -p" + info.getPassword() + " -B "
							+ info.getDatabase() + " > " + distPath
							+ info.getDatabase() + "_bak.sql";
					cmdArray = new String[] { "sh", "-c", cmd };
				}
				log.info(Arrays.toString(cmdArray));
				Process p = Runtime.getRuntime().exec(cmdArray);
				// if (p.waitFor() != 0) {
				// if (p.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
				// log.error("perform failed!");
				// }
				p.waitFor();
				p.destroy();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ����ϵͳ·����������Ӧ�Ĵ���
	 * 
	 * @param baseDir
	 * @return
	 */
	private String analyseBaseDir(String baseDir) {
		String[] dirs = baseDir.split("\\\\");
		StringBuilder newStr = new StringBuilder();
		if (baseDir != null) {
			for (int i = 0; i < dirs.length; i++) {
				if (dirs[i].contains(" ")) {

					dirs[i] = "\"" + dirs[i] + "\"";

				}
				newStr.append(dirs[i] + File.separator);
			}
		}
		return newStr.toString();
	}

	/**
	 * ʹ�ÿͻ�ָ���ı��룬�������ݵ����ݿ�
	 * 
	 * @author vagasnail
	 * 
	 * 2009-10-5 ����05:02:06
	 */
	class ImportData implements Runnable {
		String binPath;
		String srcFile;

		ImportData(String binPath, String srcFile) {
			this.binPath = binPath;
			this.srcFile = srcFile;
		}

		@Override
		public void run() {
			try {
				String[] cmdArray = null;
				if (System.getProperty("os.name").matches(".*[Ww]indows.*")) {
					String _baseDir = analyseBaseDir(binPath);
					String cmd = _baseDir + "mysql -h" + info.getHostname()
							+ " -u" + info.getUsername() + " -p"
							+ info.getPassword() + " < " + srcFile
							+ " -f --default-character-set="
							+ info.getCharacterEncoding();
					cmdArray = new String[] { "cmd", "/c", cmd };
				} else {
					String cmd = binPath + "mysql -h" + info.getHostname()
							+ " -u" + info.getUsername() + " -p"
							+ info.getPassword() + " < " + srcFile
							+ " -f --default-character-set="
							+ info.getCharacterEncoding();
					cmdArray = new String[] { "sh", "-c", cmd };
				}
				log.info(Arrays.toString(cmdArray));
				Process p = Runtime.getRuntime().exec(cmdArray);
				// if (p.waitFor() != 0) {
				// if (p.exitValue() == 1)// p.exitValue()==0��ʾ����������1������������
				// log.error("perform failed!");
				// }
				p.waitFor();
				p.destroy();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws SQLException {
		JDBCInfo info = new JDBCInfo();
		info.setUsername("root");
		info.setPassword("2536");
		info.setDriver("com.mysql.jdbc.Driver");
		info.setUrl("jdbc:mysql://localhost:3306/");
		info.setDatabase("my_db");
		info.setUseUnicode(true);
		info.setCharacterEncoding("GBK");
		MysqlHandler handler = new MysqlHandler(info);
//		handler.setConnection(MysqlTest.conn);
//		handler.setInfo(MysqlTest.info);
		// handler.handleDatabaseCharacter("gbk");
		// handler.close();
		// handler.databaseDump("D:\\");
		// System.out.println(handler.getMysqlVariables());
		handler.databaseImport("D:\\my_db_bak.sql");
	}

	public Map<String, String> getVariables() {
		return getMysqlVariables();
	}

	public void setInfo(JDBCInfo info) {
		this.info = info;
	}

}
