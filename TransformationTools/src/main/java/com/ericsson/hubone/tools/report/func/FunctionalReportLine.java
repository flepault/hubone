package com.ericsson.hubone.tools.report.func;

public class FunctionalReportLine {

	String errorType;
	String primaryKey;
	String str;	
	
	public FunctionalReportLine(String errorType,String primaryKey, String str) {
		super();
		this.errorType = errorType;
		this.primaryKey = primaryKey;
		this.str = str;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	

}
