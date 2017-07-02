package com.shenyang.sorm.core;

public interface TypeConvertor {
	/**
	 * 将数据库类型转化成java的数据类型
	 * @param columnType
	 * @return
	 */
	public String databaseType2JavaType(String columnType);
	/**
	 * 将java数据类型转化成数据库数据类型
	 * @param javaDataType
	 * @return
	 */
	public String javaType2DatabaseType(String javaDataType);
}
