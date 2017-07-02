package com.shenyang.sorm.core;

public class QueryFactory {
	private static Query prototype;

	static{
		try {
			Class c=Class.forName(DBManager.getConf().getQueryClass());
			prototype=(Query) c.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private QueryFactory(){};
	public static Query createQueryFactory(){
		try {
			return (Query) prototype.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
