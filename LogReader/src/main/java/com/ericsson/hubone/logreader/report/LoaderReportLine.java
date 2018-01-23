package com.ericsson.hubone.logreader.report;

public class LoaderReportLine {

	String errorType="";
	String file="";
	String primaryKey="";
	String field="";
	String str="";	
	
	public LoaderReportLine(String errorType,String file, String primaryKey, String field, String str) {
		super();
		if(errorType!=null)
			this.errorType = errorType;
		if(file!=null)
			this.file = file;
		if(primaryKey!=null)
			this.primaryKey = primaryKey;
		if(field!=null)
			this.field = field;
		if(str!=null)
			this.str = str;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
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
