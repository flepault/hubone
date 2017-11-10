package com.ericsson.hubone.tools.batch.job.superjob;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TaskExecutorConfig {

	public TaskExecutor taskExecutor() {

//		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//		taskExecutor.setCorePoolSize(2);
//		taskExecutor.setMaxPoolSize(2);

		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(4);
		
		return taskExecutor;
	}

}
