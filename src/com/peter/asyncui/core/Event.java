package com.peter.asyncui.core;


/**
 * Task Event to inform Interface or other components to take action
 * 
 * @author peizicheng@conew.com
 * @date 2013-10-24
 */
public class Event {

	private int _id;
	private Schema schema;
	private String desc = "";
	public Object obj1;
	public Object obj2;

	
	
	
	public Event() {
	}


	public Event(Schema schema, String desc, Object obj1) {
		super();
		this.schema = schema;
		this.desc = desc;
		this.obj1 = obj1;
	}
	

	public Event(Schema schema, String desc, Object obj1, Object obj2) {
		super();
		this.schema = schema;
		this.desc = desc;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}



	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
