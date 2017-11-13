package com.ericsson.hubone.tools.batch.data.ecb;

public class SimpleSubscription extends Subscription{
	
	public static String[] column = new String[] {"productOfferingId","accountId","startDate","endDate","commercialProdCode",
			"catalogPrice","clientCommandRef","commandId","discountAmount","discountPercent","isOnDemand","isSharedTariffGrid",
			"media","parentProductId","productId","productIntegrationId","quantity","serviceId","sharedBucketScope","siteAddressId","targetTariffGridId"
			};
	
	public static String header(){
		
		return "ProductOfferingId|AccountId|StartDate|EndDate|CharacteristicValue.CommercialProdCode|"
				+ "SubscriptionInfo.CatalogPrice|SubscriptionInfo.ClientCommandRef|"
				+ "SubscriptionInfo.CommandId|SubscriptionInfo.DiscountAmount|"
				+ "SubscriptionInfo.DiscountPercent|SubscriptionInfo.IsOnDemand|"
				+ "SubscriptionInfo.IsSharedTariffGrid|SubscriptionInfo.Media|"
				+ "SubscriptionInfo.ParentProductId|SubscriptionInfo.ProductId|"
				+ "SubscriptionInfo.ProductIntegrationId|SubscriptionInfo.Quantity|"
				+ "SubscriptionInfo.ServiceId|SubscriptionInfo.SharedBucketScope|"
				+ "SubscriptionInfo.SiteAddressId|SubscriptionInfo.TargetTariffGridId";
		
	}

}
