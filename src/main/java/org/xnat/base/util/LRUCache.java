package org.xnat.base.util;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.map.LRUMap;
import org.springframework.cache.concurrent.ConcurrentMapCache;

/**
 * 简单缓存对象实现
 * @author xnat
 * @param <K>
 * @param <V>
 */
public class LRUCache extends LRUMap {
	private static final long serialVersionUID = 1L;

//	private Lock lock = new ReentrantLock();
	
	public LRUCache() {}
	/**
	 * 默认最大100
	 */
	public LRUCache(int maxSize) {
		super(maxSize);
	}
	
	@Override
	public synchronized Object get(Object key) {
		return super.get(key);
	}
	@Override
	public synchronized Object put(Object key, Object value) {
		return super.put(key, value);
	}
}
