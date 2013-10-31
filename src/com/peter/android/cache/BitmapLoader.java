package com.peter.android.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.peter.android.AppContext;
import com.peter.android.cache.CacheUtil.ImageSize;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 支持load两种 安装的App，和APK包,
 * 
 * 此前的版本是缓存Drawable，由于在2.2版本上Drawable上面有一个Callback会
 * 引用到View，进而引用到Activity造成Activiy无法回收，所以缓存Drawable是
 * 危险的，缓存Bitmap是稳妥的做法
 * @author peizicheng@conew.com
 * @date 2013-9-7
 */
public class BitmapLoader {
	
	public static enum TaskType{
		INSTALLED_APK,UNINSTLLED_APK
	}

	public static class Configuration {
		private int poolSize;
		private int cacheSize;
	}

	interface ImageLoadingListener {
	
		void onLoadingStarted(String imageUri, View view);
	
		void onLoadingFailed(String imageUri, View view, String failReason);
	
		void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);
	
		void onLoadingCancelled(String imageUri, View view);
	}

	private static Configuration sDefaultConfig = new Configuration();
	static {
		sDefaultConfig.poolSize = 1;
		sDefaultConfig.cacheSize = 50;
	}

	private ExecutorService mExecutor = Executors.newSingleThreadExecutor();
	private MemoryCache<String, Bitmap> mImageCache;

	Context mContext;

	private static BitmapLoader sInstance;

	private BitmapLoader(Configuration config, Context context) {
		mExecutor = Executors.newFixedThreadPool(config.poolSize);
		mImageCache = new BitmapCache(config.cacheSize);
		this.mContext = context;
	}

	public synchronized static BitmapLoader getInstance() {
		if (sInstance == null) {
			sInstance = new BitmapLoader(sDefaultConfig, AppContext.getInstance());
		}
		return sInstance;
	}
	/**
	 * 最重要的方法
	 * @param view
	 * @param key
	 */
	public void loadDrawable(ImageView view, String key,TaskType type) {
		//还是解决许多刷新的问题
		if (Looper.myLooper().getThread() != Thread.currentThread() ) {
			throw new RuntimeException("不可以在UI线程外调用loadDrawable方法");
		}
		view.setScaleType(ScaleType.FIT_CENTER);
		String memoryCacheKey = CacheUtil.generateKey(key, ImageSize.getDefault());
		prepareDisplayTaskFor(view, memoryCacheKey);
		switch (type) {
		case INSTALLED_APK:
			
			break;
		case UNINSTLLED_APK:
			
			break;

		default:
			break;
		}
		LoadBitmapTask task = new LoadBitmapTask(view,this,key,memoryCacheKey,type,null);
		submit(task);
	}
	
	void submit(final LoadBitmapTask task){
		mExecutor.execute(new Runnable() {
			@Override
			public void run() {
				task.run();
			}
		});
	}

	//new SparseArray<String>()
	private final Map<Integer, String> cacheKeysForImageViews = Collections.synchronizedMap(new HashMap<Integer, String>());
	
	String getLoadingKeyForView(ImageView imageView) {
		return cacheKeysForImageViews.get(imageView.hashCode());
	}
	
	void prepareDisplayTaskFor(ImageView imageView, String memoryCacheKey) {
		cacheKeysForImageViews.put(imageView.hashCode(), memoryCacheKey);
	}

	void cancelDisplayTaskFor(ImageView imageView) {
		cacheKeysForImageViews.remove(imageView.hashCode());
	}

	

	/**
	 * 同步拿到Icon,失败返回默认图标
	 * @param info
	 * @return
	 */
	public Bitmap loadIconSync(ApplicationInfo info) {
		Bitmap result = getBitmapFromCache(info.packageName);
		if (result != null) {
			return result;
		} else {
			result = loadIcon(mContext, info);
			if (result != null) {
				cacheBitmap(info.packageName, result);
				return result;
			}
		}
		return null;
	}

	public Bitmap getBitmapFromCache(String key) {
		return mImageCache.get(key);
	}

	public void cacheBitmap(String key, Bitmap bitmap) {
		log("cache:" + key);
		mImageCache.put(key, bitmap);
	}

	private static Bitmap loadIcon(Context context, ApplicationInfo info) {
		Bitmap bm = null;
		try {
			Drawable loadIcon = info.loadIcon(context.getPackageManager());
			bm = ((BitmapDrawable)loadIcon).getBitmap();
		} catch (Exception e) {
			//Ignore
		}
		return bm;
	}

	
	private void log(String message) {
		if (CacheUtil.DBG) Log.i("ImageLoader", message);
	}
	
}

