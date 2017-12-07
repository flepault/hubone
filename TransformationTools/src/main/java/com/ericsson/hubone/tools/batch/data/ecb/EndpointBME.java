package com.ericsson.hubone.tools.batch.data.ecb;

public class EndpointBME extends EcbRootBean{
	
	public static String[] column = new String[] {"serviceIdAudit","version","internalKey","creationDate","updateDate",			
			"uID","endDate","parentAccountName","startDate","userName",
			"serviceId"};
	
	public static String header(){
		
		return "ServiceIdAudit|Version|InternalKey|CreationDate|"
				+ "UpdateDate|UID|EndDate|ParentAccountName|StartDate|UserName|ServiceId";
		
	}
		
	String serviceIdAudit="SELECT NEWID()";
	String version="1";
	String internalKey="SELECT NEWID()";
	String creationDate="SELECT GETDATE()";
	String updateDate="SELECT GETDATE()";
	String uID="SELECT NEWID()";
	String endDate;
	String parentAccountName;
	String startDate;
	String userName;
	String serviceId;

	public String getServiceIdAudit() {
		return serviceIdAudit;
	}
	public void setServiceIdAudit(String serviceIdAudit) {
		this.serviceIdAudit = serviceIdAudit;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInternalKey() {
		return internalKey;
	}
	public void setInternalKey(String internalKey) {
		this.internalKey = internalKey;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getParentAccountName() {
		return parentAccountName;
	}
	public void setParentAccountName(String parentAccountName) {
		this.parentAccountName = parentAccountName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}	
	
}
