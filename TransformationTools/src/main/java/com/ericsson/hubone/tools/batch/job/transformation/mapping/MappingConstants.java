package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MappingConstants {
	
	
	protected Date migrationHubOneAccountStartDate = new Date(new Long("978303600000"));
	
	protected Date migrationHubOneSubscriptionStartDate = new Date(new Long("1483225200000"));

	protected Date migrationHubOneFarEndDate = new Date(new Long("2145913200000"));
	
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected SimpleDateFormat formatCOM = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

}
