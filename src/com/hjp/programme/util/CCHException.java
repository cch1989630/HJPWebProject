package com.hjp.programme.util;

public class CCHException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2629187709100856769L;

	private String exceptionCode;
	
	private String exceptionMessage;
	
	public CCHException(String exceptionCode, String exceptionMessage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
}
