package com.ericsson.hubone.tools.batch.job.transformation;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.EndpointBME;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;

@Configuration
public class WriterConfiguration {
	
	@Autowired
	@Qualifier("retry")
	private boolean retry;
	
	private FlatFileItemWriter<EcbRootBean> writer(String ouput,String header, String[] column) {

		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();

		if(retry){		
			writer.setShouldDeleteIfExists(false);		
			writer.setAppendAllowed(true);
		}else{
			writer.setShouldDeleteIfExists(true);
		}

		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write(header);

			}
		});
		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
		delLineAgg.setDelimiter("|");		
		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
		fieldExtractor.setNames(column);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;

	}
	

//	private FlatFileItemWriter<EcbRootBean> writerCOM(String ouput) {
//
//		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
//
//		if(retry){		
//			writer.setShouldDeleteIfExists(false);		
//			writer.setAppendAllowed(true);
//		}else{
//			writer.setShouldDeleteIfExists(true);
//		}
//
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//
//			@Override
//			public void writeHeader(Writer writer) throws IOException {
//				writer.write(SimpleSubscription.header());
//
//			}
//		});
//		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
//		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
//		delLineAgg.setDelimiter("|");		
//		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
//		fieldExtractor.setNames(SimpleSubscription.column);
//		delLineAgg.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(delLineAgg);
//		return writer;
//
//	}
//
//	private FlatFileItemWriter<EcbRootBean> writerGCOM(String ouput) {
//
//		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
//		
//		if(retry){		
//			writer.setShouldDeleteIfExists(false);		
//			writer.setAppendAllowed(true);
//		}else{
//			writer.setShouldDeleteIfExists(true);
//		}
//		
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//
//			@Override
//			public void writeHeader(Writer writer) throws IOException {
//				writer.write(GroupSubscription.header());
//
//			}
//		});
//		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
//		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
//		delLineAgg.setDelimiter("|");		
//		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
//		fieldExtractor.setNames(GroupSubscription.column);
//		delLineAgg.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(delLineAgg);
//		return writer;
//
//	}
//
//	private FlatFileItemWriter<EcbRootBean> writerEP(String ouput) {
//
//		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
//		if(retry){		
//			writer.setShouldDeleteIfExists(false);		
//			writer.setAppendAllowed(true);
//		}else{
//			writer.setShouldDeleteIfExists(true);
//		}
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//
//			@Override
//			public void writeHeader(Writer writer) throws IOException {
//				writer.write(Endpoint.header());
//
//			}
//		});
//		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
//		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
//		delLineAgg.setDelimiter("|");		
//		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
//		fieldExtractor.setNames(Endpoint.column);
//		delLineAgg.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(delLineAgg);
//		return writer;
//
//	}
//	
//	private FlatFileItemWriter<EcbRootBean> writerEPBME(String ouput) {
//
//		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
//		if(retry){		
//			writer.setShouldDeleteIfExists(false);		
//			writer.setAppendAllowed(true);
//		}else{
//			writer.setShouldDeleteIfExists(true);
//		}
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//
//			@Override
//			public void writeHeader(Writer writer) throws IOException {
//				writer.write(EndpointBME.header());
//
//			}
//		});
//		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
//		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
//		delLineAgg.setDelimiter("|");		
//		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
//		fieldExtractor.setNames(EndpointBME.column);
//		delLineAgg.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(delLineAgg);
//		return writer;
//
//	}
//
//	private FlatFileItemWriter<EcbRootBean> writerCLI(String ouput) {
//
//		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
//		if(retry){		
//			writer.setShouldDeleteIfExists(false);		
//			writer.setAppendAllowed(true);
//		}else{
//			writer.setShouldDeleteIfExists(true);
//		}
//		writer.setHeaderCallback(new FlatFileHeaderCallback() {
//
//			@Override
//			public void writeHeader(Writer writer) throws IOException {
//				writer.write(Account.header());
//
//			}
//		});
//		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
//		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
//		delLineAgg.setDelimiter("|");		
//		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
//		fieldExtractor.setNames(Account.column);
//		delLineAgg.setFieldExtractor(fieldExtractor);
//		writer.setLineAggregator(delLineAgg);
//		return writer;
//
//	}

	@Bean(name="writerClient")
	public ItemWriter<EcbRootBean> writerClient() {
		
		FlatFileItemWriter<EcbRootBean> writer = writer("/ClientAccount.input.csv",Account.header(),Account.column);
		writer.open(new ExecutionContext());	

		return writer;

	}

	@Bean(name="writerRegroupCF")
	public ItemWriter<EcbRootBean> writerRegroupCF() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/RegroupCFAccount.input.csv",Account.header(),Account.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerCF")
	public ItemWriter<EcbRootBean> writerCF() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/CFAccount.input.csv",Account.header(),Account.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerEP")
	public ItemWriter<EcbRootBean> writerEP() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/EPAccount.input.csv",Endpoint.header(),Endpoint.column);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	@Bean(name="writerEPBME")
	public ItemWriter<EcbRootBean> writerEPBME() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/EPBME.csv",EndpointBME.header(),EndpointBME.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerOldSubscription")
	public ItemWriter<EcbRootBean> writerOldSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/Subscription.Old.input.csv",SimpleSubscription.header(),SimpleSubscription.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerNewSubscription")
	public ItemWriter<EcbRootBean> writerNewSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/Subscription.New.input.csv",SimpleSubscription.header(),SimpleSubscription.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerOldGroupSubscription")
	public ItemWriter<EcbRootBean> writerOldGroupSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/GroupSubscription.Old.input.csv",GroupSubscription.header(),GroupSubscription.column);
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerNewGroupSubscription")
	public ItemWriter<EcbRootBean> writerNewGroupSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer("/GroupSubscription.New.input.csv",GroupSubscription.header(),GroupSubscription.column);
		writer.open(new ExecutionContext());

		return writer;

	}

}
