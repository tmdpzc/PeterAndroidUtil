package com.peter.android;

/**
 * A Exception class which use for wrap Exception
 */
public class AppException extends Exception {

	protected AppException(Throwable throwable) {
		super(throwable);
	}

	protected AppException(String message) {
		super(message);
	}

	protected AppException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}
	
	private int  type;
	
	public static final int RESOUCE = 0x0013;
	
	
	public static AppException resouce(String msg,Throwable throwable){
		return new AppException(msg, throwable);
	}
}
