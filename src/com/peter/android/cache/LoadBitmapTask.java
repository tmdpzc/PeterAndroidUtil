package com.peter.android.cache;

import java.util.concurrent.atomic.AtomicBoolean;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.peter.android.cache.BitmapLoader.TaskType;

/**
 * 
 * @author peizicheng@conew.com
 * @date 2013-9-6
 */
public class LoadBitmapTask implements Runnable {

	ImageView imageView;
	BitmapLoader loader;
	String keyStr;
	String memoryCacheKey;
	Object obj;
	TaskType type;

	static Handler handler =  new Handler();

	private static int RES_DEFAULT_ICON = R.drawable.ic_dialog_email;

	private Bitmap defaultBitmap;

	public LoadBitmapTask(ImageView imageView, BitmapLoader loader, String keyStr, String memoryKey, TaskType type,
			 Object o) {
		super();
		this.imageView = imageView;
		this.loader = loader;
		this.keyStr = keyStr;
		this.obj = o;// TODO 扩展
		this.memoryCacheKey = memoryKey;
		this.type = type;
	}

	@Override
	public void run() {
		if (waitIfPaused())
			return;
		if (delayIfNeed())
			return;
		if (isCancled())
			return;
		Bitmap db = loader.getBitmapFromCache(keyStr);
		if (db == null) {
			log("Cache未命中:View:" + imageView.hashCode());
			switch (type) {
			case UNINSTLLED_APK:
				db = loadIconOfUninstalledApk(loader.mContext, keyStr);
				break;
			case INSTALLED_APK:
				db = loadIconOfInstalledPackage(loader.mContext, keyStr);
				break;

			default:
				break;
			}
			if (db != null) {
				loader.cacheBitmap(keyStr, db);
			}
		}else {
			log("Cache已命中:View:" + imageView.hashCode() + ":bitmap:" + db.hashCode());
		}
		if (db != null) {
			setBitmap(imageView, db);
			return;
		}

		if (defaultBitmap != null) {
			setBitmap(imageView, defaultBitmap);
			return;
		}
		setResouce(imageView, RES_DEFAULT_ICON);
	}

	private void setBitmap(final ImageView view, final Bitmap bm) {
		if (bm == null || view == null) {
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				log("Succeed SetBitmap:" + view.hashCode() + ":bitmap:" + bm.hashCode());
				view.setImageBitmap(bm);
			}
		});
	}

	private void setResouce(final ImageView view, final int resId) {
		if (resId == 0 || view == null) {
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				log("Succeed SetResouce:" + view.hashCode() +  ":resId:" + resId);
				view.setImageResource(resId);
			}
		});
	}

	/**
	 * return null if failed
	 * 
	 * @param context
	 * @param pkgName
	 * @return
	 */
	private static Bitmap loadIconOfInstalledPackage(Context context, String pkgName) {
		Bitmap bitmap = null;
		try {
			Drawable db = context.getPackageManager().getApplicationIcon(pkgName);
			bitmap = ((BitmapDrawable)db).getBitmap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * return null if failed
	 * 
	 * @param context
	 * @param filePath
	 * @return
	 */
	private static Bitmap loadIconOfUninstalledApk(Context context, String filePath) {
		Bitmap icon = null;
		log("start:Uninstalled:" + filePath);
		try {
			if (filePath.endsWith(".apk")) {
				PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(filePath,
						PackageManager.GET_ACTIVITIES);
				if (packageInfo != null) {
					ApplicationInfo appInfo = packageInfo.applicationInfo;
					if (Build.VERSION.SDK_INT >= 8) {
						appInfo.sourceDir = filePath;
						appInfo.publicSourceDir = filePath;
					}
					Drawable _db = appInfo.loadIcon(context.getPackageManager());
					icon = ((BitmapDrawable)_db).getBitmap();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log("end:Uninstalled:" + (icon == null ? "failed" : "success") + filePath);

		return icon;
	}

	private AtomicBoolean bCancled = new AtomicBoolean(false);

	public boolean isCancled() {
		return bCancled.get();
	}

	private boolean waitIfPaused() {
		return false;
	}

	private boolean delayIfNeed() {
		return checkTaskIsNotActual();
	}

	private boolean checkTaskIsNotActual() {
		String currentCacheKey = loader.getLoadingKeyForView(imageView);
		boolean imageViewWasReused = !memoryCacheKey.equals(currentCacheKey);
		if (imageViewWasReused) {
			log("Canceled");
		}
		return imageViewWasReused;
	}

	public void cancle() {
		this.bCancled.set(true);
	}

	private static void log(String message) {
		if (CacheUtil.DBG)
			Log.i("LoaderTask", message);
	}

}
