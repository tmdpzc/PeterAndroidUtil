package com.peter.test;

import android.content.Context;

import com.peter.android.config.CustomResouce;
import com.peter.android.config.AssetResouceManager;

public class TestAssetConfig {
	public static void testAsset (Context context) {
		try {
			CustomResouce ar = AssetResouceManager.getResouce(context, "config.json");
			System.out.println(ar.getInt("int1", -1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
