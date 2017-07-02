package com.shenyang.sormpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.shenyang.sorm.core.DBManager;

public class DBConnPool {
	private List<Connection> pool;
	private static final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();//最大连接数
	private static final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();//最小连接数
	public void initPool(){
		if(pool==null){
			pool = new ArrayList<Connection>();
		}
		while(pool.size()<POOL_MIN_SIZE){
			pool.add(DBManager.createConn());
		}
	}
	public synchronized Connection getConnection(){
		int last_index=pool.size()-1;
		Connection conn=pool.get(last_index);
		pool.remove(last_index);
		return conn;
	}
	/**
	 * 将连接放回池中
	 * @param conn
	 */
	public synchronized void close(Connection conn){
		if(pool.size()>=POOL_MAX_SIZE)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			pool.add(conn);
		}
	}
	public DBConnPool(){
		initPool();
	}
}
