package com.ericsson.hubone.tools.batch.job.transformation;

import java.io.IOException;
import java.io.Writer;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;

@Configuration
public class WriterConfiguration {
	
	private FlatFileItemWriter<EcbRootBean> writerCOM(String ouput) {

		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
		writer.setShouldDeleteIfExists(true);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write(Subscription.header());

			}
		});
		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
		delLineAgg.setDelimiter("|");		
		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
		fieldExtractor.setNames(Subscription.column);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;

	}
	
	private FlatFileItemWriter<EcbRootBean> writerEP(String ouput) {

		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
		writer.setShouldDeleteIfExists(true);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write(Endpoint.header());

			}
		});
		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
		delLineAgg.setDelimiter("|");		
		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
		fieldExtractor.setNames(Endpoint.column);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;

	}
	
	private FlatFileItemWriter<EcbRootBean> writerCLI(String ouput) {

		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();
		writer.setShouldDeleteIfExists(true);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write(Account.header());

			}
		});
		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+ouput));
		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
		delLineAgg.setDelimiter("|");		
		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
		fieldExtractor.setNames(Account.column);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		return writer;

	}

	@Bean(name="writerClient")
	public ItemWriter<EcbRootBean> writerClient() {

		FlatFileItemWriter<EcbRootBean> writer = writerCLI("/ClientAccount.input.csv");
		writer.open(new ExecutionContext());	

		return writer;

	}

	@Bean(name="writerRegroupCF")
	public ItemWriter<EcbRootBean> writerRegroupCF() {

		FlatFileItemWriter<EcbRootBean> writer = writerCLI("/RegroupCFAccount.input.csv");
		writer.open(new ExecutionContext());

		return writer;

	}

	@Bean(name="writerCF")
	public ItemWriter<EcbRootBean> writerCF() {

		FlatFileItemWriter<EcbRootBean> writer = writerCLI("/CFAccount.input.csv");
		writer.open(new ExecutionContext());

		return writer;

	}
	
	@Bean(name="writerEP")
	public ItemWriter<EcbRootBean> writerEP() {

		FlatFileItemWriter<EcbRootBean> writer = writerEP("/EPAccount.input.csv");
		writer.open(new ExecutionContext());

		return writer;

	}
	
	@Bean(name="writerOldCOM")
	public ItemWriter<EcbRootBean> writerOldCOM() {

		FlatFileItemWriter<EcbRootBean> writer = writerCOM("/Subscription.Old.input.csv");
		writer.open(new ExecutionContext());

		return writer;

	}
	
	@Bean(name="writerNewCOM")
	public ItemWriter<EcbRootBean> writerNewCOM() {

		FlatFileItemWriter<EcbRootBean> writer = writerCOM("/Subscription.New.input.csv");
		writer.open(new ExecutionContext());

		return writer;

	}

}
