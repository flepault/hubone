package com.ericsson.hubone.tools.batch.data.ecb;

public class GroupSubscription extends Subscription{

	public static String[] column = new String[] {"productOfferingId","accountId","startDate","endDate","name","description","commercialProdCode",
			"catalogPrice","clientCommandRef","commandId","discountAmount","discountPercent","isOnDemand","isSharedTariffGrid",
			"media","parentProductId","productId","productIntegrationId","quantity","serviceId","sharedBucketScope","siteAddressId","targetTariffGridId"
	};		


	public static String header(){

		return "ProductOfferingId|AccountId|StartDate|EndDate|Name|Description|CharacteristicValue.CommercialProdCode|"
				+ "SubscriptionInfo.CatalogPrice|SubscriptionInfo.ClientCommandRef|"
				+ "SubscriptionInfo.CommandId|SubscriptionInfo.DiscountAmount|"
				+ "SubscriptionInfo.DiscountPercent|SubscriptionInfo.IsOnDemand|"
				+ "SubscriptionInfo.IsSharedTariffGrid|SubscriptionInfo.Media|"
				+ "SubscriptionInfo.ParentProductId|SubscriptionInfo.ProductId|"
				+ "SubscriptionInfo.ProductIntegrationId|SubscriptionInfo.Quantity|"
				+ "SubscriptionInfo.ServiceId|SubscriptionInfo.SharedBucketScope|"
				+ "SubscriptionInfo.SiteAddressId|SubscriptionInfo.TargetTariffGridId";

	}

	String name;
	String description;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
