package com.shenyang.JDBCUtils;
/**
 * ��װ���ַ������õĲ���
 * @author Administrator
 *
 */
public class StringUtils {
	/**
	 * ������ĸ��Ϊ��д
	 * @param str
	 * @return
	 */
	public static String firsetChar2UpperCase(String str){
		return str.toUpperCase().substring(0,1)+str.substring(1);
	}
}
