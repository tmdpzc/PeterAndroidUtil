package com.peter.asyncui.core;

import android.util.Log;

/**
 * Log Util for debugging
 * @author peizicheng@conew.com
 * @date 2013-10-24
 */
public class AysnUILog {

	public static void d(String msg){
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}
	public static void e(Object obj){
		
	}
	
	
	private static final String TAG = "AsynUI";
	private static final boolean DEBUG = true;

}
