package com.ericsson.hubone.tools.report.func;

public class GeneralLine {
	
	String errorType ="";
	Integer nbError = 0;
	
	public GeneralLine(String errorType, Integer nbError) {
		if(errorType!=null)
			this.errorType = errorType;
		if(nbError!=null)
			this.nbError = nbError;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public Integer getNbError() {
		return nbError;
	}
	public void setNbError(Integer nbError) {
		this.nbError = nbError;
	}
	
	

}
