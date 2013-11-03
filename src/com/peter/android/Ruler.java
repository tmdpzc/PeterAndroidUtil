package com.peter.android;

import com.peter.asyncui.core.EventManager;
import com.peter.asyncui.core.EventMangerImpl;
import com.peter.asyncui.watcher.WatcherManager;
import com.peter.asyncui.watcher.WatcherManagerHandlerImpl;

public class Ruler {
	public static EventManager em() {
		return EventMangerImpl.getInstance();
	}

	public static ConfigManager cm() {
		return ConfigManager.getInstance();
	}

	public static WatcherManager wm() {
		return WatcherManagerHandlerImpl.getInstance();
	}
}
