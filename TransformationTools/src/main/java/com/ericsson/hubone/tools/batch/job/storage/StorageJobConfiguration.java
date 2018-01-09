package com.ericsson.hubone.tools.batch.job.storage;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.job.superjob.TaskExecutorConfig;
import com.ericsson.hubone.tools.batch.listener.JobListener;

@Configuration
public class StorageJobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("jmsTemplateStorage")
	private JmsTemplate jmsTemplateStorage;


	@Autowired
	@Qualifier("jmsTemplateFunctionalCLI")
	private JmsTemplate jmsTemplateFunctionalCLI; 
	
	@Autowired
	public TaskExecutorConfig taskExecutor;

	@Autowired
	private StorageProcessor<CesameRootBean> cliStorageProcessor;


	public JmsItemReader<CesameRootBean> reader() {
		JmsItemReader<CesameRootBean> reader = new JmsItemReader<CesameRootBean>();

		reader.setJmsTemplate(jmsTemplateStorage);

		return reader;
	}	

	public JmsItemWriter<CesameRootBean> writer() {
		JmsItemWriter<CesameRootBean> writer = new JmsItemWriter<CesameRootBean>();	

		writer.setJmsTemplate(jmsTemplateFunctionalCLI);

		return writer;
	}

	@Bean(name="storageJob")
	public Job storageJob(JobListener listener) {

		return jobBuilderFactory.get("StorageJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(storageStep())
				.end()
				.build();
	}


	public Step storageStep() {

		return stepBuilderFactory.get("StorageStep")
				.<CesameRootBean, CesameRootBean> chunk(1)			
				.reader(reader())
				.processor(cliStorageProcessor)
				.writer(writer())
				.taskExecutor(taskExecutor.taskExecutor())
				.build();
	}


}
