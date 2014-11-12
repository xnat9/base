package org.xnat.service;


import com.google.gson.JsonObject;

public interface BaseService {
	JsonObject updateEntityById(Object obj);
	
	JsonObject addEntity(Object entity);

}
