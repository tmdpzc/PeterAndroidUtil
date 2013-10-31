package com.peter.android;

import com.peter.toolbox.Md5Util;


/**
 * 用户的信息，用于收集上报之类的东东
 * @author peizicheng@gmail.com
 * @date 2013-10-30
 */
public class AndroidClientInfo {

	private static String imei ;
	private static String android_id;
	private static String language;
	
	
	public String getFingerPrint(){
		return Md5Util.md5(getFeatureString());
	}
	
	
	private String getFeatureString(){
		return null;
	}
}
