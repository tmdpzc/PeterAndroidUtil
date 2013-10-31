package com.peter.asyncui.core;



public interface EventManager {
	

	public void sendEvent(Event event);
	
	public void broadCastEvent(Schema schema,Event event);
	
	public void addListener(EventFilter filter, EventListener listener);
	
	public void removeListener(EventListener listener);
	
	public void subscribeEvent(Schema schema,EventListener listener);
	
	public void unsubscribeEvent(Schema schema,EventListener listener);
	
	void dump();
	
}
