package com.peter.android;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class AppContext extends Application{
	
	private static Context sInstance = null;
    private static Thread sUiThread;

	
	public synchronized static Context getInstance(){
		if (sInstance == null) {
			throw new AppError("在未初始化前请不要使用该方法");
		}else {
			return sInstance;
		}
	}
	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		sUiThread = Thread.currentThread();
		
	}
	
	
	public static void warnIfUiThread() {
        if (Thread.currentThread().equals(sUiThread)) {
            Log.w("Warn", "Method called on the UI thread", new Exception("STACK TRACE"));
        }
    }
}
