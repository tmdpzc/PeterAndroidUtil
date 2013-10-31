package com.peter.android.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
/**
 * 后台
 * @author peizicheng@gmail.com
 * @date 2013-10-30
 */
public class PushService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

}
