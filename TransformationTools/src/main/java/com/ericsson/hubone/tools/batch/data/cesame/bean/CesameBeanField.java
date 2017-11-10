package com.ericsson.hubone.tools.batch.data.cesame.bean;

import java.io.Serializable;

public class CesameBeanField implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8907915417204570377L;
	
	String value;
	boolean mandatory;
	String[] rules;
	String type;
	
	
	
	public CesameBeanField(String value, boolean mandatory, String[] rules,String type) {
		this.value = value;
		this.mandatory = mandatory;
		this.rules = rules;
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String[] getRules() {
		return rules;
	}
	public void setRules(String[] rules) {
		this.rules = rules;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isDependentMandatory(){
		
		for(String rule:rules){
			if(rule.equals("RG4") || rule.equals("RG6") || rule.equals("RG13") || rule.equals("RG14") || rule.equals("RG18") || rule.equals("RG19"))
				return true;
		}
		
		return false;
	}
	

}
