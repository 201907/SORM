package com.shenyang.sorm.bean;
/**
 * 管理配置信息
 * @author Administrator
 *
 */
public class Configuration {
	private String queryClass;
	public String getQueryClass() {
		return queryClass;
	}
	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}
	private int poolMinSize;
	private int poolMaxSize;
	
	public int getPoolMinSize() {
		return poolMinSize;
	}
	public void setPoolMinSize(int poolMinSize) {
		this.poolMinSize = poolMinSize;
	}
	public int getPoolMaxSize() {
		return poolMaxSize;
	}
	public void setPoolMaxSize(int poolMaxSize) {
		this.poolMaxSize = poolMaxSize;
	}
	/**
	 * 驱动类
	 */
	private String mysqlDriver;
	/**
	 * JDBC的URL
	 */
	private String mysqlURL;
	/**
	 * 数据库用户名
	 */
	private String mysqlUser;
	/**
	 * 数据库的密码
	 */
	private String mysqlPwd;
	/**
	 * 正在使用的数据库
	 */
	private String usingDB;
	/**
	 * 扫描生成JAVA类的包
	 */
	private String poPackage;
	/**
	 * 源码路径
	 */
	private String srcPath;
	public String getMysqlDriver() {
		return mysqlDriver;
	}
	public void setMysqlDriver(String mysqlDriver) {
		this.mysqlDriver = mysqlDriver;
	}
	public String getMysqlURL() {
		return mysqlURL;
	}
	public void setMysqlURL(String mysqlURL) {
		this.mysqlURL = mysqlURL;
	}
	public String getMysqlUser() {
		return mysqlUser;
	}
	public void setMysqlUser(String mysqlUser) {
		this.mysqlUser = mysqlUser;
	}
	public String getMysqlPwd() {
		return mysqlPwd;
	}
	public void setMysqlPwd(String mysqlPwd) {
		this.mysqlPwd = mysqlPwd;
	}
	public String getUsingDB() {
		return usingDB;
	}
	public void setUsingDB(String usingDB) {
		this.usingDB = usingDB;
	}
	public String getPoPackage() {
		return poPackage;
	}
	public void setPoPackage(String poPackage) {
		this.poPackage = poPackage;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public Configuration(String mysqlDriver, String mysqlURL, String mysqlUser,
			String mysqlPwd, String usingDB, String poPackage, String srcPath) {
		super();
		this.mysqlDriver = mysqlDriver;
		this.mysqlURL = mysqlURL;
		this.mysqlUser = mysqlUser;
		this.mysqlPwd = mysqlPwd;
		this.usingDB = usingDB;
		this.poPackage = poPackage;
		this.srcPath = srcPath;
	}
	public Configuration() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
