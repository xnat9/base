package org.xnat.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

/**
 * 公共常用工具类
 * @author xnat
 * Oct 6, 2014 9:58:10 AM
 */
public class Utils {
	private Utils() {}
	
	/**
	 * 得到int的时间辍
	 * @return
	 */
	public static Integer getIntTime() {
		String t = String.valueOf(System.currentTimeMillis());
		return Integer.parseInt(t.substring(0, t.length()-3));
	}
    
	/**
	 * 转换一般字符患到base64编码的字符串 v2
	 * @param str
	 * @return
	 */
	public static String toBase64(String str) {
		Base64 encoder =  new Base64();
		return new String(encoder.encode(str.getBytes()));
	}
    
	
	/**
	 * 获得时间字符串(格式为: 年:月:日    时:分:秒)
	 * @return
	 */
	public static String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		return sdf.format(date);
	}
	/**
	 * 将String转化为Datetime类型
	 */
	public static Date getDatetime(String str){
		Date date = null;
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		try {
			date = fmt.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将String转化为Date类型
	 */
	public static Date getDate(String str){
		Date date = null;
		DateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
		try {
			date = fmt.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 格式化字符串到日期类型
	 * @param str
	 * @return
	 */
	public static Date formatStringToDate(String str) {
		String[] pattrns = {"yyyy-MM-dd H:m:s", "yyyy/MM/dd H:m:s", "yyyy/MM/dd", "MM/dd/yyyy", "yyyy-MM-dd"};
		Date d = null;
		SimpleDateFormat sdf = null;
		for (String p : pattrns) {
			sdf = new SimpleDateFormat(p);
			try {
				d = sdf.parse(str);
				if (d != null) return d;
			} catch (ParseException e) {
			}
		}
		return d;
	}
	/**
	 * 格式化datebox类型的日期
	 */
	public static Date formatString(String str) {
		String[] pattrns = {"MM/dd/yyyy"};
		Date d = null;
		SimpleDateFormat sdf = null;
		for (String p : pattrns) {
			sdf = new SimpleDateFormat(p);
			try {
				d = sdf.parse(str);
				if (d != null) return d;
			} catch (ParseException e) {
			}
		}
		return d;
	}
	/**
	 * 
	 * 获得时间字符串(格式为: pattern)
	 * 时间格式: h:m:s
	 * 日期格式: yyyy:MM:dd
	 * @return
	 */
	public static String getDateString(Date date, String pattrn) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattrn);
			return sdf.format(date);
		} catch (IllegalArgumentException e) {
			return "时间格式化参数(pattrn)错误";
		}
	}
	
	/**
	 * 字符串转换为数字
	 * @param str
	 * @return 如果出现异常，则返回-1
	 */
	public static Integer toInt(Object value) {
		if (value == null) return null;
		else if (value instanceof String) {
			return Integer.valueOf(value.toString());
		} 
		else if (value instanceof Integer) return (Integer) value;
		else if (value instanceof Boolean) {
			if ((Boolean)value) return 1;
			else return 0;
		}
		return null;
	}
	public static Boolean toBool(Object value) {
		if (value instanceof String) return Boolean.valueOf(value.toString());
		else if (value instanceof Integer) {
			if ((Integer) value > 0) return true;
			else return false;
		}
		return null;
	}
	/**
	 * md5 加密
	 * @param s
	 * @return 出现异常, 则返回null
	 */
	public static String md5Encrypt(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes());
			byte[] bts = md.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (byte b : bts) {
				if (Integer.toHexString(0XFF & b).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0XFF & b));
				else
					md5StrBuff.append(Integer.toHexString(0XFF & b));
			}
			s = md5StrBuff.toString();
			return s;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 首分字母大写
	 * @param str
	 * @return
	 */
	public static String firstLetterToUpper(String str) {
		if (str == null || str.length() < 1 || !Character.isLetter(str.charAt(0))) return str;
		char[] as = str.toCharArray();
		as[0] -= 32;
		return String.valueOf(as);
	}
	/**
	 * 首分字母小写
	 * @param str
	 * @return
	 */
	public static String firstLetterToLower(String str) {
		if (str == null || str.length() < 1 || !Character.isLetter(str.charAt(0))) return str;
		char[] as = str.toCharArray();
		as[0] += 32;
		return String.valueOf(as);
	}
}
