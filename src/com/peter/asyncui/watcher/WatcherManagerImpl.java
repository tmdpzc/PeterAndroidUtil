package com.peter.asyncui.watcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.text.TextUtils;

/**
 * Watcher 总管类
 * 
 * 
 * @author peizicheng@conew.com
 * @date 2013-10-25
 */
//TODO 改成Handler的实现，Timer 的创建开销太大
public class WatcherManagerImpl extends WatcherManager {

	private static final int _DELTA = 200;

	private Timer timer = new Timer();

	private TimerTask timerTask = new WatcherTask();

	private static WatcherManager sInstance = null;

	private WatcherManagerImpl(){
		
	}

	public synchronized static WatcherManager getInstance() {
		if (sInstance == null) {
			sInstance = new WatcherManagerImpl();
		}
		return sInstance;
	}

	@Override
	public void start() {
		timer.cancel();
		resetWatchers();
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, _DELTA);
	}

	@Override
	public void pause() {
		timer.cancel();
		timer = null;
	}

	@Override
	public void resume() {
		timer.cancel();
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, _DELTA);
	}

	@Override
	public void stop() {
		timer.cancel();
		timer = null;
		resetWatchers();
	}

	private class WatcherTask extends TimerTask {

		@Override
		public void run() {
			for (Watcher w : watchers) {
				w.tick();
			}
		}

	}

}
