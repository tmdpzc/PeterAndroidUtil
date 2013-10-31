package com.peter.android;

import android.app.IntentService;
import android.content.Intent;

public class ControledIntentService extends IntentService{

	public ControledIntentService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
	}

}
