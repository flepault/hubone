package com.ericsson.hubone.tools.batch.job.transformation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;


public class ItemListWriter implements ItemWriter<List<EcbRootBean>> {

	private ItemWriter<EcbRootBean> clientWriter;

	private ItemWriter<EcbRootBean> regroupCFWriter;

	private ItemWriter<EcbRootBean> cfWriter;

	private ItemWriter<EcbRootBean> epWriter;

	private ItemWriter<EcbRootBean> oldSouscriptionWriter;

	private ItemWriter<EcbRootBean> newSouscriptionWriter;

	private ItemWriter<EcbRootBean> oldGroupSouscriptionWriter;

	private ItemWriter<EcbRootBean> newGroupSouscriptionWriter;

	public ItemListWriter(ItemWriter<EcbRootBean> clientWriter, ItemWriter<EcbRootBean> regroupCFWriter,
			ItemWriter<EcbRootBean> cfWriter,ItemWriter<EcbRootBean> epWriter,ItemWriter<EcbRootBean> oldSouscriptionWriter,ItemWriter<EcbRootBean> newSouscriptionWriter,
			ItemWriter<EcbRootBean> oldGroupSouscriptionWriter,ItemWriter<EcbRootBean> newGroupSouscriptionWriter) {
		super();
		this.clientWriter = clientWriter;
		this.regroupCFWriter = regroupCFWriter;
		this.cfWriter = cfWriter;
		this.epWriter = epWriter;
		this.oldSouscriptionWriter = oldSouscriptionWriter;
		this.newSouscriptionWriter = newSouscriptionWriter;
		this.oldGroupSouscriptionWriter = oldGroupSouscriptionWriter;
		this.newGroupSouscriptionWriter = newGroupSouscriptionWriter;
	}



	@Override
	public void write(List<? extends List<EcbRootBean>> items) throws Exception {

		for (List<EcbRootBean> listEcbCliCom : items) {			 

			if(listEcbCliCom.get(0) instanceof Account){					
				if(((Account)listEcbCliCom.get(0)).getAccountType().equals(HierarchieClient.Client.toString())){
					clientWriter.write(listEcbCliCom);
				}else if(((Account)listEcbCliCom.get(0)).getAccountType().equals(HierarchieClient.RegroupCF.toString())){
					regroupCFWriter.write(listEcbCliCom);
				}else if(((Account)listEcbCliCom.get(0)).getAccountType().equals(HierarchieClient.CF.toString()))
					cfWriter.write(listEcbCliCom);			
			}else {

				List<EcbRootBean> epList = new ArrayList<EcbRootBean>();
				List<EcbRootBean> newSubList = new ArrayList<EcbRootBean>();
				List<EcbRootBean> oldSubList = new ArrayList<EcbRootBean>();
				List<EcbRootBean> newGSubList = new ArrayList<EcbRootBean>();
				List<EcbRootBean> oldGSubList = new ArrayList<EcbRootBean>();

				for(EcbRootBean ecbCliCom : listEcbCliCom){
					if(ecbCliCom instanceof Endpoint)
						epList.add(ecbCliCom);
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
					}


				}

				epWriter.write(epList);
				newSouscriptionWriter.write(newSubList);
				oldSouscriptionWriter.write(oldSubList);
				newGroupSouscriptionWriter.write(newGSubList);
				oldGroupSouscriptionWriter.write(oldGSubList);

			}

		}


	}


}