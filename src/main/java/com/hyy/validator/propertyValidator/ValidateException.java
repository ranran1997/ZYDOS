package com.hyy.validator.propertyValidator;

/**
 * 
 * 功能描述：验证失败异常
 *
 */
public class ValidateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5899358103997771365L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}
}
