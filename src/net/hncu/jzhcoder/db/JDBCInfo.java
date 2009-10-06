package net.hncu.jzhcoder.db;

/**
 * JDBC的连接信息
 * 
 * @author vagasnail
 * 
 * 2009-10-5 上午09:59:22
 */
public class JDBCInfo {
	private String username;
	private String password;
	private String url;
	private String database;
	private String driver;
	private boolean useUnicode;
	/**
	 * 客户指定编码
	 */
	private String characterEncoding;
	private StringBuilder fullUrl;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public boolean isUseUnicode() {
		return useUnicode;
	}

	public void setUseUnicode(boolean useUnicode) {
		this.useUnicode = useUnicode;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getHostname(){
		if(this.url != null && this.url.length() > 0){
			return url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
		}
		return "";
	}

	public String getFullUrl() {
		if (fullUrl != null && fullUrl.length() > 0) {
			fullUrl = new StringBuilder();
			fullUrl.append(this.url);
			fullUrl.append(this.database);
			fullUrl.append("?useUnicode=");
			fullUrl.append(this.useUnicode);
			fullUrl.append("&characterEncoding=");
			fullUrl.append(this.characterEncoding);
		}
		return fullUrl.toString();
	}

}
