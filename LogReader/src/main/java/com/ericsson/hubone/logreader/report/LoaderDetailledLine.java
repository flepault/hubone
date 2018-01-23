package com.ericsson.hubone.logreader.report;

public class LoaderDetailledLine {
	
	String field;
	String value;
	Integer nb;	
	
	public LoaderDetailledLine(String field, String value, Integer nb) {
		super();
		this.field = field;
		this.value = value;
		this.nb = nb;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getNb() {
		return nb;
	}
	public void setNb(Integer nb) {
		this.nb = nb;
	}
	
}
