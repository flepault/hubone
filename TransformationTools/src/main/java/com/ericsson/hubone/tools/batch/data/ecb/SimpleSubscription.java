package com.ericsson.hubone.tools.batch.data.ecb;

public class SimpleSubscription extends Subscription{
	
	public static String[] column = new String[] {"productOfferingId","accountId","startDate","endDate","commercialProdCode","migrationId"};
	
	public static String header(){
		
		return "ProductOfferingId|AccountId|StartDate|EndDate|CharacteristicValue.CommercialProdCode|CharacteristicValue.MigrationId";	
	}

}
