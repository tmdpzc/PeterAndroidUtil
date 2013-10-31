package com.peter.asyncui.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * Even 总管类
 * @author peizicheng@conew.com
 * @date 2013-10-24
 */
public class EventMangerImpl implements EventManager {

	protected final Map<EventListener, ListenerWrapper> lisenners = Collections.synchronizedMap(new HashMap<EventListener, ListenerWrapper>());
	protected final Map<Schema, ListenerGroup> groupListeners = Collections.synchronizedMap(new HashMap<Schema, ListenerGroup>());
	private Queue<Event> mEventQueue = new LinkedBlockingQueue<Event>();

	private static EventManager sInstance = null;

	private EventMangerImpl() {

	}

	public synchronized static EventManager getInstance() {
		if (sInstance == null) {
			sInstance = new EventMangerImpl();
		}
		return sInstance;
	}

	@Override
	public void sendEvent(Event event) {
		if (event == null) {
			throw new NullPointerException("Event is null.");
		}
		mEventQueue.add(event);
	}

	@Override
	public void broadCastEvent(Schema schema,Event event) {
		if (schema == null || event == null) {
			throw new NullPointerException("Arguments can't be null");
		}
		ListenerGroup listenerGroup = groupListeners.get(schema);
		if (listenerGroup != null) {
			listenerGroup.fireEvent(event);
		}
	}

	@Override
	public void addListener(EventFilter filter, EventListener listener) {
		if (listener == null) {
			throw new NullPointerException("Listener is null.");
		}
		ListenerWrapper wrapper = new ListenerWrapper(listener, filter);
		this.lisenners.put(listener, wrapper);
	}

	@Override
	public void removeListener(EventListener listener) {
		this.lisenners.remove(listener);
	}

	@Override
	public void subscribeEvent(Schema schema, EventListener listener) {
		if (schema == null || listener == null) {
			throw new NullPointerException("Arguments can't be null");
		}
		ListenerGroup group = groupListeners.get(schema);
		if (group == null) {
			group = new ListenerGroup();
			group.addListener(listener);
			groupListeners.put(schema, group);
		}else {
			group.addListener(listener);
		}
	}
	@Override
	public void unsubscribeEvent(Schema schema, EventListener listener) {
		if (schema == null || listener == null) {
			throw new NullPointerException("Arguments can't be null");
		}
		ListenerGroup group = groupListeners.get(schema);
		if (group == null) {
			return;
		}else {
			group.removeListener(listener);
		}
	}
	
	@Override
	public void dump() {
		StringBuffer sb = new StringBuffer();
		sb.append("EvenQueueSize:" + mEventQueue.size());
		sb.append("lisenners:" + lisenners.size());
		sb.append("groupListeners:" + groupListeners.size());
		System.out.println(sb);
	}

	protected static class ListenerWrapper {
		private EventListener listener;
		private EventFilter filter;

		public ListenerWrapper(EventListener listener, EventFilter filter) {
			super();
			this.listener = listener;
			this.filter = filter;
		}

		public void notifyListener(Event event) {
			if ((this.filter == null) || (this.filter.accept(event)))
				this.listener.onEven(event);
		}
	}

	protected static class ListenerGroup {
		Set<EventListener> listeners = Collections.synchronizedSet(new HashSet<EventListener>());

		public void addListener(EventListener listener) {
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
		
		public void removeListener(EventListener listener){
			listeners.remove(listener);
		}

		public void clear() {
			listeners.clear();
		}

		public void fireEvent(Event event) {
			for (EventListener l : listeners) {
				l.onEven(event);
			}
		}

	}



}
