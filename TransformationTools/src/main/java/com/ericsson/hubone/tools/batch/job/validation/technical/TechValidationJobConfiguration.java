package com.ericsson.hubone.tools.batch.job.validation.technical;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.jms.JmsItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jms.core.JmsTemplate;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;
import com.ericsson.hubone.tools.batch.job.superjob.TaskExecutorConfig;
import com.ericsson.hubone.tools.batch.listener.JobListener;
import com.ericsson.hubone.tools.batch.listener.StepListener;

@Configuration
public class TechValidationJobConfiguration{

	@Autowired
	public FilesNames filesNames;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public TaskExecutorConfig taskExecutor;


	@Autowired
	@Qualifier("jmsTemplateStorage")
	private JmsTemplate jmsTemplateStorage;

	public JmsItemWriter<CesameRootBean> writerCLI(){

		JmsItemWriter<CesameRootBean> writer = new JmsItemWriter<CesameRootBean>();
		writer.setJmsTemplate(jmsTemplateStorage);		
		return writer;
	}

	@Autowired
	@Qualifier("jmsTemplateFunctional")
	private JmsTemplate jmsTemplateFunctional; 

	public JmsItemWriter<CesameRootBean> writerCOM(){

		JmsItemWriter<CesameRootBean> writer = new JmsItemWriter<CesameRootBean>();
		writer.setJmsTemplate(jmsTemplateFunctional);		
		return writer;
	}

	public FlatFileItemReader<Cli> cliReader(String inputFile) {

		File file = new File(FilesNames.INPUT_FOLDER+"/"+inputFile);

		FlatFileItemReader<Cli> reader = new FlatFileItemReader<Cli>();
		reader.setStrict(false);
		reader.setEncoding("Cp1252");
		reader.setResource(new FileSystemResource(file));		
		reader.setLineMapper(new DefaultLineMapper<Cli>() {{
			setLineTokenizer(new DelimitedLineTokenizer("||") {{
				setNames(Cli.column);
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Cli>() {{
				setTargetType(Cli.class);
			}});
		}});
		return reader;
	}

	public FlatFileItemReader<Com> comReader(String inputFile) {

		File file = new File(FilesNames.INPUT_FOLDER+"/"+inputFile);
		FlatFileItemReader<Com> reader = new FlatFileItemReader<Com>();
		reader.setStrict(false);
		reader.setResource(new FileSystemResource(file));	
		reader.setEncoding("Cp1252");
		reader.setLineMapper(new DefaultLineMapper<Com>() {{
			setLineTokenizer(new DelimitedLineTokenizer("||") {{
				setQuoteCharacter('{');
				setNames(Com.column);
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Com>() {{
				setTargetType(Com.class);
			}});
		}});
		return reader;
	}


	public TechValidationProcessor<Cli> cliTechValidationProcessor(String inputFile,Boolean complete) {
		if(complete)
			return new TechValidationProcessor<Cli>(inputFile);
		else
			return new TechValidationProcessor<Cli>();
	}

	public TechValidationProcessor<Com> comTechValidationProcessor(String inputFile,Boolean complete) {
		if(complete)
			return new TechValidationProcessor<Com>(inputFile);
		else
			return new TechValidationProcessor<Com>();
	}

	@Bean(name="technicalValidationJob")
	@Profile("full")
	public Job technicalValidationJob(JobListener jobListener,StepListener stepListener) {

		return technicalValidationJob(jobListener,true,stepListener);								
	}

	@Bean(name="technicalValidationJob")
	@Profile("fast")
	public Job noTechnicalValidationJob(JobListener jobListener,StepListener stepListener) {

		return technicalValidationJob(jobListener,false,stepListener);								
	}


	public Job technicalValidationJob(JobListener jobListener,Boolean complete,StepListener stepListener) {

		JobBuilder builder = jobBuilderFactory.get("technicalValidationJob");
		builder.incrementer(new RunIdIncrementer());
		builder.listener(jobListener);

		return builder.flow(technicalValidationStepCLI("technicalValidationClient",filesNames.CLI_CLIENT,complete,stepListener))
				.next(technicalValidationStepCLI("technicalValidationRegroupCF",filesNames.CLI_REGROUPCF,complete,stepListener))
				.next(technicalValidationStepCLI("technicalValidationCF",filesNames.CLI_CF,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationADP",filesNames.COM_ADP,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationAIRFRANCE",filesNames.COM_AIRFRANCE,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationAVIS",filesNames.COM_AVIS,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationRESTE",filesNames.COM_RESTE,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationCMD_M1",filesNames.COM_CMD_M1,complete,stepListener))
				.next(technicalValidationStepCOM("technicalValidationCMD_M2",filesNames.COM_CMD_M2,complete,stepListener))
				.end().build();								
	}

	public Step technicalValidationStepCLI(String name,String inputFile,Boolean complete,StepListener stepListener) {


		return stepBuilderFactory.get(name)
				.listener(stepListener)
				.<Cli, Cli> chunk(1)
				.reader(cliReader(inputFile))
				.processor(cliTechValidationProcessor(inputFile,complete))
				.writer(writerCLI())
				.taskExecutor((new TaskExecutorConfig()).taskExecutor())
				.build();		
	}	

	public Step technicalValidationStepCOM(String name,String inputFile,Boolean complete,StepListener stepListener) {

		return stepBuilderFactory.get(name)
				.listener(stepListener)
				.<Com, Com> chunk(1)
				.reader(comReader(inputFile))
				.processor(comTechValidationProcessor(inputFile,complete))
				.writer(writerCOM())
				.taskExecutor(taskExecutor.taskExecutor())
				.build();		
	}	

	@Bean(name="technicalValidationJob")
	@Profile({"functional","transform"})
	public Job technicalCLIValidationJob(JobListener jobListener,StepListener stepListener) {

		return technicalCLIValidationJob(jobListener,false,stepListener);								
	}	

	public Job technicalCLIValidationJob(JobListener jobListener,Boolean complete,StepListener stepListener) {

		JobBuilder builder = jobBuilderFactory.get("technicalValidationJob");
		builder.incrementer(new RunIdIncrementer());
		builder.listener(jobListener);

		return builder.flow(technicalValidationStepCLI("technicalValidationClient",filesNames.CLI_CLIENT,complete,stepListener))
				.next(technicalValidationStepCLI("technicalValidationRegroupCF",filesNames.CLI_REGROUPCF,complete,stepListener))
				.next(technicalValidationStepCLI("technicalValidationCF",filesNames.CLI_CF,complete,stepListener))
				.end().build();								
	}

}
