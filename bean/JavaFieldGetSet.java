package com.shenyang.sorm.bean;
/**
 * 封装了java属性和get，set方法的源代码
 * @author Administrator
 *
 */
public class JavaFieldGetSet {
	/**
	 * 属性的源码信息
	 */
	 private String fieldInfo;
	 /**
	  * get方法的源码信息
	  */
	 private String getInfo;
	 /**
	  * set方法的源码信息
	  */
	 private String setInfo;
	 
	public JavaFieldGetSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public JavaFieldGetSet(String fieldInfo, String getInfo, String setInfo) {
		super();
		this.fieldInfo = fieldInfo;
		this.getInfo = getInfo;
		this.setInfo = setInfo;
	}
	public String getFieldInfo() {
		return fieldInfo;
	}
	public void setFieldInfo(String fieldInfo) {
		this.fieldInfo = fieldInfo;
	}
	public String getGetInfo() {
		return getInfo;
	}
	public void setGetInfo(String getInfo) {
		this.getInfo = getInfo;
	}
	public String getSetInfo() {
		return setInfo;
	}
	public void setSetInfo(String setInfo) {
		this.setInfo = setInfo;
	}
	@Override
	public String toString() {
		System.out.println(fieldInfo);
		System.out.println(getInfo);
		System.out.println(setInfo);
		return super.toString();
	}
	
}
