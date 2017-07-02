package com.shenyang.sorm.core;

public interface TypeConvertor {
	/**
	 * �����ݿ�����ת����java����������
	 * @param columnType
	 * @return
	 */
	public String databaseType2JavaType(String columnType);
	/**
	 * ��java��������ת�������ݿ���������
	 * @param javaDataType
	 * @return
	 */
	public String javaType2DatabaseType(String javaDataType);
}
