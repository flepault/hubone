package com.ericsson.hubone.tools.batch.data.ecb;

public class Subscription extends EcbRootBean{

	String productOfferingId;
	String accountId;
	String startDate;
	String endDate;

	String commercialProdCode;
	String migrationId;	

	Boolean newCOM;

	public String getProductOfferingId() {
		return productOfferingId;
	}
	public void setProductOfferingId(String productOfferingId) {
		this.productOfferingId = productOfferingId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getNewCOM() {
		return newCOM;
	}
	public void setNewCOM(Boolean newCOM) {
		this.newCOM = newCOM;
	}	
	public String getCommercialProdCode() {
		return commercialProdCode;
	}
	public void setCommercialProdCode(String commercialProdCode) {
		this.commercialProdCode = commercialProdCode;
	}
	public String getMigrationId() {
		return migrationId;
	}
	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}
}
