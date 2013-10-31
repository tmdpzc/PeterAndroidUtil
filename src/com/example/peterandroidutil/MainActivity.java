package com.example.peterandroidutil;

import java.io.IOException;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.peter.android.Ruler;
import com.peter.asyncui.core.Event;
import com.peter.asyncui.core.Schema;
import com.peter.asyncui.core.android.BaseEventFragmentActivity;
import com.peter.test.TestJSON;
import com.peter.test.TestUtil;
import com.peter.watcher.Watcher;
import com.peter.watcher.WatcherManager;
import com.peter.watcher.WatcherManagerImpl;

public class MainActivity extends BaseEventFragmentActivity implements OnClickListener {
	
	WatcherManager wm = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wm = Ruler.wm();
		wm.addWatcher(new Watcher(0, null) {
			
			@Override
			public Event doWatch() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void dispatchEvent(Event event) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onUIEventInUiThread(Event event) {
		System.out.println("OnUiEvent");
		TextView tv = (TextView) findViewById(R.id.textView2);
		tv.setText("收到消息:" + System.currentTimeMillis());
	}

	public void testEvent(View view) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Ruler.em().broadCastEvent(Schema.UI_SCHEMA, new Event());
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt1:
			wm.start();
			break;
		case R.id.bt2:
			wm.pause();
			break;
		case R.id.bt3:
			wm.resume();
			break;
		case R.id.bt4:
			wm.stop();
			break;

		default:
			break;
		}
	}

}