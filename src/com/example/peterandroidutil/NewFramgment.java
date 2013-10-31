package com.example.peterandroidutil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peter.asyncui.core.Event;
import com.peter.asyncui.core.android.BaseEventFragment;

public class NewFramgment extends BaseEventFragment{

	@Override
	protected void onUIEventInUiThread(Event event) {
		TextView tv = (TextView)getActivity().findViewById(R.id.fg_text2);
		tv.setText("收到消息:" + System.currentTimeMillis());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  return inflater.inflate(R.layout.fragment_main, container, false);
	}
}
