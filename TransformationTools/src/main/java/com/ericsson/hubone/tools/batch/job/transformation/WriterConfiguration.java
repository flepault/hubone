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
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.EndpointBME;
import com.ericsson.hubone.tools.batch.data.ecb.FlatRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.NonRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.RampBucket;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.SubscriptionInfoBME;
import com.ericsson.hubone.tools.batch.data.ecb.XPCMS;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;

@Component
public class WriterConfiguration {
		
	@Autowired
	public FilesNames filesNames;
	
	private FlatFileItemWriter<EcbRootBean> writer(String ouput,String header, String[] column,String encoding) {

		FlatFileItemWriter<EcbRootBean> writer = new FlatFileItemWriter<EcbRootBean>();

		
		writer.setShouldDeleteIfExists(true);
		
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.write(header);

			}
		});
		writer.setResource(new FileSystemResource(FilesNames.OUTPUT_FOLDER+"/"+ouput));
		DelimitedLineAggregator<EcbRootBean> delLineAgg = new DelimitedLineAggregator<EcbRootBean>();
		delLineAgg.setDelimiter("|");		
		BeanWrapperFieldExtractor<EcbRootBean> fieldExtractor = new BeanWrapperFieldExtractor<EcbRootBean>();
		fieldExtractor.setNames(column);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		try {
			writer.afterPropertiesSet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encoding!=null)
			writer.setEncoding(encoding);
		
		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerClient() {
		
		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.Client,Account.header(),Account.column,null);
		writer.open(new ExecutionContext());	

		return writer;

	}

	public ItemWriter<EcbRootBean> writerRegroupCF() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.RegroupCF,Account.header(),Account.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerCF() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.CF,Account.header(),Account.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerEP() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.EP,Endpoint.header(),Endpoint.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerEPBME() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.EPBME,EndpointBME.header(),EndpointBME.column,"Cp1252");
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerOldSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.SubscriptionOld,SimpleSubscription.header(),SimpleSubscription.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerNewSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.SubscriptionNew,SimpleSubscription.header(),SimpleSubscription.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerOldGroupSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.GroupSouscriptionOld,GroupSubscription.header(),GroupSubscription.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

	public ItemWriter<EcbRootBean> writerNewGroupSubscription() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.GroupSouscriptionNew,GroupSubscription.header(),GroupSubscription.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerSubscriptionInfoBME() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.SubscriptionInfoBME,SubscriptionInfoBME.header(),SubscriptionInfoBME.column,"Cp1252");
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerFlatRecurringCharge() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.FlatRecurringCharge,FlatRecurringCharge.header(),FlatRecurringCharge.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerNonRecurringCharge() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.NonRecurringCharge,NonRecurringCharge.header(),NonRecurringCharge.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerRampBucket() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.RampBucket,RampBucket.header(),RampBucket.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}
	
	public ItemWriter<EcbRootBean> writerXPCMS() {

		FlatFileItemWriter<EcbRootBean> writer = writer(filesNames.XPCMS,XPCMS.header(),XPCMS.column,null);
		writer.open(new ExecutionContext());

		return writer;

	}

}
