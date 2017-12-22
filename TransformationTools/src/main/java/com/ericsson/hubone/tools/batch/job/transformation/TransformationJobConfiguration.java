package com.ericsson.hubone.tools.batch.job.transformation;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.jms.JmsItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.job.superjob.TaskExecutorConfig;
import com.ericsson.hubone.tools.batch.listener.JobListener;

@Configuration
public class TransformationJobConfiguration{

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("jmsTemplateTransform")
	private JmsTemplate jmsTemplateTransform;
	
	@Autowired
	public TaskExecutorConfig taskExecutor;

	@Autowired
	private TransformationProcessor<CesameRootBean,EcbRootBean> transformationProcessor;

	public JmsItemReader<CesameRootBean> reader() {
		JmsItemReader<CesameRootBean> reader = new JmsItemReader<CesameRootBean>();

		reader.setJmsTemplate(jmsTemplateTransform);
		
		return reader;
	}
	
	@Autowired
	private WriterConfiguration writerConfig;
	

	public ItemListWriter writer() {		
		
		return new ItemListWriter(writerConfig.writerClient(),writerConfig.writerRegroupCF(),writerConfig.writerCF(),writerConfig.writerEP(),writerConfig.writerEPBME()
				,writerConfig.writerOldSubscription(),writerConfig.writerNewSubscription(),
				writerConfig.writerOldGroupSubscription(),writerConfig.writerNewGroupSubscription());
		
	}

	

	@Bean(name="transformationJob")
	public Job transformationJob(JobListener listener) {

		return jobBuilderFactory.get("TransformationJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(transformationJobStep())
				.end()
				.build();
	}

	public Step transformationJobStep() {

		return stepBuilderFactory.get("TransformationJobStep")
				//.allowStartIfComplete(true)	
				.<CesameRootBean, List<EcbRootBean>> chunk(1)		
				.reader(reader())
				.processor(transformationProcessor)
				.writer(writer())
				.taskExecutor(taskExecutor.taskExecutor())
				.build();
	}



}
