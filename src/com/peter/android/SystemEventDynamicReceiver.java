package com.peter.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * 监控系统的各种事件
 * @author peizicheng@gmail.com
 * @date 2013-10-29
 */
public class SystemEventDynamicReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
	}
	/**
	 * 注册该广播
	 * @param context
	 * @param receiver
	 */
	private void registeBroadcast(Context context,BroadcastReceiver receiver){
		IntentFilter filter = new IntentFilter();
		/**
		 * 注册广播
		 */
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Intent.ACTION_SCREEN_ON);
		context.registerReceiver(receiver, filter);
	}
}
