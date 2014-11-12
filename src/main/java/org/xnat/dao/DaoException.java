package org.xnat.dao;

/**
 * 抛 dao 层异常 通知 service ; 如果 抛SQLException(编译期异常) 不能通过, 用DaoException包装 再抛出
 * @author xnat
 * Nov 8, 2014 10:01:28 PM
 */
public class DaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
