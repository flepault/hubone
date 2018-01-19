package com.ericsson.hubone.tools.batch.data.ecb;

public class Endpoint extends EcbRootBean{
	
	public static String[] column = new String[] {"userName","password","ancestorAccount","accountStatus","payerAccount",			
			"accountStartDate","accountEndDate","payment_StartDate","payment_EndDate","dayOfMonth",
			"firstDayOfMonth","secondDayOfMonth","internal_UsageCycleType","internal_TimezoneID","internal_Language",
			"internal_Currency"						
			};
	
	public static String header(){
		
		return "UserName|Password_|AncestorAccount|AccountStatus|PayerAccount|AccountStartDate|AccountEndDate"
				+ "|Payment_StartDate|Payment_EndDate|DayOfMonth|FirstDayOfMonth|SecondDayOfMonth"
				+ "|UsageCycleType|TimezoneID|Language|Currency";
		
	}
	
	String accountType;
	
	String userName;
	String password="password";
	String ancestorAccount;
	String accountStatus="Active";
	String payerAccount;	
	String accountStartDate;//DateTime
	String accountEndDate;
	String payment_StartDate;
	String payment_EndDate;
	String dayOfMonth="31";
	String firstDayOfMonth;
	String secondDayOfMonth;
	
	String internal_UsageCycleType="Monthly";
	String internal_TimezoneID="(GMT+01:00) Paris, Madrid, Amsterdam";
	String internal_Language="FR";
	String internal_Currency="EUR";	
	
	Boolean newEP;
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountStartDate() {
		return accountStartDate;
	}
	public void setAccountStartDate(String accountStartDate) {
		this.accountStartDate = accountStartDate;
	}	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccountEndDate() {
		return accountEndDate;
	}
	public void setAccountEndDate(String accountEndDate) {
		this.accountEndDate = accountEndDate;
	}
	public String getPayment_StartDate() {
		return payment_StartDate;
	}
	public void setPayment_StartDate(String payment_StartDate) {
		this.payment_StartDate = payment_StartDate;
	}
	public String getPayment_EndDate() {
		return payment_EndDate;
	}
	public void setPayment_EndDate(String payment_EndDate) {
		this.payment_EndDate = payment_EndDate;
	}
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public String getFirstDayOfMonth() {
		return firstDayOfMonth;
	}
	public void setFirstDayOfMonth(String firstDayOfMonth) {
		this.firstDayOfMonth = firstDayOfMonth;
	}
	public String getSecondDayOfMonth() {
		return secondDayOfMonth;
	}
	public void setSecondDayOfMonth(String secondDayOfMonth) {
		this.secondDayOfMonth = secondDayOfMonth;
	}
	public String getInternal_UsageCycleType() {
		return internal_UsageCycleType;
	}
	public void setInternal_UsageCycleType(String internal_UsageCycleType) {
		this.internal_UsageCycleType = internal_UsageCycleType;
	}
	public String getInternal_TimezoneID() {
		return internal_TimezoneID;
	}
	public void setInternal_TimezoneID(String internal_TimezoneID) {
		this.internal_TimezoneID = internal_TimezoneID;
	}
	public String getInternal_Language() {
		return internal_Language;
	}
	public void setInternal_Language(String internal_Language) {
		this.internal_Language = internal_Language;
	}
	public String getInternal_Currency() {
		return internal_Currency;
	}
	public void setInternal_Currency(String internal_Currency) {
		this.internal_Currency = internal_Currency;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAncestorAccount() {
		return ancestorAccount;
	}
	public void setAncestorAccount(String ancestorAccount) {
		this.ancestorAccount = ancestorAccount;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public Boolean getNewEP() {
		return newEP;
	}
	public void setNewEP(Boolean newEP) {
		this.newEP = newEP;
	}
	

}
