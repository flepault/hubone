package com.ericsson.hubone.tools.batch.data.ecb;

public class GroupSubscription extends Subscription{

	public static String[] column = new String[] {"productOfferingId","accountId","startDate","endDate","name","description","commercialProdCode","migrationId"};		


	public static String header(){

		return "ProductOfferingId|AccountId|StartDate|EndDate|Name|Description|CharacteristicValue.CommercialProdCode|CharacteristicValue.MigrationId";}

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
