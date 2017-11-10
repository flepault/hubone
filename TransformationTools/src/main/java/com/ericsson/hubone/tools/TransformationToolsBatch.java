package com.ericsson.hubone.tools;

import java.io.IOException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class TransformationToolsBatch {


	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(TransformationToolsBatch.class, args);
		
		 JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
		 Job superJob = null;
		 		 
		 superJob = ctx.getBean("superJob",Job.class);   
		 
		 JobParameters jobParameters = new JobParametersBuilder()
		            .toJobParameters();  
		 
		jobLauncher.run(superJob, jobParameters);
		
		 System.exit(0);
		
	}
	
	
	
}
