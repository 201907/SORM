package com.shenyang.JDBCUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shenyang.sorm.bean.ColumnInfo;
import com.shenyang.sorm.bean.JavaFieldGetSet;
import com.shenyang.sorm.bean.TableInfo;
import com.shenyang.sorm.core.DBManager;
import com.shenyang.sorm.core.MySqlTypeConvertor;
import com.shenyang.sorm.core.TableContext;
import com.shenyang.sorm.core.TypeConvertor;

/**
 * 生成java文件常用的操作
 * @author Administrator
 *
 */
public class JavaFileUtils {
	/**
	 * 根据字段信息生成java属性信息
	 */
	public static JavaFieldGetSet createFieldGetSetSRC(ColumnInfo column,TypeConvertor convertor){
		JavaFieldGetSet jfgs=new JavaFieldGetSet();
		String javaFieldType = convertor.databaseType2JavaType(column.getDataType());
		jfgs.setFieldInfo("\tprivate "+javaFieldType+" "+column.getName()+";\n");
		
		StringBuilder getSrc=new StringBuilder();
		getSrc.append("\tpublic "+javaFieldType+" get"+StringUtils.firsetChar2UpperCase(column.getName())+"(){\n");
		getSrc.append("\t\treturn "+column.getName()+";\n\t}");
		getSrc.append("\t\n");
		jfgs.setGetInfo(getSrc.toString());

		StringBuilder setSrc=new StringBuilder();
		setSrc.append("\tpublic void set"+StringUtils.firsetChar2UpperCase(column.getName())+"(");
		setSrc.append(javaFieldType+" "+column.getName()+"){\n");
		setSrc.append("\t\tthis."+column.getName()+"="+column.getName()+";\n");
		setSrc.append("\t}\n");
		jfgs.setSetInfo(setSrc.toString());
		return jfgs;
	}
	/**
	 * 根据表信息生成java类的源代码
	 * @param tableinfo
	 * @param convertor
	 * @return
	 */
	public static String createJavaSrc(TableInfo tableInfo,TypeConvertor convertor){
		Map<String,ColumnInfo>columns = tableInfo.getColumns();
		List<JavaFieldGetSet> javaFields = new ArrayList<JavaFieldGetSet>();
		for(ColumnInfo c:columns.values()){
			javaFields.add(createFieldGetSetSRC(c, convertor));
		}
		StringBuilder src=new StringBuilder();
		src.append("package "+DBManager.getConf().getPoPackage()+";\n\n");
		src.append("import java.sql.*;\n");
		src.append("import java.util.*;\n\n");
		src.append("public class "+StringUtils.firsetChar2UpperCase(tableInfo.getTname())+" {\n\n");
		for(JavaFieldGetSet f:javaFields){
			src.append(f.getFieldInfo());
		}
		for(JavaFieldGetSet f:javaFields){
			src.append(f.getGetInfo());
		}
		for(JavaFieldGetSet f:javaFields){
			src.append(f.getSetInfo());
		}
		src.append("}\n");
		return src.toString();
	}
	public static void createJavaPOFile(TableInfo tableInfo,TypeConvertor convertor){
		String src = createJavaSrc(tableInfo,convertor);
		String srcPath = DBManager.getConf().getSrcPath()+"\\";
		String packagePath = DBManager.getConf().getPoPackage().replaceAll("\\.", "\\\\");
		String path=srcPath+packagePath+"\\"+StringUtils.firsetChar2UpperCase(tableInfo.getTname()+".java");
		File file = new File(srcPath+packagePath);
		if(!file.exists()){
			file.mkdirs();
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(path);//路径是个难点，需要通过配置文件的信息来拼接
			bw = new BufferedWriter(fw);
			bw.write(src);
			System.out.println("建立表"+tableInfo.getTname()+"对应的java类"+StringUtils.firsetChar2UpperCase(tableInfo.getTname()+".java"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
