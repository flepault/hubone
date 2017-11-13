package com.ericsson.hubone.tools.batch.job.transformation;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
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
	@Qualifier("writerClient")
    private ItemWriter<EcbRootBean> clientWriter;
	
	@Autowired
	@Qualifier("writerRegroupCF")
    private ItemWriter<EcbRootBean> regroupCFWriter;
	
	@Autowired
	@Qualifier("writerCF")
    private ItemWriter<EcbRootBean> cfWriter;
	
	@Autowired
	@Qualifier("writerEP")
    private ItemWriter<EcbRootBean> epWriter;
	
	@Autowired
	@Qualifier("writerOldSubscription")
    private ItemWriter<EcbRootBean> oldSouscriptionWriter;
	
	@Autowired
	@Qualifier("writerNewSubscription")
    private ItemWriter<EcbRootBean> newSouscriptionWriter;
	
	@Autowired
	@Qualifier("writerOldGroupSubscription")
    private ItemWriter<EcbRootBean> oldGroupSouscriptionWriter;
	
	@Autowired
	@Qualifier("writerNewGroupSubscription")
    private ItemWriter<EcbRootBean> newGroupSouscriptionWriter;
	

	public ItemListWriter writer() {		
		
		return new ItemListWriter(clientWriter,regroupCFWriter,cfWriter,epWriter,oldSouscriptionWriter,newSouscriptionWriter,
				oldGroupSouscriptionWriter,newGroupSouscriptionWriter);
		
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
				.allowStartIfComplete(true)	
				.<CesameRootBean, List<EcbRootBean>> chunk(1)		
				.reader(reader())
				.processor(transformationProcessor)
				.writer(writer())
				.taskExecutor(taskExecutor.taskExecutor())
				.build();
	}



}
