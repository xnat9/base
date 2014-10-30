package org.xnat.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * service 层的工具类
 * @author xnat
 * Oct 23, 2014 10:58:37 AM
 */
public final class Utils_ser {
	private Utils_ser() {}
	
	/**
	 * 从配置文件(config.properties)中获取属性的值
	 * @param attr
	 * @return
	 */
	public static String getValueFromConfig(String attr) {
		Properties prop = new Properties();
		try {
			prop.load(Utils_ser.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		return prop.getProperty(attr);
	}
	/**
	 * 得到php接口的的url
	 * @param interName(接口名)
	 * @return
	 * Oct 6, 2014 9:52:22 AM
	 */
	public static String getPhpInterUrl(String interName) {
		return getValueFromConfig("phpInterfacePrefix")+getValueFromConfig(interName);
	}
	
	/**
	 * 从一个url地址加载一个json字符串 用原生jdk URLConnection实现
	 * @param url
	 * @return
	 */
	public static String loadJsonStrFromUrl_v1 (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
        }
        return json.toString();
    }
	/**
	 * 从一个url地址加载一个json字符串 用appache的HttpClient实现
	 * @param url
	 * @return
	 * Oct 23, 2014 11:06:02 AM
	 */
	public static String loadJsonStrFromUrl_v1_2(String url) {
		StringBuilder json = new StringBuilder();
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse resp = httpClient.execute(httpGet);
//			resp.getEntity().getContentEncoding();
			BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String inputLine = null;
			while ( (inputLine = br.readLine()) != null) {
                json.append(inputLine);
            }
			br.close();
			return json.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加载json格式的字符串
	 * @param url
	 * @param bean
	 * @return
	 * Oct 6, 2014 11:05:16 AM
	 */
	public static String loadJsonStrFromUrlByPost(String url, Object bean) {
		StringBuilder json = new StringBuilder();
//		@SuppressWarnings("deprecation")
//		HttpClient httpClient = new DefaultHttpClient();
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
//			resp.getEntity().getContentEncoding();
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			try {
				for (Field f : bean.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					if (f.get(bean) != null) {
						valuePairs.add(new BasicNameValuePair(f.getName(), f.get(bean).toString()));
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));
			HttpResponse resp = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String inputLine = null;
			while ( (inputLine = br.readLine()) != null) {
				json.append(inputLine);
			}
			br.close();
			return json.toString();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加载json格式的字符串
	 * @param url 地址 post方式 
	 * @param params
	 * @return
	 * Oct 6, 2014 10:55:15 AM
	 */
	public static String loadJsonStrFromUrlByPost(String url, Map<String, Object> params) {
		StringBuilder json = new StringBuilder();
//		@SuppressWarnings("deprecation")
//		HttpClient httpClient = new DefaultHttpClient();
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost httpPost = new HttpPost(url);
//			resp.getEntity().getContentEncoding();
			List<NameValuePair> valuePairs = new ArrayList<NameValuePair>();
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				valuePairs.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(valuePairs));
			HttpResponse resp = httpClient.execute(httpPost);
			BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String inputLine = null;
			while ( (inputLine = br.readLine()) != null) {
				json.append(inputLine);
			}
			br.close();
			return json.toString();
		} catch (IOException e) {
//			e.printStackTrace();
		}
		return null;
	}
}
