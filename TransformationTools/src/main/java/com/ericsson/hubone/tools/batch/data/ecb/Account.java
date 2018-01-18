package com.ericsson.hubone.tools.batch.data.ecb;

public class Account extends EcbRootBean{
	
	public static String[] column = new String[] {"userName","password","ancestorAccount","accountStatus","payerAccount",			
			"accountStartDate","accountEndDate","payment_StartDate","payment_EndDate","dayOfMonth",
			"firstDayOfMonth","secondDayOfMonth","internal_UsageCycleType","internal_TimezoneID","internal_Language",
			"internal_Currency","internal_Billable","internal_TaxExempt","contact_FirstName","contact_LastName","contact_Email","contact_PhoneNumber","contact_Company",
			"contact_Address1","contact_Address2","contact_Address3","contact_City","contact_State","contact_Zip","contact_Country",
			"contact_FacsimileTelephoneNumber","contact_AccountName","contact_BillingAddressID","contact_ShipmentAddressID",
			"contact_BillingContactID","contact_CommercialContactID","billing_BillingGroupName","billing_InvoiceTemplate",
			"billing_InvoiceSupport","billing_IsToCheckInvoice","billing_HasJustifDetCom","billing_PaymentDelay",
			"billing_PaymentMode","billing_IbanCodeIso","billing_IbanCodeCtl","billing_IbanCodeCompte","billing_Bic",
			"common_BillComment","common_RowIdSiebel","common_CrmStatus","common_CustomerRole","common_GroupName","common_TvaIntra",
			"common_CodeApe","common_Organization","common_Siret","common_CodeService","common_CodeCC","common_ClientRootId","common_GroupId",
			"common_ClientParentName","common_CodeMarche"
						
			};
	
	public static String header(){
		
		return "UserName|Password_|AncestorAccount|AccountStatus|PayerAccount|AccountStartDate|AccountEndDate"
				+ "|Payment_StartDate|Payment_EndDate|DayOfMonth|FirstDayOfMonth|SecondDayOfMonth"
				+ "|UsageCycleType|TimezoneID|Language|Currency|Billable|TaxExempt|FirstName|LastName|Email|PhoneNumber|Company"
				+ "|Address1|Address2|Address3|City|State|Zip|Country|FacsimileTelephoneNumber|AccountName"
				+ "|BillingAddressID|ShipmentAddressID|BillingContactID|CommercialContactID|BillingGroupName"
				+ "|InvoiceTemplate|InvoiceSupport|IsToCheckInvoice|HasJustifDetCom|PaymentDelay|PaymentMode"
				+ "|IbanCodeIso|IbanCodeCtl|IbanCodeCompte|Bic|BillComment|RowIdSiebel|CrmStatus|CustomerRole|GroupName"
				+ "|TvaIntra|CodeApe|Organization|Siret|CodeService|CodeCC|ClientRootId|GroupId|ClientParentName|CodeMarche";
		
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
	String internal_Billable;
	String internal_TaxExempt;
	
	String billing_InvoiceTemplate;
	String billing_InvoiceSupport;
	String billing_BillingGroupName;
	String billing_IsToCheckInvoice;
	String billing_HasJustifDetCom;
	String billing_PaymentDelay;
	String billing_PaymentMode;
	String billing_IbanCodeIso;
	String billing_IbanCodeCtl;
	String billing_IbanCodeCompte;
	String billing_Bic;
	
	String common_BillComment;
	
	
	String contact_FirstName;
	String contact_LastName;
	String contact_PhoneNumber;
	String contact_Company;
	String contact_Address1;
	String contact_Address2;
	String contact_Address3;
	String contact_City;
	String contact_State;
	String contact_Zip;
	String contact_Country;
	String contact_FacsimileTelephoneNumber;	
	String contact_Email;
	String contact_AccountName;
	String contact_BillingAddressID;
	String contact_ShipmentAddressID;
	String contact_BillingContactID;
	String contact_CommercialContactID;
	
	String common_RowIdSiebel;
	String common_CrmStatus;
	String common_CustomerRole;
	String common_GroupName;
	String common_TvaIntra;
	String common_CodeApe;
	String common_Organization;
	String common_Siret;
	String common_CodeService;
	String common_CodeCC;
	String common_ClientRootId;
	String common_GroupId;
	String common_ClientParentName;
	String common_CodeMarche;
	
	
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
	public String getInternal_Billable() {
		return internal_Billable;
	}
	public void setInternal_Billable(String internal_Billable) {
		this.internal_Billable = internal_Billable;
	}
	public String getInternal_TaxExempt() {
		return internal_TaxExempt;
	}
	public void setInternal_TaxExempt(String internal_TaxExempt) {
		this.internal_TaxExempt = internal_TaxExempt;
	}
	public String getBilling_InvoiceTemplate() {
		return billing_InvoiceTemplate;
	}
	public void setBilling_InvoiceTemplate(String billing_InvoiceTemplate) {
		this.billing_InvoiceTemplate = billing_InvoiceTemplate;
	}
	public String getBilling_InvoiceSupport() {
		return billing_InvoiceSupport;
	}
	public void setBilling_InvoiceSupport(String billing_InvoiceSupport) {
		this.billing_InvoiceSupport = billing_InvoiceSupport;
	}
	public String getBilling_BillingGroupName() {
		return billing_BillingGroupName;
	}
	public void setBilling_BillingGroupName(String billing_BillingGroupName) {
		this.billing_BillingGroupName = billing_BillingGroupName;
	}
	public String getBilling_IsToCheckInvoice() {
		return billing_IsToCheckInvoice;
	}
	public void setBilling_IsToCheckInvoice(String billing_IsToCheckInvoice) {
		this.billing_IsToCheckInvoice = billing_IsToCheckInvoice;
	}
	public String getBilling_HasJustifDetCom() {
		return billing_HasJustifDetCom;
	}
	public void setBilling_HasJustifDetCom(String billing_HasJustifDetCom) {
		this.billing_HasJustifDetCom = billing_HasJustifDetCom;
	}
	public String getBilling_PaymentDelay() {
		return billing_PaymentDelay;
	}
	public void setBilling_PaymentDelay(String billing_PaymentDelay) {
		this.billing_PaymentDelay = billing_PaymentDelay;
	}
	public String getBilling_PaymentMode() {
		return billing_PaymentMode;
	}
	public void setBilling_PaymentMode(String billing_PaymentMode) {
		this.billing_PaymentMode = billing_PaymentMode;
	}
	public String getBilling_IbanCodeIso() {
		return billing_IbanCodeIso;
	}
	public void setBilling_IbanCodeIso(String billing_IbanCodeIso) {
		this.billing_IbanCodeIso = billing_IbanCodeIso;
	}
	public String getBilling_IbanCodeCtl() {
		return billing_IbanCodeCtl;
	}
	public void setBilling_IbanCodeCtl(String billing_IbanCodeCtl) {
		this.billing_IbanCodeCtl = billing_IbanCodeCtl;
	}
	public String getBilling_IbanCodeCompte() {
		return billing_IbanCodeCompte;
	}
	public void setBilling_IbanCodeCompte(String billing_IbanCodeCompte) {
		this.billing_IbanCodeCompte = billing_IbanCodeCompte;
	}
	public String getBilling_Bic() {
		return billing_Bic;
	}
	public void setBilling_Bic(String billing_Bic) {
		this.billing_Bic = billing_Bic;
	}
	public String getCommon_BillComment() {
		return common_BillComment;
	}
	public void setCommon_BillComment(String common_BillComment) {
		this.common_BillComment = common_BillComment;
	}
	public String getContact_Email() {
		return contact_Email;
	}
	public void setContact_Email(String contact_Email) {
		this.contact_Email = contact_Email;
	}
	public String getContact_AccountName() {
		return contact_AccountName;
	}
	public void setContact_AccountName(String contact_AccountName) {
		this.contact_AccountName = contact_AccountName;
	}
	public String getContact_BillingAddressID() {
		return contact_BillingAddressID;
	}
	public void setContact_BillingAddressID(String contact_BillingAddressID) {
		this.contact_BillingAddressID = contact_BillingAddressID;
	}
	public String getContact_ShipmentAddressID() {
		return contact_ShipmentAddressID;
	}
	public void setContact_ShipmentAddressID(String contact_ShipmentAddressID) {
		this.contact_ShipmentAddressID = contact_ShipmentAddressID;
	}
	public String getContact_BillingContactID() {
		return contact_BillingContactID;
	}
	public void setContact_BillingContactID(String contact_BillingContactID) {
		this.contact_BillingContactID = contact_BillingContactID;
	}
	public String getContact_CommercialContactID() {
		return contact_CommercialContactID;
	}
	public void setContact_CommercialContactID(String contact_CommercialContactID) {
		this.contact_CommercialContactID = contact_CommercialContactID;
	}
	public String getCommon_RowIdSiebel() {
		return common_RowIdSiebel;
	}
	public void setCommon_RowIdSiebel(String common_RowIdSiebel) {
		this.common_RowIdSiebel = common_RowIdSiebel;
	}
	public String getCommon_CrmStatus() {
		return common_CrmStatus;
	}
	public void setCommon_CrmStatus(String common_CrmStatus) {
		this.common_CrmStatus = common_CrmStatus;
	}
	public String getCommon_CustomerRole() {
		return common_CustomerRole;
	}
	public void setCommon_CustomerRole(String common_CustomerRole) {
		this.common_CustomerRole = common_CustomerRole;
	}
	public String getCommon_GroupName() {
		return common_GroupName;
	}
	public void setCommon_GroupName(String common_GroupName) {
		this.common_GroupName = common_GroupName;
	}
	public String getCommon_TvaIntra() {
		return common_TvaIntra;
	}
	public void setCommon_TvaIntra(String common_TvaIntra) {
		this.common_TvaIntra = common_TvaIntra;
	}
	public String getCommon_CodeApe() {
		return common_CodeApe;
	}
	public void setCommon_CodeApe(String common_CodeApe) {
		this.common_CodeApe = common_CodeApe;
	}
	public String getCommon_Organization() {
		return common_Organization;
	}
	public void setCommon_Organization(String common_Organization) {
		this.common_Organization = common_Organization;
	}
	public String getCommon_Siret() {
		return common_Siret;
	}
	public void setCommon_Siret(String common_Siret) {
		this.common_Siret = common_Siret;
	}
	public String getCommon_CodeService() {
		return common_CodeService;
	}
	public void setCommon_CodeService(String common_CodeService) {
		this.common_CodeService = common_CodeService;
	}
	public String getCommon_CodeCC() {
		return common_CodeCC;
	}
	public void setCommon_CodeCC(String common_CodeCC) {
		this.common_CodeCC = common_CodeCC;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getContact_FirstName() {
		return contact_FirstName;
	}
	public void setContact_FirstName(String contact_FirstName) {
		this.contact_FirstName = contact_FirstName;
	}
	public String getContact_LastName() {
		return contact_LastName;
	}
	public void setContact_LastName(String contact_LastName) {
		this.contact_LastName = contact_LastName;
	}
	public String getContact_PhoneNumber() {
		return contact_PhoneNumber;
	}
	public void setContact_PhoneNumber(String contact_PhoneNumber) {
		this.contact_PhoneNumber = contact_PhoneNumber;
	}
	public String getContact_Company() {
		return contact_Company;
	}
	public void setContact_Company(String contact_Company) {
		this.contact_Company = contact_Company;
	}
	public String getContact_Address1() {
		return contact_Address1;
	}
	public void setContact_Address1(String contact_Address1) {
		this.contact_Address1 = contact_Address1;
	}
	public String getContact_Address2() {
		return contact_Address2;
	}
	public void setContact_Address2(String contact_Address2) {
		this.contact_Address2 = contact_Address2;
	}
	public String getContact_Address3() {
		return contact_Address3;
	}
	public void setContact_Address3(String contact_Address3) {
		this.contact_Address3 = contact_Address3;
	}
	public String getContact_City() {
		return contact_City;
	}
	public void setContact_City(String contact_City) {
		this.contact_City = contact_City;
	}
	public String getContact_State() {
		return contact_State;
	}
	public void setContact_State(String contact_State) {
		this.contact_State = contact_State;
	}
	public String getContact_Zip() {
		return contact_Zip;
	}
	public void setContact_Zip(String contact_Zip) {
		this.contact_Zip = contact_Zip;
	}
	public String getContact_Country() {
		return contact_Country;
	}
	public void setContact_Country(String contact_Country) {
		this.contact_Country = contact_Country;
	}
	public String getContact_FacsimileTelephoneNumber() {
		return contact_FacsimileTelephoneNumber;
	}
	public void setContact_FacsimileTelephoneNumber(String contact_FacsimileTelephoneNumber) {
		this.contact_FacsimileTelephoneNumber = contact_FacsimileTelephoneNumber;
	}
	public String getCommon_ClientRootId() {
		return common_ClientRootId;
	}
	public void setCommon_ClientRootId(String common_ClientRootId) {
		this.common_ClientRootId = common_ClientRootId;
	}
	public String getCommon_GroupId() {
		return common_GroupId;
	}
	public void setCommon_GroupId(String common_GroupId) {
		this.common_GroupId = common_GroupId;
	}
	public String getCommon_ClientParentName() {
		return common_ClientParentName;
	}
	public void setCommon_ClientParentName(String common_ClientParentName) {
		this.common_ClientParentName = common_ClientParentName;
	}
	public String getCommon_CodeMarche() {
		return common_CodeMarche;
	}
	public void setCommon_CodeMarche(String common_CodeMarche) {
		this.common_CodeMarche = common_CodeMarche;
	}
	
	

}
