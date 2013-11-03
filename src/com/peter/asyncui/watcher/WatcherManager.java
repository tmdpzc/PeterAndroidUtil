package com.peter.asyncui.watcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.text.TextUtils;

/**
 * Watcher 的管理类
 * 
 * @author peizicheng@conew.com
 * @date 2013-10-25
 */
public abstract class WatcherManager {

	protected static final int _DELTA = 200;

	protected List<Watcher> watchers = Collections.synchronizedList(new LinkedList<Watcher>());

	protected static WatcherManager sInstance = null;

	public void addWatcher(Watcher watcher) {
		if (watcher != null) {
			watchers.add(watcher);
		}
	}

	public Collection<Watcher> getWatchers() {
		List<Watcher> result = new ArrayList<Watcher>();
		Collections.copy(watchers, result);
		return result;
	}

	public Watcher getWatcherByTag(String tag) {
		if (TextUtils.isEmpty(tag)) {
			return null;
		}
		for (Watcher w : watchers) {
			if (tag.equals(w.getTag())) {
				return w;
			}
		}
		return null;
	}

	public void removeWatcher(Watcher watcher) {
		watchers.remove(watcher);
	}
	
	public void resetWatchers() {
		for (Watcher w : watchers) {
			w.reset();
		}
	}

	/**
	 * start everything
	 */
	public abstract void start();

	/**
	 * pause for a moment
	 */
	public abstract void pause();

	/**
	 * resume from pause
	 */
	public abstract void resume();

	/**
	 * shutdown the world
	 */
	public abstract void stop();


}
