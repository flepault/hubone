package com.ericsson.hubone.tools.batch.job.superjob;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import com.ericsson.hubone.tools.report.tech.TechnicalReport;
import com.ericsson.hubone.tools.report.tech.TechnicalReportLine;

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

	public static String CLI_CLIENT;
	public static String CLI_REGROUPCF;
	public static String CLI_CF;
	public static String COM_ADP;
	public static String COM_AIRFRANCE;
	public static String COM_AVIS;
	public static String COM_RESTE;
	public static String COM_CMD_M1;
	public static String COM_CMD_M2;

	public static String Client = "ClientAccount.input.csv";
	public static String RegroupCF = "RegroupCFAccount.input.csv";
	public static String CF = "CFAccount.input.csv";
	public static String EP = "EPAccount.input.csv";
	public static String EPBME = "EPBME.csv";
	public static String SubscriptionOld = "Subscription.Old.input.csv";
	public static String SubscriptionNew ="Subscription.New.input.csv";
	public static String GroupSouscriptionOld = "GroupSubscription.Old.input.csv";
	public static String GroupSouscriptionNew = "GroupSubscription.New.input.csv";
	public static String SubscriptionInfoBME = "SubscriptionInfoBME.csv";
	public static String FlatRecurringCharge = "t_pt_FlatRecurringCharge.input.csv";
	public static String NonRecurringCharge = "t_pt_NonRecurringCharge.input.csv";
	public static String RampBucket = "t_pt_RampBucket.input.csv";
	public static String XPCMS = "t_pt_XPCMS.input.csv";

	public static void controlFilesNames() throws IOException{	

		new File(FilesNames.INPUT_FOLDER).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {				

				if(name.startsWith(FilesNames.RN1)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.CLI_CLIENT = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-1", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN2)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.CLI_REGROUPCF = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-2", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN3)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.CLI_CF = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-3", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN4)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_ADP = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-4", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN5)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_AIRFRANCE = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-5", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN6)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_AVIS = name;
						return true;
					} else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-6", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN7)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_RESTE = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-7", name, "", "", "Pattern error"));	

				}else if(name.startsWith(FilesNames.RN8)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_CMD_M1 = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-8", name, "", "", "Pattern error"));	

				}else if(name.startsWith(FilesNames.RN9)){
					if(name.matches(FilesNames.END_OF_FILE)){
						FilesNames.COM_CMD_M2 = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-9", name, "", "", "Pattern error"));	

				}

				return false;

			}
		});


		if(FilesNames.CLI_CLIENT==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-1", "", "", "", "CLI_PIVOT_CLIENT_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.CLI_REGROUPCF==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-2", "", "", "", "CLI_PIVOT_REGROUPCF_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.CLI_CF==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-3", "", "", "", "CLI_PIVOT_CF_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_ADP==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-4", "", "", "", "COM_PIVOT_ADP_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_AIRFRANCE==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-5", "", "", "", "COM_PIVOT_AIRFRANCE_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_AVIS==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-6", "", "", "", "COM_PIVOT_AVIS_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_RESTE==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-7", "", "", "", "COM_PIVOT_RESTE_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_CMD_M1==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-8", "", "", "", "COM_PIVOT_CMD_M1_YYYYMMDDhhmm.csv does not exist"));
		if(FilesNames.COM_CMD_M2==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-9", "", "", "", "COM_PIVOT_CMD_M2_YYYYMMDDhhmm.csv does not exist"));



	}
}
