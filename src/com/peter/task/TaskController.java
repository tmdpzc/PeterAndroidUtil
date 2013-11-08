package com.peter.task;

/**
 *  控制器
 */
public interface TaskController {
	/**
	 * Cancel the task
	 */
	public void cancel(int taskid);
	/**
	 * Pause the task 
	 */
	public void pause(int taskid);
	/**
	 * Resume the task 
	 */
	public void resume(int taskid);
	
}
