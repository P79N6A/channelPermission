package com.haier.svc.api.controller.util.http;

/**
 * Dao层异常信息
 * @FileName:JobException.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2014-10-15 上午10:44:24
 */
public class JobException extends RuntimeException {

	/**
	 * @fields serialVersionUID 
	 */
	private static final long serialVersionUID = 223434434545343434L;

	public JobException() {
		super();
	}

	public JobException(String message, Throwable cause) {
		super(message, cause);
	}

	public JobException(String message) {
		super(message);
	}

	public JobException(Throwable cause) {
		super(cause);
	}

}