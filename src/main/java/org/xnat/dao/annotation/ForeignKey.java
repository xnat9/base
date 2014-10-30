package org.xnat.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {
	/**
	 * 被关联的实体
	 * @return
	 * Oct 16, 2014 1:26:50 PM
	 */
    public Class<?> refEntity();
	
    /**
     * 被关联的实体的被关联字段
     * @return
     * Oct 16, 2014 1:26:41 PM
     */
    public String refFiled() default "id";
}
