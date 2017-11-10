package com.ericsson.hubone.tools.report.tech;

public class DetailledRulesLine {
	
	String field;
	String rule;
	Integer value;	
	
	public DetailledRulesLine(String field, String rule, Integer value) {
		super();
		this.field = field;
		this.rule = rule;
		this.value = value;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
