package com.shenyang.sorm.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.shenyang.JDBCUtils.JDBCUtils;
import com.shenyang.JDBCUtils.ReflectUtils;
import com.shenyang.JDBCUtils.StringUtils;
import com.shenyang.sorm.bean.ColumnInfo;
import com.shenyang.sorm.bean.TableInfo;
/**
 * 负责针对Mysql数据库的查询 
 * @author Administrator
 *
 */
public abstract class Query implements Cloneable,Serializable{
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Object obj = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oom = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bos= new ByteArrayOutputStream();
			oom=new ObjectOutputStream(bos);
			oom.writeObject(this);
			byte [] buffer = bos.toByteArray();
			bis = new ByteArrayInputStream(buffer);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oom.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}
	public abstract Object queryPagenate(int pageNum,int size);
	public int executeDML(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Connection conn = DBManager.getConn();
		int count=0;
		java.sql.PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			count = ps.executeUpdate();
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	
	public void insert(Object obj) {
		// TODO Auto-generated method stub
		Class clazz=obj.getClass();
		int count=0;
		List<Object>params= new ArrayList<Object>();
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder sb=new StringBuilder();
		sb.append("insert into ").append(tableInfo.getTname()).append(" (");
		for(Field f : fields){
			String fieldName=f.getName();
			Object fieldValue = ReflectUtils.invokeGet(fieldName, obj);
			if(fieldValue!=null){
				count++;
				sb.append(fieldName+",");
				params.add(fieldValue);
			}
		}
		sb.setCharAt(sb.length()-1, ')');
		sb.append(" values (");
		for(int i=0;i<count;i++){
			sb.append("?,");
		}
		sb.setCharAt(sb.length()-1, ')');
		executeDML(sb.toString(), params.toArray());
	}

	
	public void delete(Class clazz, Object id) {
		// TODO Auto-generated method stub
		TableInfo tableInfo = TableContext.poClassTableMap.get(clazz);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();
		String sql="delete from "+tableInfo.getTname()+" where "+onlyPriKey.getName()+"=? ";
		executeDML(sql,new Object[]{id});
	}

	
	public void delete(Object obj) {
		// TODO Auto-generated method stub
		Class c=obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey = tableInfo.getOnlyPriKey();//获得主键
		//通过反射调用属性对应的get方法set方法
		Object priKeyValue=ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
		delete(c,priKeyValue);
	}

	
	public int update(Object obj, String[] fieldNames) {
		// TODO Auto-generated method stub
		//update tablename set columnname = 'value' where id= '';
		List<Object> values=new ArrayList<Object>();
		Class c = obj.getClass();
		TableInfo tableInfo = TableContext.poClassTableMap.get(c);
		ColumnInfo onlyPriKey=tableInfo.getOnlyPriKey();
		Object onlyPriValue= ReflectUtils.invokeGet(onlyPriKey.getName(), obj);
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(tableInfo.getTname()).append(" set ");
		for(String fieldName:fieldNames){
			sql.append(fieldName+"=?,");
			values.add(ReflectUtils.invokeGet(fieldName, obj));
		}
		sql.setCharAt(sql.length()-1,' ');
		sql.append("where ").append(onlyPriKey.getName()+"=").append(onlyPriValue);
		return executeDML(sql.toString(), values.toArray());
	}

	
	public List queryRows(String sql, Class clazz, Object[] params) {
		// TODO Auto-generated method stub
		Connection conn = DBManager.getConn();
		List<Object>list = null;
		ResultSet rs = null;
		java.sql.PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);

			rs= ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			//多行
			while(rs.next()){
				if(list==null){
					list=new ArrayList<>();
				}
				Object rowObj = clazz.newInstance();//调用javabean的无参构造器

				//多列
				for(int i=0;i<metaData.getColumnCount();i++){
					String columnName=metaData.getColumnLabel(i+1);
					Object columnValue=rs.getObject(i+1);
					ReflectUtils.invokeSet(rowObj, columnName, columnValue);
				}
				list.add(rowObj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}

	
	public Object queryUniqueRow(String sql, Class clazz, Object[] params) {
		// TODO Auto-generated method stub
		List list = queryRows(sql,clazz,params);
		return list==null?null:list.get(0);
	}

	
	public Object queryValue(String sql, Object[] params) {
		// TODO Auto-generated method stub
		Connection conn = DBManager.getConn();
		Object object = null;
		ResultSet rs = null;
		java.sql.PreparedStatement ps=null;
		try {
			ps = conn.prepareStatement(sql);
			JDBCUtils.handleParams(ps, params);
			rs = ps.executeQuery();
			//多行
			while(rs.next()){
				object=rs.getObject(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return object;

	}

	
	public Number queryNumber(String sql, Object[] params) {
		// TODO Auto-generated method stub
		return (Number)queryValue(sql, params);
	}

}
