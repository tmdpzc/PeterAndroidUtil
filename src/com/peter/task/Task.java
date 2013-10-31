package com.peter.task;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import com.peter.asyncui.core.Event;

/**
 * 单个的任务
 * @author peizicheng@conew.com
 * @date 2013-10-25
 */
public class Task  implements Comparable<Task>, Callable<Event>{
	
	private static final AtomicInteger sIncCounter = new AtomicInteger(0);
	
	/**
	 * 调度的权重
	 */
	private int weight = sIncCounter.getAndIncrement();

	@Override
	public int compareTo(Task other) {
		return this.weight > other.weight ? 1: (this.weight == other.weight) ? 0 : -1;
	}

	@Override
	public Event call() throws Exception {
		return null;
	}
	
	
	
	
}
