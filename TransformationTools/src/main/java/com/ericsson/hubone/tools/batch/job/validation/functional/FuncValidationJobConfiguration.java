package com.ericsson.hubone.tools.batch.job.validation.functional;

import javax.sql.DataSource;

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
public class FuncValidationJobConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
		
	@Autowired
	@Qualifier("jmsTemplateFunctional")
	private JmsTemplate jmsTemplateFunctional;
		
	@Autowired
	@Qualifier("jmsTemplateTransform")
	private JmsTemplate jmsTemplateTransform;
	
	@Autowired
	public TaskExecutorConfig taskExecutor;
	
	@Autowired
	private FuncValidationProcessor<CesameRootBean> funcValidationProcessor;

	public JmsItemReader<CesameRootBean> reader() {
		JmsItemReader<CesameRootBean> reader = new JmsItemReader<CesameRootBean>();
		
		reader.setJmsTemplate(jmsTemplateFunctional);
		
		return reader;
	}


	public JmsItemWriter<CesameRootBean> writer() {
		JmsItemWriter<CesameRootBean> writer = new JmsItemWriter<CesameRootBean>();	
		
		writer.setJmsTemplate(jmsTemplateTransform);
		
		return writer;
	}

	@Bean(name="functionalValidationJob")
	public Job functionalValidationJob(JobListener listener) {
				
		return jobBuilderFactory.get("functionalValidationJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(functionalStep())
				.end()
				.build();
	}


	public Step functionalStep() {
		
		return stepBuilderFactory.get("functionalStep")
				.<CesameRootBean, CesameRootBean> chunk(1)
				.reader(reader())
				.processor(funcValidationProcessor)
				.writer(writer())
				.taskExecutor(taskExecutor.taskExecutor())
				.build();
	}

}
