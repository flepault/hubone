package com.ericsson.hubone.tools.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.report.func.FunctionalReport;
import com.ericsson.hubone.tools.report.tech.TechnicalReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;

@Component
public class JobListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobListener.class);
	
	boolean technicalReport=false;
	boolean functionalReport=false;
	boolean transformationReport=false;

	@Autowired
	public JobListener() {
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info(jobExecution.getJobInstance().getJobName()+" JOB FINISHED! Time to verify the results");
			
			if(!technicalReport && jobExecution.getJobInstance().getJobName().equals("technicalValidationJob")){
				TechnicalReport.getIntance().printReport();
				technicalReport=true;
			}
			
			if(!functionalReport && jobExecution.getJobInstance().getJobName().equals("functionalValidationJob")){
				FunctionalReport.getIntance().printReport();
				functionalReport=true;
			}
			
			if(!transformationReport && jobExecution.getJobInstance().getJobName().equals("TransformationJob")){
				TransformationReport.getIntance().printReport();
				transformationReport=true;
			}
					
		
		}
	}
}
