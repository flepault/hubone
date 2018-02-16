package com.ericsson.hubone.tools.batch.job.transformation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.EndpointBME;
import com.ericsson.hubone.tools.batch.data.ecb.FlatRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.NonRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.RampBucket;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
import com.ericsson.hubone.tools.batch.data.ecb.SubscriptionInfoBME;
import com.ericsson.hubone.tools.batch.data.ecb.XPCMS;


public class ItemListWriter implements ItemWriter<List<EcbRootBean>> {

	private ItemWriter<EcbRootBean> clientWriter;

	private ItemWriter<EcbRootBean> regroupCFWriter;

	private ItemWriter<EcbRootBean> cfWriter;

	private ItemWriter<EcbRootBean> epWriter;

	private ItemWriter<EcbRootBean> epBMEWriter;

	private ItemWriter<EcbRootBean> oldSouscriptionWriter;

	private ItemWriter<EcbRootBean> newSouscriptionWriter;

	private ItemWriter<EcbRootBean> oldGroupSouscriptionWriter;

	private ItemWriter<EcbRootBean> newGroupSouscriptionWriter;

	private ItemWriter<EcbRootBean> subscriptionInfoBMEWriter;

	private ItemWriter<EcbRootBean> flatRecurringChargeWriter;

	private ItemWriter<EcbRootBean> nonRecurringChargeWriter;
	
	private ItemWriter<EcbRootBean> rampBucketWriter;

	private ItemWriter<EcbRootBean> xpcmsWriter;

	public ItemListWriter(ItemWriter<EcbRootBean> clientWriter, ItemWriter<EcbRootBean> regroupCFWriter,
			ItemWriter<EcbRootBean> cfWriter,ItemWriter<EcbRootBean> epWriter,ItemWriter<EcbRootBean> epBMEWriter,ItemWriter<EcbRootBean> oldSouscriptionWriter,ItemWriter<EcbRootBean> newSouscriptionWriter,
			ItemWriter<EcbRootBean> oldGroupSouscriptionWriter,ItemWriter<EcbRootBean> newGroupSouscriptionWriter,
			ItemWriter<EcbRootBean> subscriptionInfoBMEWriter,ItemWriter<EcbRootBean> flatRecurringChargeWriter,ItemWriter<EcbRootBean> nonRecurringChargeWriter,
			ItemWriter<EcbRootBean> rampBucketWriter,ItemWriter<EcbRootBean> xpcmsWriter) {
		super();
		this.clientWriter = clientWriter;
		this.regroupCFWriter = regroupCFWriter;
		this.cfWriter = cfWriter;
		this.epWriter = epWriter;
		this.epBMEWriter = epBMEWriter;
		this.oldSouscriptionWriter = oldSouscriptionWriter;
		this.newSouscriptionWriter = newSouscriptionWriter;
		this.oldGroupSouscriptionWriter = oldGroupSouscriptionWriter;
		this.newGroupSouscriptionWriter = newGroupSouscriptionWriter;
		this.subscriptionInfoBMEWriter = subscriptionInfoBMEWriter;
		this.flatRecurringChargeWriter = flatRecurringChargeWriter;
		this.nonRecurringChargeWriter = nonRecurringChargeWriter;
		this.rampBucketWriter = rampBucketWriter;
		this.xpcmsWriter = xpcmsWriter;
	}



	@Override
	public void write(List<? extends List<EcbRootBean>> items) throws Exception {

		for (List<EcbRootBean> listEcbCliCom : items) {			 


			List<EcbRootBean> clientList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> regroupCFList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> cfList = new ArrayList<EcbRootBean>();

			List<EcbRootBean> epList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> epBMEList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> newSubList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> oldSubList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> newGSubList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> oldGSubList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> subBMEList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> recICBList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> nonRecICBList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> rampBucketICBList = new ArrayList<EcbRootBean>();
			List<EcbRootBean> xpcmsICBList = new ArrayList<EcbRootBean>();

			for(EcbRootBean ecbCliCom : listEcbCliCom){
				if(ecbCliCom instanceof Account){					
					if(((Account)ecbCliCom).getAccountType().equals(HierarchieClient.Client.toString())){
						clientList.add(ecbCliCom);
					}else if(((Account)ecbCliCom).getAccountType().equals(HierarchieClient.RegroupCF.toString())){
						regroupCFList.add(ecbCliCom);
					}else if(((Account)ecbCliCom).getAccountType().equals(HierarchieClient.CF.toString()))
						cfList.add(ecbCliCom);	
				}else if(ecbCliCom instanceof Endpoint)
					epList.add(ecbCliCom);
				else if(ecbCliCom instanceof EndpointBME)
					epBMEList.add(ecbCliCom);
				else if(ecbCliCom instanceof SimpleSubscription){
					if(((Subscription)ecbCliCom).getNewCOM())
						newSubList.add(ecbCliCom);
					else
						oldSubList.add(ecbCliCom);
				}else if(ecbCliCom instanceof GroupSubscription){
					if(((Subscription)ecbCliCom).getNewCOM())
						newGSubList.add(ecbCliCom);
					else
						oldGSubList.add(ecbCliCom);
				}else if(ecbCliCom instanceof SubscriptionInfoBME) {
					subBMEList.add(ecbCliCom);
				}else if(ecbCliCom instanceof FlatRecurringCharge) {
					recICBList.add(ecbCliCom);
				}else if(ecbCliCom instanceof NonRecurringCharge) {
					nonRecICBList.add(ecbCliCom);
				}else if(ecbCliCom instanceof RampBucket) {
					rampBucketICBList.add(ecbCliCom);
				}else if(ecbCliCom instanceof XPCMS) {
					xpcmsICBList.add(ecbCliCom);
				}


			}

			clientWriter.write(clientList);
			regroupCFWriter.write(regroupCFList);
			cfWriter.write(cfList);						
			epWriter.write(epList);
			epBMEWriter.write(epBMEList);
			newSouscriptionWriter.write(newSubList);
			oldSouscriptionWriter.write(oldSubList);
			newGroupSouscriptionWriter.write(newGSubList);
			oldGroupSouscriptionWriter.write(oldGSubList);
			subscriptionInfoBMEWriter.write(subBMEList);
			flatRecurringChargeWriter.write(recICBList);
			nonRecurringChargeWriter.write(nonRecICBList);
			rampBucketWriter.write(rampBucketICBList);
			xpcmsWriter.write(xpcmsICBList);

		}


	}


}