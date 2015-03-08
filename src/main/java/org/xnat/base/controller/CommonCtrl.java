package org.xnat.base.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xnat.base.service.BaseService;

/**
设计原则: 路径互斥, 简单明了, 既可复杂又可简单
设计: http://localhost:8080/[{prefix(路径标识)}](中括号表示可选)/{tableName}/{pkId}/{add.do/del.do/get.do/update.do}
	http://localhost:8080/{prefix}/{tableName}/key/value/{add.do/del.do/get.do/update.do}
	get.do: params: {
			selectFields(要获得哪些字段, 默认为全部): [],
			conditions(条件(支持and--并列条件), 默认为分页查询--page参数): [
					{ key: k1, denotation(默认为"="): ">", value: 3 }, 
					{}],
			page: { page(默认第1页): 1, limit(每页显示多少个, 默认50): 10, start(从第几个开始, 默认为0): 0 }
		}
		result: { 
			success(默认为true): true,
			status: 200,
			serSay(服务器说): { tip: "", warn: "", err: "" } 
		}

 * @author xnat
 */
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
