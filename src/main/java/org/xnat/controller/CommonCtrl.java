package org.xnat.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xnat.service.BaseService;

import com.google.gson.JsonObject;

@Controller
@RequestMapping("common")
public class CommonCtrl {
	@Autowired
	private BaseService baseService;
//	public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException   
//    } 
	@RequestMapping(value="imgUpload", method=RequestMethod.POST)
	public void imgUpload(@RequestParam("imgFile") MultipartFile file, HttpServletResponse resp, HttpServletRequest req) throws IOException {
//		User_info user = (User_info) Utils_ctrl.getSessionM_user(resp, req);
//		if (user == null) return;
//		JsonObject result = new JsonObject();
//		if (file.isEmpty()) {
//			result.addProperty("success", false);
//			resp.getWriter().write(result.toString()); return;
//		}
//		result.addProperty("success", true);
//		File f = new File("test.jpg");
//		SaveFileFromInputStream(file.getInputStream(), "./", file.getOriginalFilename());		
//		file.transferTo(f);
//		FileOutputStream fos = new FileOutputStream(f);
//		f.createNewFile();
//		MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
//		reqEntity.setCharset(Consts.UTF_8);
//		File f = new File(file.getOriginalFilename());
//		file.transferTo(f);
//		reqEntity.addPart(PhpInter.imgUpoad.upload, new FileBody(f));
//		String url = PhpInter.imgUpoad.prefixurl;
//		url += PhpInter.imgUpoad.a+"=UplodePhoto&"+PhpInter.imgUpoad.c+"=User&"+
//				PhpInter.imgUpoad.sid+"="+user.getUid()+"&"+PhpInter.imgUpoad.uid+"="+user.getUid();
////				+"&"+PhpInter.imgUpoad.uploadfile+"=";
//		String jsonStr = Utils_ser.loadJsonStrFromUrlByPost(url, reqEntity.build());
//		JsonObject jo = DataUtils.toJsonObject(jsonStr);
//		result.addProperty("src", "http://192.168.31.212"+jo.get("data").getAsString());
////		result = baseService.imgUpload(params);
//		resp.getWriter().write(result.toString());
	}
}
