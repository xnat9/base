package org.xnat.dao;

import org.xnat.service.Utils_ser;


public class DataSourceContextHolder {
	public final static String DATASOURCE1; //数据源1
    public final static String DATASOURCE2; //源2
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>(); 
	
	//如果不初始化此字段的值　外部就不能直接用
	static {
		DATASOURCE1 = Utils_ser.getValueFromConfig("dataSource1.id");
		DATASOURCE2 = Utils_ser.getValueFromConfig("dataSource2.id");
	}
    public static void setDbType(String dbType) {  
        contextHolder.set(dbType);  
    }  
    public static String getDbType() {  
        return contextHolder.get();  
    }  
    public static void clearDbType() {  
        contextHolder.remove();  
    }  
}
