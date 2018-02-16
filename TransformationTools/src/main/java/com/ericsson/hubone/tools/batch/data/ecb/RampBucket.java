package com.ericsson.hubone.tools.batch.data.ecb;

public class RampBucket extends EcbRootBean{
	
	public static String[] column = new String[] {
			"iCBAccountId","migrationId","poName","piName","rateType","priceListName","accQualGroup","usgQualGroup",
			"itemsToAggregate","tierId","tierName","startOfUnitRange","endOfUnitRange","priority","tariffCodesList",
			"newRate"
			};		


	public static String header(){
		return "ICBAccountId|MigrationId|PoName|PiName|RateType|"
				+ "PriceListName|AccQualGroup|UsgQualGroup|ItemsToAggregate|"
				+ "TierId|TierName|StartOfUnitRange|EndOfUnitRange|Priority|TariffCodesList|NewRate";
		}

	String iCBAccountId;
	String migrationId;
	String poName;
	String piName;
	String rateType;
	String priceListName;
	String accQualGroup;
	String usgQualGroup;
	String itemsToAggregate;
	String tierId;
	String tierName;
	String startOfUnitRange;
	String endOfUnitRange;
	String priority;
	String tariffCodesList;
	String newRate;


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
	public String getAccQualGroup() {
		return accQualGroup;
	}
	public void setAccQualGroup(String accQualGroup) {
		this.accQualGroup = accQualGroup;
	}
	public String getUsgQualGroup() {
		return usgQualGroup;
	}
	public void setUsgQualGroup(String usgQualGroup) {
		this.usgQualGroup = usgQualGroup;
	}
	public String getItemsToAggregate() {
		return itemsToAggregate;
	}
	public void setItemsToAggregate(String itemsToAggregate) {
		this.itemsToAggregate = itemsToAggregate;
	}
	public String getTierId() {
		return tierId;
	}
	public void setTierId(String tierId) {
		this.tierId = tierId;
	}
	public String getTierName() {
		return tierName;
	}
	public void setTierName(String tierName) {
		this.tierName = tierName;
	}
	public String getStartOfUnitRange() {
		return startOfUnitRange;
	}
	public void setStartOfUnitRange(String startOfUnitRange) {
		this.startOfUnitRange = startOfUnitRange;
	}
	public String getEndOfUnitRange() {
		return endOfUnitRange;
	}
	public void setEndOfUnitRange(String endOfUnitRange) {
		this.endOfUnitRange = endOfUnitRange;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getTariffCodesList() {
		return tariffCodesList;
	}
	public void setTariffCodesList(String tariffCodesList) {
		this.tariffCodesList = tariffCodesList;
	}
	public String getNewRate() {
		return newRate;
	}
	public void setNewRate(String newRate) {
		this.newRate = newRate;
	}
	
	
}
