package com.peter.android.config.cloud;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
/**
 * 云端消息控制
 */
public class CloudResourceManager {
	private static Map<String, CloudResource> cache = new HashMap<String, CloudResource>();
	
	

	public interface OnCloudPreferecensChangedListenear {
		public void onCloudPreferenceChanged(CloudResource cloudPreferences, String key);
	}
	
	private CloudResourceManager() {

	}

	public synchronized static CloudResource getResouce(Context context, String fileName) throws Exception {

		CloudResource ar = cache.get(fileName);
		if (ar != null) {
			return ar;
		}

		ar = new JsonCloudResouceImp(context, fileName);
		cache.put(fileName, ar);
		return ar;
	}

	private void shinkIfNeed(){
		
	}

	public void registerOnCloudPreferenceListener(OnCloudPreferecensChangedListenear listenear) {

	}

	public void unregisterOnCloudPrefenceLIstener(OnCloudPreferecensChangedListenear listenear) {

	}

}
