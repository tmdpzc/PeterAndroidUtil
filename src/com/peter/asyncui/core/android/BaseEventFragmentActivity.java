package com.peter.asyncui.core.android;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.peter.android.Ruler;
import com.peter.asyncui.core.Event;
import com.peter.asyncui.core.EventListener;
import com.peter.asyncui.core.Schema;

public abstract class BaseEventFragmentActivity extends FragmentActivity implements EventListener{

	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	public void onEven(final Event event) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				onUIEventInUiThread(event);
			}
		});
	}
	
	protected abstract void onUIEventInUiThread(Event event);
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Ruler.em().subscribeEvent(Schema.UI_SCHEMA, this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Ruler.em().unsubscribeEvent(Schema.UI_SCHEMA, this);
	}
}
