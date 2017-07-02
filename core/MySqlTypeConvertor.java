package com.shenyang.sorm.core;
/**
 * Mysql数据类型和java数据类型转换
 * @author Administrator
 *
 */
public class MySqlTypeConvertor implements TypeConvertor{

	@Override
	public String databaseType2JavaType(String columnType) {
		//varchar->String
		if(("varchar").equalsIgnoreCase(columnType)||("char").equalsIgnoreCase(columnType)){
			return "String";
		}else if(("int").equalsIgnoreCase(columnType)||
				("tinyint").equalsIgnoreCase(columnType)||
				("smallint").equalsIgnoreCase(columnType)||
				("integer").equalsIgnoreCase(columnType)){
			return "Integer";
		}else if(("bigint").equalsIgnoreCase(columnType)){
			return "long";
		}else if(("double").equalsIgnoreCase(columnType)||
				("float").equalsIgnoreCase(columnType)){
			return "Double";
		}else if(("varchar").equalsIgnoreCase(columnType)){
			return "java.sql.CLob";
		}else if(("varchar").equalsIgnoreCase(columnType)){
			return "java.sql.BLob";
		}else if("date".equalsIgnoreCase(columnType)){
			return "java.sql.Date";
		}else if(("varchar").equalsIgnoreCase(columnType)){
			return "java.sql.time";
		}else if(("varchar").equalsIgnoreCase(columnType)){
			return "java.sql.Timestamp";
		}
		return null;
	}

	@Override
	public String javaType2DatabaseType(String javaDataType) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
