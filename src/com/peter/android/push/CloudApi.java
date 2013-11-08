package com.peter.android.push;



public interface CloudApi {
	/*是否需要更新数据*/
	public boolean needPull(String lastKey);
	public HttpPush pull(String key);
	
}
