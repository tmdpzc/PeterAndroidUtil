package com.peter.android.push;

import android.graphics.drawable.Drawable;

/**
 * 云端资源
 * @author peizicheng@gmail.com
 * @date 2013-10-30
 */
public abstract class RemoteResource {

	public abstract String getString(int id);

	public abstract int getInt(int id);

	public abstract boolean getBoolean(int id);

	public abstract Drawable getDrawable();
	
}
