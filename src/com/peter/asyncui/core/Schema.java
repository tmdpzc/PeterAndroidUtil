package com.peter.asyncui.core;

public final class Schema {
	
	public static final Schema UI_SCHEMA = new Schema("ui");
	public static final Schema DOWNLAOD_SCHEMA = new Schema("download");

	private String uri;
	
	
	public Schema(String uri) {
		super();
		this.uri = uri;
	}


	public String toString() {
		return uri;
	};
}
