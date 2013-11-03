package com.peter.test;

import com.peter.asyncui.core.Event;
import com.peter.asyncui.watcher.Watcher;
import com.peter.asyncui.watcher.WatcherManager;
import com.peter.asyncui.watcher.WatcherManagerImpl;

public class TestUtil {
	public static final void testWatcher() {
		WatcherManager wm = WatcherManagerImpl.getInstance();
		Watcher w = new TestWatcher(10, "a");
		wm.addWatcher(w);
		w = new TestWatcher(23, "b");
		wm.addWatcher(w);
		w = new TestWatcher(27, "c");
		wm.addWatcher(w);
		wm.start();
	}

	public static final class TestWatcher extends Watcher {

		public TestWatcher(long period, String tag) {
			super(period, tag);
		}

		@Override
		public Event doWatch() {
			System.out.println(getTag() + ":do watch");
			return null;
		}

		@Override
		public void dispatchEvent(Event event) {

		}

	}
}
