package com.ericsson.hubone.tools.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepListener implements StepExecutionListener {
	
	private static final Logger log = LoggerFactory.getLogger(StepListener.class);
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		if(stepExecution.getStatus() == BatchStatus.COMPLETED) {
			
			log.info(stepExecution.getStepName()+" STEP FINISHED! Time to verify the results");
			
		}
		
		return stepExecution.getExitStatus();
		
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
				
	}

}
