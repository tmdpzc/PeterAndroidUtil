package com.peter.task;


/**
 * 任务监视器
 */
public interface TaskObserver<Result,Progress> {
	
	/**
	 * 通知开始运行
	 * @param id task id 
	 */
	public void notifyStarted(int id);
	
	/**
	 * 通知完成
	 * @param id task id 
	 */
	public void notifyStoped(int id);
	
	/**
	 * 超时通知
	 * @param id task id 
	 */
	public void notifyTimeOut(int id);
	/**
	 * 更新进度条
	 * @param id task id 
	 */
	public void onProgressUpdate(Progress... values);
	
	/**
	 * 任务执行成功完成
	 * @param id task id 
	 */
	public void onResult(int id,Result result);
	/**
	 * 任务执行错误
	 * @param e
	 */
	public void onError(int id,Exception e);
}
