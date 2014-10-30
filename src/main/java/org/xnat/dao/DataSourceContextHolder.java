package org.xnat.dao;


public class DataSourceContextHolder {
	public final static String DATASOURCE1 ="d1"; //数据源1
    public final static String DATASOURCE2 ="d2"; //源2
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>(); 
	
	//如果不初始化此字段的值　外部就不能直接用
//	static {
//		DATASOURCE1 = Utils.getValueFromConfig("dataSource.id");
//		DATASOURCE2 = Utils.getValueFromConfig("dataSource2.id");
//	}
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
