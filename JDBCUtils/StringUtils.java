package com.shenyang.JDBCUtils;
/**
 * 封装了字符串常用的操作
 * @author Administrator
 *
 */
public class StringUtils {
	/**
	 * 将首字母变为大写
	 * @param str
	 * @return
	 */
	public static String firsetChar2UpperCase(String str){
		return str.toUpperCase().substring(0,1)+str.substring(1);
	}
}
