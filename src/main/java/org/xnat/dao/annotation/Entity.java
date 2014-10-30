package org.xnat.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.xnat.dao.DataSourceContextHolder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
	public String tableName();
	public String dataSourceId() default DataSourceContextHolder.DATASOURCE1;
}
