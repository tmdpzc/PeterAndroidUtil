package com.peter.watcher;

import java.util.concurrent.atomic.AtomicInteger;

import com.peter.asyncui.core.Event;

public abstract class Watcher implements Comparable<Watcher> {

	private long period = 1; /* 监控周期 */

	private boolean enable;

	private String tag;

	
	public Watcher(long period, String tag) {
		super();
		this.period = period;
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	private AtomicInteger counter = new AtomicInteger(0);

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		if (period <= 0) {
			throw new IllegalArgumentException("Peroid must greater than 0");
		}
		this.period = period;
	}

	/**
	 * 脉冲信号
	 */
	public void tick() {
		int count = counter.addAndGet(1);
		if (count % period == 0) {
			onWatch();
			counter.set(0);
		}
	}

	public void reset() {
		counter.set(0);
	}

	private void onWatch() {
		Event e = doWatch();
		if (e != null) {
			dispatchEvent(e);
		}
		return;
	}
	
	@Override
	public int compareTo(Watcher another) {
		return (int) (this.period - another.period);
	}
	

	public abstract Event doWatch();

	public abstract void dispatchEvent(Event event);

}
