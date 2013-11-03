package com.peter.task;

/**
 *  控制器
 */
public interface TaskController {
	/**
	 * Cancel the task
	 */
	public void cancel(int id);
	/**
	 * Pause the task 
	 */
	public void pause(int id);
	/**
	 * Resume the task 
	 */
	public void resume(int id);
	
}
