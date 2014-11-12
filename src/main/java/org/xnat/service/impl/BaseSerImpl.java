package org.xnat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xnat.dao.Utils_dao;
import org.xnat.dao.util.BaseDaoUtil;
import org.xnat.dao.util.DataUtils;
import org.xnat.service.BaseService;

import com.google.gson.JsonObject;

@Service
public class BaseSerImpl implements BaseService {

	@Autowired
	private BaseDaoUtil baseDaoUtil;
	
	@Override
	public JsonObject updateEntityById(Object obj) {
		JsonObject result = new JsonObject();
		String idStr = Utils_dao.getIdField(obj.getClass()).getName();
		if (DataUtils.bean_getFieldValue(obj, idStr) == null) {
			result.addProperty("msg", "id为null"); return result;
		};
		int r = baseDaoUtil.updateById(obj);
		if (r > 0) result.addProperty("success", true);
		else result.addProperty("success", false);
		return result;
	}

	@Override
	public JsonObject addEntity(Object entity) {
		JsonObject result = new JsonObject();
		int r = baseDaoUtil.insert(entity);
		if (r > 0) result.addProperty("success", true);
		else result.addProperty("success", false);
		return result;
	}

//	@Override
//	public JsonObject imgUpload(Map<String, Object> params) {
//		JsonObject result = new JsonObject();
//		//		String url = Utils_ser.getPhpInterUrl("imgUpload");
//		String json = Utils_ser.loadJsonStrFromUrlByPost(PhpInter.imgUpoad.prefixurl, params);
//		System.out.println(json+" ------------------------------");
//		JsonObject jo = DataUtils.toJsonObject(json);
//		System.out.println(jo.toString()+" ============");
//		result.addProperty("success", true);
//		
//		return result;
//	}
}
