package com.ericsson.hubone.tools.batch.data.ecb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class SubscriptionInfoBME extends EcbRootBean{
	
	protected static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String[] column = new String[] {"subscriptionInfoId","version","internalKey","creationDate","updateDate","uID",
			"quantity","catalogPrice","discountPercent","discountAmount","isSharedTariffGrid",
			"targetTariffGridId","isOnDemand","sharedBucketScope","appliedPrice","siteAddressId",
			"productIntegrationId","productId","parentProductId","media","commandId","attributs","groupId",
			"serviceId","clientCommandRef","action","modifyAppliedDate","resiliateAppliedDate","createAppliedDate","subId","migrationId"};
	
	public static String header(){
		
		return "SubscriptionInfoId|Version|InternalKey|CreationDate|UpdateDate|UID"
				+ "|Quantity|CatalogPrice|DiscountPercent|DiscountAmount|IsSharedTariffGrid|"
				+ "TargetTariffGridId|IsOnDemand|SharedBucketScope|AppliedPrice|SiteAddressId|"
				+ "ProductIntegrationId|ProductId|ParentProductId|Media|CommandId|Attributs|GroupId|"
				+ "ServiceId|ClientCommandRef|Action|ModifyAppliedDate|ResiliateAppliedDate|CreateAppliedDate|SubId|MigrationId";
		
	}
	public SubscriptionInfoBME() {
		this.subscriptionInfoId = "{"+UUID.randomUUID().toString()+"}";
		this.version ="1";
		this.internalKey="{"+UUID.randomUUID().toString()+"}";
		
		Date now = Calendar.getInstance().getTime();		
		this.creationDate = format.format(now);
		this.updateDate =  format.format(now);
		this.uID=new String("137");
	}
	
	String subscriptionInfoId;
	String version;
	String internalKey;
	String creationDate;
	String updateDate;
	String uID;
	
	String quantity;
	String catalogPrice;	
	String discountPercent;
	String discountAmount;
	String isSharedTariffGrid;
	String targetTariffGridId;
	String isOnDemand;
	String sharedBucketScope;
	String appliedPrice;
	String siteAddressId;
	String productIntegrationId;
	String productId;
	String parentProductId;
	String media;
	String commandId;
	String attributs;
	String groupId;
	String serviceId;
	String clientCommandRef;
	String action;
	String modifyAppliedDate;
	String resiliateAppliedDate;
	String createAppliedDate;
	String subId;
	String migrationId;	
	
	public String getSubscriptionInfoId() {
		return subscriptionInfoId;
	}
	public void setSubscriptionInfoId(String subscriptionInfoId) {
		this.subscriptionInfoId = subscriptionInfoId;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAttributs() {
		return attributs;
	}
	public void setAttributs(String attributs) {
		this.attributs = attributs;
	}
	public String getModifyAppliedDate() {
		return modifyAppliedDate;
	}
	public void setModifyAppliedDate(String modifyAppliedDate) {
		this.modifyAppliedDate = modifyAppliedDate;
	}
	public String getResiliateAppliedDate() {
		return resiliateAppliedDate;
	}
	public void setResiliateAppliedDate(String resiliateAppliedDate) {
		this.resiliateAppliedDate = resiliateAppliedDate;
	}
	public String getCreateAppliedDate() {
		return createAppliedDate;
	}
	public void setCreateAppliedDate(String createAppliedDate) {
		this.createAppliedDate = createAppliedDate;
	}
	public String getMigrationId() {
		return migrationId;
	}
	public void setMigrationId(String migrationId) {
		this.migrationId = migrationId;
	}
	public String getAppliedPrice() {
		return appliedPrice;
	}
	public void setAppliedPrice(String appliedPrice) {
		this.appliedPrice = appliedPrice;
	}
	public String getCatalogPrice() {
		return catalogPrice;
	}
	public void setCatalogPrice(String catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public String getClientCommandRef() {
		return clientCommandRef;
	}
	public void setClientCommandRef(String clientCommandRef) {
		this.clientCommandRef = clientCommandRef;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(String discountPercent) {
		this.discountPercent = discountPercent;
	}
	public String getIsOnDemand() {
		return isOnDemand;
	}
	public void setIsOnDemand(String isOnDemand) {
		this.isOnDemand = isOnDemand;
	}
	public String getIsSharedTariffGrid() {
		return isSharedTariffGrid;
	}
	public void setIsSharedTariffGrid(String isSharedTariffGrid) {
		this.isSharedTariffGrid = isSharedTariffGrid;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getParentProductId() {
		return parentProductId;
	}
	public void setParentProductId(String parentProductId) {
		this.parentProductId = parentProductId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductIntegrationId() {
		return productIntegrationId;
	}
	public void setProductIntegrationId(String productIntegrationId) {
		this.productIntegrationId = productIntegrationId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getSharedBucketScope() {
		return sharedBucketScope;
	}
	public void setSharedBucketScope(String sharedBucketScope) {
		this.sharedBucketScope = sharedBucketScope;
	}
	public String getSiteAddressId() {
		return siteAddressId;
	}
	public void setSiteAddressId(String siteAddressId) {
		this.siteAddressId = siteAddressId;
	}
	public String getTargetTariffGridId() {
		return targetTariffGridId;
	}
	public void setTargetTariffGridId(String targetTariffGridId) {
		this.targetTariffGridId = targetTariffGridId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	
}
