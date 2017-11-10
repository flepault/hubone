package com.ericsson.hubone.tools.report.transformation;

public class GeneralLine {
	
	String type = "";
	Integer nbItems = 0;
	
	public GeneralLine(String type, Integer nbItems) {
		if(type != null)
			this.type = type;
		if(nbItems!= null)
			this.nbItems = nbItems;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getNbItems() {
		return nbItems;
	}
	public void setNbError(Integer nbItems) {
		this.nbItems = nbItems;
	}
	
	

}
