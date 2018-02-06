package com.ericsson.hubone.tools.batch.job.superjob;

public class FilesNames {

	public static String RN1 = "CLI_PIVOT_CLIENT_";
	public static String RN2 = "CLI_PIVOT_REGROUPCF_";
	public static String RN3 = "CLI_PIVOT_CF_";
	public static String RN4 = "COM_PIVOT_ADP_";
	public static String RN5 = "COM_PIVOT_AIRFRANCE_";
	public static String RN6 = "COM_PIVOT_AVIS_";
	public static String RN7 = "COM_PIVOT_RESTE_";
	public static String RN8 = "COM_PIVOT_CMD_M1_";
	public static String RN9 = "COM_PIVOT_CMD_M2_";
	
	public static String END_OF_FILE = ".*[0-9]{8}_[0-9]{6}.csv";
	
	public static String INPUT_FOLDER = "input";
	
	public static String OUTPUT_FOLDER = "output";

	public String CLI_CLIENT;
	public String CLI_REGROUPCF;
	public String CLI_CF;
	public String COM_ADP;
	public String COM_AIRFRANCE;
	public String COM_AVIS;
	public String COM_RESTE;
	public String COM_CMD_M1;
	public String COM_CMD_M2;
	
	public String Client = "ClientAccount.input.csv";
	public String RegroupCF = "RegroupCFAccount.input.csv";
	public String CF = "CFAccount.input.csv";
	public String EP = "EPAccount.input.csv";
	public String EPBME = "EPBME.csv";
	public String SubscriptionOld = "Subscription.Old.input.csv";
	public String SubscriptionNew ="Subscription.New.input.csv";
	public String GroupSouscriptionOld = "GroupSubscription.Old.input.csv";
	public String GroupSouscriptionNew = "GroupSubscription.New.input.csv";
	public String SubscriptionInfoBME = "SubscriptionInfoBME.csv";
	public String FlatRecurringCharge = "t_pt_FlatRecurringCharge.input.csv";
	public String NonRecurringCharge = "t_pt_NonRecurringCharge.input.csv";
	

}
