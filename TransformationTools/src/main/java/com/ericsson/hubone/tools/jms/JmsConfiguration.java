package com.ericsson.hubone.tools.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsConfiguration {

	public static final String TRANSFORM_JOB_QUEUE = "transform.job.queue";
	public static final String STORAGE_JOB_QUEUE = "storage.job.queue";
	public static final String FUNCTIONAL_JOB_QUEUE = "functional.job.queue";
	
	@Bean(name="jmsTemplateTransform")
	public JmsTemplate jmsTemplateTransform() {
		JmsTemplate jmsTemplate = configJmsTemplate();		
		jmsTemplate.setDefaultDestination(new ActiveMQQueue(TRANSFORM_JOB_QUEUE));
		return jmsTemplate;
	}

	@Bean(name="jmsTemplateStorage")
	public JmsTemplate jmsTemplateStorage() {
		JmsTemplate jmsTemplate = configJmsTemplate();
		jmsTemplate.setDefaultDestination(new ActiveMQQueue(STORAGE_JOB_QUEUE));
		return jmsTemplate;
	}

	@Bean(name="jmsTemplateFunctional")
	public JmsTemplate jmsTemplateFunctional() {
		JmsTemplate jmsTemplate = configJmsTemplate();
		jmsTemplate.setDefaultDestination(new ActiveMQQueue(FUNCTIONAL_JOB_QUEUE));
		return jmsTemplate;
	}
	
	private JmsTemplate configJmsTemplate(){
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setReceiveTimeout(500);
		jmsTemplate.setSessionTransacted(true);
		
		return jmsTemplate;
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {


		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
		factory.setTrustAllPackages(true);
//		factory.setOptimizeAcknowledge(true);		
		
		
		//MULTI THREAD
		factory.setExclusiveConsumer(true);
		factory.setMaxThreadPoolSize(4);
		factory.setAlwaysSessionAsync(true);
		factory.setUseAsyncSend(true);
		//MULTI THREAD
		
		//MONO THREAD
//		factory.setMaxThreadPoolSize(1);
//		factory.setAlwaysSessionAsync(false);
		//MONO THREAD
		
		factory.setOptimizedMessageDispatch(true);

		ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
		prefetchPolicy.setQueuePrefetch(1);
		factory.setPrefetchPolicy(prefetchPolicy);
		
		PooledConnectionFactory pool = new PooledConnectionFactory(factory);
		//MONO THREAD
//		pool.setMaxConnections(1);
//		pool.setMaximumActiveSessionPerConnection(1);
		//MONO THREAD
		
		//MULTI THREAD
		pool.setMaxConnections(4);
		pool.setMaximumActiveSessionPerConnection(4);
		//MULTI THREAD
	
		pool.isCreateConnectionOnStartup();

		return pool;
	}

}
