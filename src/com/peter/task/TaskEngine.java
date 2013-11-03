package com.peter.task;
/**
 * 扫描引擎，应该是单例的
 */
public interface TaskEngine {
	/**
	 * 添加任务
	 * 
	 * @param task
	 * @return
	 */
	public int addTask(Task task);

	/**
	 * 添加任务，并在延迟delay时间后运行
	 * 
	 * @param task
	 * @param delay
	 * @return
	 */
	public int addTask(Task task, long delay);

	/**
	 * 结束掉所有线程
	 */
	public void shutdown();
	/**
	 * 增加监视器
	 * @param observer
	 */
	public void addTaskObserver(TaskObserver observer);
	/**
	 * 移除监视器
	 * @param observer
	 */
	public void removeTaskObserver(TaskObserver observer);
	/**
	 * 得到控制器
	 * @param controller
	 */
	public TaskController getTaskController();
	
}
