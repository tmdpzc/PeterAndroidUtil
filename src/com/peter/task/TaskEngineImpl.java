package com.peter.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import android.R.integer;

/**
 * Task engine 实现类
 */
public class TaskEngineImpl implements TaskEngine {

	private static final String LOG_TAG = "TaskEngineImpl";

	private static final int CORE_POOL_SIZE = 5;
	private static final int MAXIMUM_POOL_SIZE = 128;
	private static final int KEEP_ALIVE = 1;

	/**
	 * 线程工厂类
	 */
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		public Thread newThread(Runnable r) {
			return new Thread(r, "WorkerThread #" + mCount.getAndIncrement());
		}
	};

	private Object mLock = new Object();
	/**
	 * 所有的通知器
	 */
	private Set<TaskObserver> mObservers = Collections.synchronizedSet(new HashSet<TaskObserver>());
	/**
	 * 缓存Map
	 */
	private Map<Integer, Task> mTasks = Collections.synchronizedMap(new HashMap<Integer, Task>());
	
	private TaskController mController = new TaskController() {

		@Override
		public void resume(int id) {
			Task t = getTaskById(id);
			if (t != null) {
				
			}

		}

		@Override
		public void pause(int id) {
			// TODO Auto-generated method stub

		}

		@Override
		public void cancel(int id) {
			// TODO Auto-generated method stub

		}
		
		private Task getTaskById(int id){
			return mTasks.get(id);
		}
	};

	/**
     * 
     */
	private static class TaskWrapper {
		Task task;
		long delay;

		public TaskWrapper(Task task, long delay) {
			super();
			this.task = task;
			this.delay = delay;
		}

	}

	private class InterTask implements Runnable {

		private Task task;

		@Override
		public void run() {

		}

	}

	private final AtomicInteger mCount = new AtomicInteger(1);
	/**
	 * 工作线程池
	 */
	protected ExecutorService mWorkers = null;
	/**
	 * 工作队列
	 */
	protected BlockingQueue<TaskWrapper> mTaskQueue = new LinkedBlockingQueue<TaskWrapper>();

	/**
	 * 工作方法
	 */
	protected void run() {
		while (isAlive()) {
			try {
				dispatchLoop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void dispatchLoop() throws InterruptedException {
		TaskWrapper t = mTaskQueue.take();
	}

	private boolean isAlive() {
		return true;
		// TODO 增加控制方法
	}

	protected ExecutorService getWorkers() {
		return mWorkers;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public int addTask(Task task) {
		synchronized (mLock) {
			int id = mCount.getAndIncrement();
			task.setId(id);
			mTaskQueue.add(new TaskWrapper(task, 0));
			return id;
		}
	}

	@Override
	public int addTask(Task task, long delay) {
		synchronized (mLock) {
			int id = mCount.getAndIncrement();
			mTaskQueue.add(new TaskWrapper(task, delay));
			return id;
		}
	}

	@Override
	public void addTaskObserver(TaskObserver observer) {
		synchronized (mLock) {
			if (!mObservers.contains(observer)) {
				mObservers.add(observer);
			}
		}
	}

	@Override
	public void removeTaskObserver(TaskObserver observer) {
		mObservers.remove(observer);
	}

	@Override
	public TaskController getTaskController() {
		return mController;
	}
}
