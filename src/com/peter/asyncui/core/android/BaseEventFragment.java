package com.peter.asyncui.core.android;

import com.peter.android.Ruler;
import com.peter.asyncui.core.Event;
import com.peter.asyncui.core.EventListener;
import com.peter.asyncui.core.Schema;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseEventFragment extends Fragment implements EventListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Ruler.em().subscribeEvent(Schema.UI_SCHEMA, this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Ruler.em().unsubscribeEvent(Schema.UI_SCHEMA, this);
	}

	/**
	 * 必须做线程转换
	 */
	@Override
	public void onEven(final Event event) {
		 getActivity().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				onUIEventInUiThread(event);
			}
		});
	}
	protected abstract void onUIEventInUiThread(Event event);
	
}
