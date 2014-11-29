package org.xnat.service.module.dbInterface;

import com.google.gson.JsonObject;

public interface MainService {
	/**
	 * 执行任意sql
	 * @param sql
	 * @return {success: true/false, msg: 额外信息, data: 数据 }
	 * Nov 20, 2014 10:19:46 AM
	 */
	JsonObject doSql(String sql);
	
}
