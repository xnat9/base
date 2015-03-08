package org.xnat.base.service;


import com.google.gson.JsonObject;

public interface BaseService {
	JsonObject updateEntityById(Object obj);
	
	JsonObject addEntity(Object entity);

}
