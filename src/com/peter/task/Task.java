package com.peter.task;

import android.os.AsyncTask;
import android.text.GetChars;

/**
 * 单个的任务
 * 
 * @author peizicheng@conew.com
 * @param <V>
 * @date 2013-10-25
 */
public abstract class Task {

	/**
	 * Indicates the current status of the task. Each status will be set only
	 * once during the lifetime of a task.
	 */
	public enum Status {
		/**
		 * Indicates that the task has not been executed yet.
		 */
		PENDING,
		/**
		 * Indicates that the task is running.
		 */
		RUNNING,
		/**
		 * Indicates that the task is paused
		 */
		PAUSED,
		/**
		 * Indicates that {@link AsyncTask#onPostExecute} has finished.
		 */
		FINISHED,
	}

	private int _id = 0;
	
	private volatile Status statu;

	private TaskTimerStamp timerStamp = new TaskTimerStamp();

	public abstract void doWork();

	public void process() {
		
		
	}

	public abstract int getId();

	protected void setId(int id) {
		this._id = id;
	}

	public TaskTimerStamp getTimerStamp() {
		return timerStamp;
	}
	
	public Status getStatus(){
		return statu;
	}
}
