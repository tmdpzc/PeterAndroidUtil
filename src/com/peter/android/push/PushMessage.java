package com.peter.android.push;

/**
 * 推送的消息
 * 
 * @author peizicheng@gmail.com
 * @date 2013-10-30
 */
public abstract class PushMessage {

	public enum TimeType {
		RECIEVE, HANDLED
	}
	

	public static class PushTimeInfo {

		private long createTime;
		private long receiveTime;
		private long handledTime;

	}

	private PushTimeInfo timeInfo;

	public void touch(TimeType type) {
		if (type == TimeType.RECIEVE) {
			timeInfo.receiveTime = System.currentTimeMillis();
		} else if (type == TimeType.HANDLED) {
			timeInfo.handledTime = System.currentTimeMillis();
		}
	}


	protected boolean shouldCache;

	protected int id;

	protected String tag;

	public PushTimeInfo getTimeInfo() {
		return timeInfo;
	}

	public boolean isShouldCache() {
		return shouldCache;
	}

	public int getId() {
		return id;
	}

	public String getTag() {
		return tag;
	}
	

	
}
