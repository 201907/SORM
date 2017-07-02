package com.shenyang.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
	public static Object invokeGet(String fieldName,Object obj){
		try {
			Class c = obj.getClass();
			Method m=c.getDeclaredMethod("get"+StringUtils.firsetChar2UpperCase(fieldName),null);
			Object priKeyValue = m.invoke(obj, null);
			return priKeyValue;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static void invokeSet(Object obj,String columnName,Object columnValue){
		Method m = null;
		try {
			m = obj.getClass().getDeclaredMethod("set"+StringUtils.firsetChar2UpperCase(columnName),columnValue.getClass());
			m.invoke(obj, columnValue);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
