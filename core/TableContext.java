package com.shenyang.sorm.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import com.shenyang.JDBCUtils.JavaFileUtils;
import com.shenyang.JDBCUtils.StringUtils;
import com.shenyang.sorm.bean.ColumnInfo;
import com.shenyang.sorm.bean.TableInfo;

/**
 * 负责获取管理数据库所有表结构和类结构的关系，根据表结构生成类结构
 * @author Administrator
 *
 */
public class TableContext {
	private TableContext(){}
	/**
	 * 表名为key，表信息为value
	 */
	public static Map<String,TableInfo> tables = new HashMap<String, TableInfo>();
	/**
	 * 将po的class对象和表信息关联起来，便于重用！
	 */
	public static Map<Class, TableInfo> poClassTableMap = new HashMap<Class, TableInfo>();
	
	static{
		try{
			Connection con=DBManager.getConn();
			java.sql.DatabaseMetaData dbmd=con.getMetaData();
			ResultSet tableRet=dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
			while(tableRet.next()){
				String tableName=(String)tableRet.getObject("TABLE_NAME");
				TableInfo ti=new TableInfo(tableName,new ArrayList<ColumnInfo>(),new HashMap<String,ColumnInfo>());
				tables.put(tableName, ti);
				ResultSet set=dbmd.getColumns(null, "%", tableName, "%");
				while(set.next()){
					ColumnInfo ci=new ColumnInfo(set.getString("COLUMN_NAME"),
							set.getString("TYPE_NAME"), 0) ;
					ti.getColumns().put(set.getString("COLUMN_NAME"),ci);
				}
				ResultSet set2=dbmd.getPrimaryKeys(null, "%", tableName);
				while(set2.next()){
					ColumnInfo ci2=(ColumnInfo)ti.getColumns().get(set2.getObject("COLUMN_NAME"));
					ci2.setKeyType(1);//设置主键类型
					ti.getPriKeys().add(ci2);
				}
				if(ti.getPriKeys().size()>0){
					ti.setOnlyPriKey(ti.getPriKeys().get(0));
				}
				
			}
			updateJavaPOFile();//每次启动更新类结构
			loadPOTables();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 加载po包下的类
	 */
	public static void loadPOTables(){
		for(TableInfo tableInfo:tables.values()){
			Class c;
			try {
				c = Class.forName(DBManager.getConf().getPoPackage()+"."+StringUtils.firsetChar2UpperCase(tableInfo.getTname()));
				poClassTableMap.put(c,tableInfo);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据表结构更新配置的po包下面的java类
	 */
	public static void updateJavaPOFile(){
		Map<String, TableInfo> tables = TableContext.tables;
		for(TableInfo tableInfo:tables.values()){
			JavaFileUtils.createJavaPOFile(tableInfo,new MySqlTypeConvertor());
		}
	}
	public static void main(String[] args) {

	}
}
