package com.ericsson.hubone.tools.batch.data.ecb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class EndpointBME extends EcbRootBean{
	
	protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String[] column = new String[] {"serviceIdAudit","version","internalKey","creationDate","updateDate",			
			"uID","endDate","startDate","userName","serviceId","parentAccountName","ePAccountId"};
	
	public static String header(){
		
		return "ServiceIdAudit|Version|InternalKey|CreationDate|UpdateDate|"
				+ "UID|EndDate|StartDate|UserName|ServiceId|ParentAccountName|EPAccountId";
		
	}
	
	
		
	public EndpointBME() {
		this.serviceIdAudit = "{"+UUID.randomUUID().toString()+"}";
		this.version ="1";
		this.internalKey="{"+UUID.randomUUID().toString()+"}";
		
		Date now = Calendar.getInstance().getTime();		
		this.creationDate = format.format(now);
		this.updateDate =  format.format(now);
		this.uID=new String("137");
		this.ePAccountId="";
	}

	String serviceIdAudit;
	String version;
	String internalKey;
	String creationDate;
	String updateDate;
	String uID;
	String endDate;
	String parentAccountName;
	String startDate;
	String userName;
	String serviceId;
	String ePAccountId;

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



	public String getePAccountId() {
		return ePAccountId;
	}



	public void setePAccountId(String ePAccountId) {
		this.ePAccountId = ePAccountId;
	}	
	
	
}
