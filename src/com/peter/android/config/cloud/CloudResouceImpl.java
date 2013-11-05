package com.peter.android.config.cloud;

import com.peter.android.config.CustomResouce;

public class CloudResouceImpl implements CloudResource{
	
	private CustomResouce innerResouce = null;

	@Override
	public String getName() {
		return innerResouce.getName();
	}

	@Override
	public int getInt(String key, int def) {
		return innerResouce.getInt(key, def);
	}

	@Override
	public String getString(String key) {
		return innerResouce.getString(key);
	}

	@Override
	public String[] getStrings(String key) {
		return innerResouce.getStrings(key);
	}

	@Override
	public boolean getBoolean(String key, boolean def) {
		return innerResouce.getBoolean(key, def);
	}

	@Override
	public double getDouble(String key, double def) {
		return innerResouce.getDouble(key, def);
	}

}
