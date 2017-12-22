package com.ericsson.hubone.tools.batch.job.superjob;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ericsson.hubone.tools.report.tech.TechnicalReport;
import com.ericsson.hubone.tools.report.tech.TechnicalReportLine;

@Configuration
public class SuperJobConfiguration {
		
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("technicalValidationJob")
	private Job technicalValidationJob;
	
		
	@Autowired
	@Qualifier("functionalValidationJob")
	private Job functionalValidationJob;
	
	@Autowired
	@Qualifier("storageJob")
	private Job storageJob;
	
	@Autowired
	@Qualifier("transformationJob")
	private Job transformationJob;
	
		
	@Bean(name="superJob")
	@Profile({"full","fast"})
	public Job fullSuperJob() {
		
		JobBuilder builder = jobBuilderFactory.get("superJob");
		
		return builder.start(launchTechValidationJob()).on(ExitStatus.COMPLETED.getExitCode())
		.to(launchStorageJob()).on(ExitStatus.COMPLETED.getExitCode())
		.to(launchFuncValidationJob()).on(ExitStatus.COMPLETED.getExitCode())
		.to(launchTransformationJob()).end().build();									
	}	
	
	private Step launchTechValidationJob() {
		return stepBuilderFactory.get("launchTechValidationJob")
					.job(technicalValidationJob)
					.build();
	}
		
	
	private Step launchFuncValidationJob() {
		return stepBuilderFactory.get("launchFuncValidationJob")
				.job(functionalValidationJob)
				.build();
	}
	

	private Step launchStorageJob(){
		return stepBuilderFactory.get("launchStorageJob")
				.job(storageJob)
				.build();
	}
	

	private Step launchTransformationJob() {		
		
		return stepBuilderFactory.get("launchTransformationJob")
				.job(transformationJob)
				.build();
	}
	
	@Bean
	public FilesNames controlFilesNames() throws IOException{	
		
		FilesNames filesNames = new FilesNames();

		new File(FilesNames.INPUT_FOLDER).listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {				

				if(name.startsWith(FilesNames.RN1)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.CLI_CLIENT = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-1", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN2)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.CLI_REGROUPCF = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-2", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN3)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.CLI_CF = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-3", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN4)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_ADP = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-4", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN5)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_AIRFRANCE = name;
						return true;
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-5", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN6)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_AVIS = name;
						return true;
					} else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-6", name, "", "", "Pattern error"));

				}else if(name.startsWith(FilesNames.RN7)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_RESTE = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-7", name, "", "", "Pattern error"));	

				}else if(name.startsWith(FilesNames.RN8)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_CMD_M1 = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-8", name, "", "", "Pattern error"));	

				}else if(name.startsWith(FilesNames.RN9)){
					if(name.matches(FilesNames.END_OF_FILE)){
						filesNames.COM_CMD_M2 = name;
						return true;					
					}else
						TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-9", name, "", "", "Pattern error"));	

				}

				return false;

			}
		});


		if(filesNames.CLI_CLIENT==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-1", "", "", "", "CLI_PIVOT_CLIENT_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.CLI_REGROUPCF==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-2", "", "", "", "CLI_PIVOT_REGROUPCF_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.CLI_CF==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-3", "", "", "", "CLI_PIVOT_CF_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_ADP==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-4", "", "", "", "COM_PIVOT_ADP_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_AIRFRANCE==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-5", "", "", "", "COM_PIVOT_AIRFRANCE_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_AVIS==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-6", "", "", "", "COM_PIVOT_AVIS_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_RESTE==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-7", "", "", "", "COM_PIVOT_RESTE_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_CMD_M1==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-8", "", "", "", "COM_PIVOT_CMD_M1_YYYYMMDDhhmm.csv does not exist"));
		if(filesNames.COM_CMD_M2==null)
			TechnicalReport.getIntance().increaseFileError(new TechnicalReportLine("ERR-RN-9", "", "", "", "COM_PIVOT_CMD_M2_YYYYMMDDhhmm.csv does not exist"));

		
		return filesNames;
		
	}
	
}
