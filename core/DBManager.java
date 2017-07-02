package com.shenyang.sorm.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.shenyang.sorm.bean.Configuration;
import com.shenyang.sormpool.DBConnPool;

/**
 * 根据配置信息，维持连接对象的管理
 * @author Administrator
 *
 */
public class DBManager {
	private static Configuration conf;
	private static DBConnPool pool=null;
	static{//加载指定的资源文件
		Properties pros=new Properties();
		try {
			pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conf = new Configuration();
		conf.setMysqlDriver(pros.getProperty("mysqlDriver"));
		conf.setPoPackage(pros.getProperty("poPackage"));
		conf.setMysqlUser(pros.getProperty("mysqlUser"));
		conf.setMysqlPwd(pros.getProperty("mysqlPwd"));
		conf.setMysqlURL(pros.getProperty("mysqlURL"));
		conf.setSrcPath(pros.getProperty("srcPath"));
		conf.setUsingDB(pros.getProperty("usingDB"));
		conf.setQueryClass(pros.getProperty("queryClass"));
		conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
		conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
	}
	public static Configuration getConf(){
		return conf;
	}
	public static Connection getConn(){
		if(pool==null){
			pool=new DBConnPool();
		}
		return pool.getConnection();
		
	}
	public static Connection createConn(){
		try {
			Class.forName(conf.getMysqlDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return DriverManager.getConnection(conf.getMysqlURL(), conf.getMysqlUser(), conf.getMysqlPwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
}
