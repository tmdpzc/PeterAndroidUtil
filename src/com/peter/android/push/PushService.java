package com.peter.android.push;

import android.app.IntentService;
import android.content.Intent;
/**
 * push的后台，Http 过会儿
 * @author peizicheng@gmail.com
 * @date 2013-10-30
 */
public class PushService extends IntentService{

	public PushService(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
	}

	private void onHandle_tick(){
		
	}
}
