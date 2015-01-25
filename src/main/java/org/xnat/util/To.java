package org.xnat.util;

import java.util.Map;

/**
 * 格式转换工具
 * @author xnat
 */
public interface To extends Util {
	/**
	 * toStr 转换成特定格式的字符串
	 * @param <K>
	 * @param <V>
	 */
	<K, V> Object toJsonObject(Map<K, V> map);
}
