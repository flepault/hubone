package com.ericsson.hubone.tools.batch.job.superjob;

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
	@Profile({"full"})
	public Job fullSuperJob() throws IOException {
		
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
	
	
}
