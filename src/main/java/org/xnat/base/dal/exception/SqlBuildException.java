package org.xnat.base.dal.exception;

/**
 * sql构建时异常
 * @author xnat
 */
public class SqlBuildException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public SqlBuildException() {}
	public SqlBuildException(String message) { super(message); }
}
