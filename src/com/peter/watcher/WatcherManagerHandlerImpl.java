package com.peter.watcher;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 使用Handler 来实现定时功能
 * 
 * @author peizicheng@gmail.com
 * @date 2013-10-29
 */
public class WatcherManagerHandlerImpl extends WatcherManager {

	private static final int _DELTA = 1000;

	private static final int RUNNING = 1;
	private static final int PAUSING = 2;

	private static final int MSG_RUN = 1;
	private static final int MSG_QUIT = 2;

	private volatile int mStatu = 0;

	private ServiceHandler mHandler;

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				for (Watcher w : watchers) {
					w.tick();
				}
			} catch (Exception e) {
			}
			log("Ticking--------");

			mHandler.postDelayed(this, _DELTA);
		}
	};

	private Looper mLooper;

	private static WatcherManagerHandlerImpl sInstance = null;

	private WatcherManagerHandlerImpl() {

	}

	public synchronized static WatcherManager getInstance() {
		if (sInstance == null) {
			sInstance = new WatcherManagerHandlerImpl();
		}
		return sInstance;
	}

	/**
	 * 异步处理Handler
	 * 
	 * @author peizicheng@gmail.com
	 * @date 2013-10-29
	 */
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			log("Get Msg:" + msg.what);
			switch (msg.what) {
			case MSG_RUN:
				mHandler.post(mRunnable);
				break;
			case MSG_QUIT:
				mHandler.removeCallbacks(mRunnable);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void start() {
		if (mStatu == 0) {
			log("Start");
			HandlerThread thread = new HandlerThread("WatcherManager[" + this.getClass().getSimpleName() + "]",
					android.os.Process.THREAD_PRIORITY_BACKGROUND);
			thread.start();
			mLooper = thread.getLooper();
			mHandler = new ServiceHandler(mLooper);
			mHandler.sendEmptyMessage(MSG_RUN);
			mStatu = RUNNING;
		}
	}

	@Override
	public void pause() {
		if (mStatu == RUNNING) {
			log("Pause");
			mHandler.sendEmptyMessage(MSG_QUIT);
			mStatu = PAUSING;
		}
	}

	@Override
	public void resume() {
		if (mStatu == PAUSING) {
			log("Resume");
			mHandler.sendEmptyMessage(MSG_RUN);
			mStatu = RUNNING;
		}
	}

	@Override
	public void stop() {
		if (mStatu != 0) {
			log("Stop");
			if (mLooper != null) {
				mLooper.quit();
				mLooper = null;
			}
			mHandler = null;
			mStatu = 0;
		}
	}
	
	private static final String TAG = "WM-Handler";
	private static final boolean DEBUG = true;

	public static void log(String msg) {
		if (DEBUG) {
			Log.d(TAG, msg);
		}
	}
}
