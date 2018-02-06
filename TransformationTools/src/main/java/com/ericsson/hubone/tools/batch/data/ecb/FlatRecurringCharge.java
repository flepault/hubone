package com.ericsson.hubone.tools.batch.data.ecb;

public class FlatRecurringCharge extends EcbRootBean{
	
	
	public static String[] column = new String[] {
			"iCBAccountId","migrationId","poName","piName","rateType","priceListName","rCAmount"
			};		


	public static String header(){
		return "ICBAccountId|MigrationId|PoName|PiName|RateType|PriceListName|RCAmount";
		}

	String iCBAccountId;
	String migrationId;
	String poName;
	String piName;
	String rateType;
	String priceListName;
	String rCAmount;


	public String getiCBAccountId() {
		return iCBAccountId;
	}
	public void setiCBAccountId(String iCBAccountId) {
		this.iCBAccountId = iCBAccountId;
	}
	public String getMigrationId() {
		return migrationId;
	}
	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}
	public String getPoName() {
		return poName;
	}
	public void setPoName(String poName) {
		this.poName = poName;
	}
	public String getPiName() {
		return piName;
	}
	public void setPiName(String piName) {
		this.piName = piName;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getPriceListName() {
		return priceListName;
	}
	public void setPriceListName(String priceListName) {
		this.priceListName = priceListName;
	}
	public String getrCAmount() {
		return rCAmount;
	}
	public void setrCAmount(String rCAmount) {
		this.rCAmount = rCAmount;
	}
	
}
