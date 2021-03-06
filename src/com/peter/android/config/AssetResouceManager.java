package com.peter.android.config;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 管理类，用来从配置文件里面取数据
 */
public class AssetResouceManager {

	private static Map<String, CustomResouce> cache = new HashMap<String, CustomResouce>();

	private AssetResouceManager() {
		
	}

	public synchronized static CustomResouce getResouce(Context context,
			String fileName) throws Exception {

		CustomResouce ar = cache.get(fileName);
		if (ar != null) {
			return ar;
		}

		ar = new JsonResouceImp(context, fileName);
		cache.put(fileName, ar);
		return ar;
	}

}
