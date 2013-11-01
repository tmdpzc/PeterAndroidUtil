package com.peter.android.config;

/**
 * Asset 同时发布的设置
 */
public interface AssetResouce {
	
	public String getName();
	
	public int getInt(String key, int def);

	public String getString(String key);

	public String[] getStrings(String key);

	public boolean getBoolean(String key, boolean def);

	public double getDouble(String key, double def);

}
