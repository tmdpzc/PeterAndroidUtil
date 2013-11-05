package com.peter.android;

import android.app.Application;
import android.content.Context;

public class AppContext extends Application{
	
	private static Context sInstance = null;
	
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
	}
}
