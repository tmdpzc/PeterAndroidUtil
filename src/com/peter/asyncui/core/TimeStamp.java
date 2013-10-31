package com.peter.asyncui.core;


public class TimeStamp {
	public enum Tag {
		START_TIME,
		END_TIME
	}
	private long mCreateTime  = 0;
	private long mStartTime = 0;
	private long mEndTime   = 0;	
	
	public TimeStamp() {
		mCreateTime = System.currentTimeMillis();
	}
	
	public void touch(Tag tag) {
		switch (tag) {
		case START_TIME:
			mStartTime = System.currentTimeMillis();
			break;
		case END_TIME:
			mEndTime   = System.currentTimeMillis();
			break;
		default:
			/* NOP */
		}
	}

	public long getLifeTime() {
		touch(Tag.END_TIME);
		return mEndTime - mStartTime;
	}
	
	public double getLifeTimeSec() {
		touch(Tag.END_TIME);
		return (mEndTime - mStartTime) / 1000.0;
	}
	
	public int getLifeTimeSecInt() {
		touch(Tag.END_TIME);
		return (int)(mEndTime - mStartTime) / 1000;
	}
	
	public long getStartTimestamp() {
		return mStartTime;
	}
	
	public long getEndTimestamp() {
		return mEndTime;
	}
	
	public void reset() {
		mStartTime = System.currentTimeMillis();
	}
}
