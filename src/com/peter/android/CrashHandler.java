package com.peter.android;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.telephony.TelephonyManager;

public class CrashHandler implements UncaughtExceptionHandler {

	private String imei = "";
	private String appVersion = "";
	private String sysVersion = "";
	private String dumpKey = "";
	private String channalId = "";

	private int appFlag;

	private UncaughtExceptionHandler mOldHandler;

	private CrashHandler() {

	}

	private static CrashHandler instance;

	public synchronized static CrashHandler getInstance() {
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (ex instanceof AppException) {
			// Handle internal exception 
		}else {
			// Handle System exception
		}
	}
	
	
	

	public void regiterCrashHandler(Context context) {
		initParameters(context);
		mOldHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	private void initParameters(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
			ApplicationInfo ai = context.getApplicationInfo();
			appFlag = ai.flags;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Throwable  Exception
	 * @param e
	 * @return
	 */
	private String getExceptionKey(Throwable e){
		return null;
	}
	
	/**
	 * 发送上报数据
	 * @param crashReport
	 */
	private void sendCrashReport(String crashReport){
		
	}
	
	
}
