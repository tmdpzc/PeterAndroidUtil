package com.peter.android.cache;

import java.util.Collection;
import java.util.Map;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 新的Drawable 问题
 * @author peizicheng@conew.com
 * @date 2013-9-6
 */
public class BitmapCache implements MemoryCache<String, Bitmap> {

	private LruCache<String, Bitmap> mImageCache;
	
	

	public BitmapCache(int maxSize) {
		mImageCache = new LruCache<String, Bitmap>(maxSize);
	}

	@Override
	public boolean put(String key, Bitmap value) {
		mImageCache.put(key, value);
		return true;
	}

	@Override
	public Bitmap get(String key) {
		return mImageCache.get(key);
	}

	@Override
	public void remove(String key) {
		mImageCache.remove(key);
	}

	@Override
	public Collection<String> keys() {
		return mImageCache.snapshot().keySet();
	}


	@Override
	public void clear() {
		mImageCache.evictAll();
	}

	public synchronized final Map<String, Bitmap> snapshot() {
		return mImageCache.snapshot();
	}
	
	public int maxSize(){
		return mImageCache.maxSize();
	}
}
