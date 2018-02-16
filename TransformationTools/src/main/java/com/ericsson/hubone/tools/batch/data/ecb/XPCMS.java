package com.ericsson.hubone.tools.batch.data.ecb;

public class XPCMS extends EcbRootBean{
	
	public static String[] column = new String[] {
			"iCBAccountId","migrationId","poName","piName","rateType","priceListName","originZoneId","destZoneId",
			"tariffCode","unitPrice","timeCredit","undividedPeriod","connectionPrice","codeGL","analysisCode"
			};		


	public static String header(){
		return "ICBAccountId|MigrationId|PoName|PiName|RateType|"
				+ "PriceListName|OriginZoneId|DestZoneId|TariffCode|UnitPrice|"
				+ "TimeCredit|UndividedPeriod|ConnectionPrice|CodeGL|AnalysisCode";
		}

	
	String iCBAccountId;
	String migrationId;
	String poName;
	String piName;
	String rateType;
	String priceListName;
	String originZoneId;
	String destZoneId;
	String tariffCode;
	String unitPrice;
	String timeCredit;
	String undividedPeriod;
	String connectionPrice;
	String codeGL;
	String analysisCode;


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
	public String getOriginZoneId() {
		return originZoneId;
	}
	public void setOriginZoneId(String originZoneId) {
		this.originZoneId = originZoneId;
	}
	public String getDestZoneId() {
		return destZoneId;
	}
	public void setDestZoneId(String destZoneId) {
		this.destZoneId = destZoneId;
	}
	public String getTariffCode() {
		return tariffCode;
	}
	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getTimeCredit() {
		return timeCredit;
	}
	public void setTimeCredit(String timeCredit) {
		this.timeCredit = timeCredit;
	}
	public String getUndividedPeriod() {
		return undividedPeriod;
	}
	public void setUndividedPeriod(String undividedPeriod) {
		this.undividedPeriod = undividedPeriod;
	}
	public String getConnectionPrice() {
		return connectionPrice;
	}
	public void setConnectionPrice(String connectionPrice) {
		this.connectionPrice = connectionPrice;
	}
	public String getCodeGL() {
		return codeGL;
	}
	public void setCodeGL(String codeGL) {
		this.codeGL = codeGL;
	}
	public String getAnalysisCode() {
		return analysisCode;
	}
	public void setAnalysisCode(String analysisCode) {
		this.analysisCode = analysisCode;
	}
	
	
}
