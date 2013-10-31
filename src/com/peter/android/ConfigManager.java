package com.peter.android;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class ConfigManager {
	private static ConfigManager sInstance = null;

	private ConfigManager() {
		sp = PreferenceManager.getDefaultSharedPreferences(AppContext.getInstance());
	}

	private SharedPreferences sp = null;

	public synchronized static ConfigManager getInstance() {
		if (sInstance == null) {
			sInstance = new ConfigManager();
		}
		return sInstance;
	}

	public long getLong(String tag) {
		return sp.getLong(tag, -1);
	}

	public int getInt(String tag) {
		return sp.getInt(tag, -1);
	}

	public String getString(String tag) {
		return sp.getString(tag, "");
	}

	public boolean haveDone(String action) {
		String _tag = "have_done_" + action;
		return !sp.getBoolean(_tag, false);
	}

	public void setActionDone(String action) {
		String _tag = "have_done_" + action;
		setBooleanValue(_tag, true);
	}

	public void markActionNow(String action) {
		String _tag = "did_on_" + action;
		setLongValue(_tag, System.currentTimeMillis());
	}

	public boolean shouldTackAction(String action, long period) {
		String _tag = "did_on_" + action;
		Long lastNotify = sp.getLong(_tag, 0);
		if (-1 == lastNotify) {
			return true;
		} else {
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastNotify >= period) {
				return true;
			}
			return false;
		}
	}

	private void setBooleanValue(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	private void setLongValue(String key, long value) {
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	private void setIntValue(String key, int value) {
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	private void setStringValue(String key, String value) {
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
}
