package com.peter.android;

public class AppError extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4391350457671793250L;

	public AppError(String string) {
		super(string);
	}

}
