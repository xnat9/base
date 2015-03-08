package org.xnat.dao.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import javax.persistence.Column;

import org.junit.Test;
import org.xnat.base.entity.Person;
import org.xnat.base.util.DbUtil;

/**
 * 自制定代码生成类
 * @author xnat
 * Oct 18, 2014 2:56:32 PM
 */
public class StrCodeGenerate {
	public static void main(String[] args) {
	}
	
	/**
	 * 为extjs 的model field 字段 
	 * 
	 * Oct 22, 2014 1:21:57 PM
	 */
	@Test
	public void generateExtJsModelFieldStr() {
		Class clazz = Person.class;
		StringBuilder sb = new StringBuilder();
		for (Field f :clazz.getDeclaredFields()) {
			Column col = f.getAnnotation(Column.class);
			if (col == null) continue;
			String type = f.getType().getSimpleName();
			switch(type) {
			case "Integer":
			case "Long":
				type = "int"; break;
			case "String":
				type = "string"; break;
			case "Double":
			case "Float":
				type = "float"; break;
			default:
				try {
					throw new Exception("添加类型"+type);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			sb.append("{ name: \'"+f.getName()+"\', type: \'"+type+"\' },\n");
		};
		System.out.println(sb.toString());
	}
	/**
	 * 为extjs grid column
	 * 
	 * Oct 23, 2014 2:06:56 PM
	 */
	@Test
	public void generateExtjsGridColumnStr() {
		Class clazz = Person.class;
		StringBuilder sb = new StringBuilder();
		for (Field f :clazz.getDeclaredFields()) {
			Column col = f.getAnnotation(Column.class);
			if (col == null) continue;
			sb.append("{ text: \'\', dataIndex: \'"+f.getName()+"\' },\n");
		};
		System.out.println(sb.toString());
	}
	
	/**
	 * 为bean 产生　Fields 类　以方便访问　类属性
	 * @return
	 * Oct 17, 2014 9:44:41 AM
	 */
	@Test
	public void generateBeanFieldsClass() {
		Class clazz = Person.class;
		String classStr = "public static final class Field {\n";
		String fieldStr = "\tpublic final static String ";
		StringBuilder sb = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		sb.append(classStr);
		for (Field f :clazz.getDeclaredFields()) {
			Column col = f.getAnnotation(Column.class);
			if (col == null) continue;
			sb.append(fieldStr);
			sb.append(f.getName()+" = "+"\""+f.getName()+"\";\n");
			fields.append(f.getName()+",");
		};
		sb.append("}");
		System.out.println(sb.toString());
		System.out.println(fields.toString());
	}
	
	/**
	 * 为实体产生tostring 方法
	 */
	@Test
	public void generateEntityTostringMethod() {
		Class clazz = Person.class;
		StringBuilder sb = new StringBuilder();
		sb.append("@Override\n");
		sb.append("public String toString() {\n");
		sb.append("\treturn \"[\"+");
		for (Field f :clazz.getDeclaredFields()) {
			Column col = f.getAnnotation(Column.class);
			if (col == null) continue;
			sb.append("\""+f.getName()+"=\"+this."+f.getName()+"+\"; \"+");
		};
		sb.delete(sb.length()-6, sb.length()).append("+\"]\";");
		sb.append("\n}");
		System.out.println(sb.toString());
	}
	
	/**
	 * 产生　entity 的 private column 字段　字符串
	 * 
	 * Oct 30, 2014 1:41:15 PM
	 */
	@Test
	public void genearateEntityFieldStr() {
		Connection con = DbUtil.getDB().getCon();
		DatabaseMetaData meta = null;
		String tableName = "system_content";
		try {
			meta = con.getMetaData();
			StringBuilder sb = new StringBuilder();
			ResultSet tableSet = meta.getTables(null, "%", "%", new String[]{"TABLE"});
			while (tableSet.next()) {
				if (!tableName.equals(tableSet.getString("TABLE_NAME"))) continue;
				String tabComment = tableSet.getString("REMARKS");
//				System.out.println("tabComment: "+tabComment);
				if (tabComment != null && !tabComment.isEmpty()) {
					sb.append(tabComment+"\n\n");
				}
				break;
			}
			ResultSet pkSet = meta.getPrimaryKeys(null, null, tableName);
			String pkCol = "";
			while(pkSet.next()) {
				pkCol += pkSet.getString("COLUMN_NAME");
			}
			ResultSet colSet = meta.getColumns(null, "%", tableName, "%");
			while(colSet.next()) {
				String comment = colSet.getString("REMARKS");
				if (comment != null && !comment.isEmpty())sb.append("// "+comment+"\n");
				String colName = colSet.getString("COLUMN_NAME");
				if (colName.equals(pkCol)) sb.append("@Id @Column\nprivate ");
				else sb.append("@Column\nprivate ");
				String type_name = colSet.getString("TYPE_NAME");
				String java_type = "";
				switch(type_name) {
				case "TINYINT":
				case "SMALLINT":
				case "MEDIUMINT":
				case "MEDIUMINT UNSIGNED":
				case "INT":
				case "INT UNSIGNED":
				case "BIT":
					java_type = "Integer"; break;
				case "BIGINT":
					java_type = "Long"; break;
				case "BOOL":
				case "BOOLEAN":
					java_type = "Boolean"; break;
				case "FLOAT":
					java_type = "Float"; break;
				case "DOUBLE":
					java_type = "Double"; break;
				case "CHAR":
				case "TEXT":
				case "VARCHAR":
					java_type = "String"; break;
				default:
					throw new Exception("有未列出的sql 类型..."+colName+" : "+type_name);
				}
				sb.append(java_type+" ");
				sb.append(colName+";\n");
			}
			System.out.println(sb.toString());
//			while (tableSet.next()) {
//				tableName = tableSet.getString("TABLE_NAME");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtil.getDB().close(con);
		}
	}
}
