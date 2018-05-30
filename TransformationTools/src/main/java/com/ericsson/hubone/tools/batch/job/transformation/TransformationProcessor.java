package com.ericsson.hubone.tools.batch.job.transformation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.CycleFacturation;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.job.transformation.exception.EndpointException;
import com.ericsson.hubone.tools.batch.job.transformation.mapping.CliToAccount;
import com.ericsson.hubone.tools.batch.job.transformation.mapping.CliToEndpoint;
import com.ericsson.hubone.tools.batch.job.transformation.mapping.ComToSubscription;
//import com.ericsson.hubone.tools.report.transformation.CartoClient;

@Component
public class TransformationProcessor<T extends CesameRootBean,V extends EcbRootBean> implements ItemProcessor<T,List<EcbRootBean>> {
	
	@Autowired
	private CliToEndpoint cliToEndpoint;

	@Autowired
	private ComToSubscription comToSubscription;

	@Autowired
	private CliToAccount cliToAccount;
		
	

	@Override
	public List<EcbRootBean> process(T t) throws Exception {		
		return transform(t);
	}

	private List<EcbRootBean> transform(T t) {		

		List<EcbRootBean> list = new ArrayList<EcbRootBean>();

		if(t instanceof Cli){
			list.add(cliToAccount.createAccount((Cli)t));
			if(((Cli)t).getCLIENT_FACTURE()!=null && ((Cli)t).getCLIENT_FACTURE().equals("Y"))
				list.add(cliToAccount.createMinFactSouscription((Cli)t));
			
			if(((Cli)t).getFREQ_CYCLE_FACTU().equals(CycleFacturation.Bimestriel.toString()))
				list.add(cliToAccount.createBimestrielSouscription((Cli)t));
			
			return list;
		}else if (t instanceof Com){

			Com com = (Com)t;			

			Boolean epNullorOk = true;
			Endpoint endpoint = null;

			if(com.getMEDIA()!=null && !com.getMEDIA().equals("")){				

				if(com.getSERVICE_ID()!=null && !com.getSERVICE_ID().equals("")){	

					try {
						endpoint = cliToEndpoint.createEndpoint(com);
					} catch (EndpointException e) {
						epNullorOk = false;
					}

					if(endpoint!=null && endpoint.getNewEP()){
						list.add(endpoint);
						list.add(cliToEndpoint.createEndpointBME(endpoint, com));
						
//						CartoClient.getIntance().addAccount(endpoint.getUserName(),"Endpoint");	
					}

				}
			}

			if(epNullorOk) {
				
				List<EcbRootBean> ecbRootBeanList = null;
				
				if(com.getCODE_PRODUIT_RAFAEL().startsWith("VIDE") ){
					ecbRootBeanList = comToSubscription.createBriqueVide(com);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("REC") || 
						com.getCODE_PRODUIT_RAFAEL().startsWith("RECCA") ){
					ecbRootBeanList = comToSubscription.createAbonnement(com);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("GT") ){					
					ecbRootBeanList = comToSubscription.createGrilleTarifaire(com,endpoint);
				}else if(com.getCODE_PRODUIT_RAFAEL().equals("PONCT") || 
						com.getCODE_PRODUIT_RAFAEL().equals("DEDPRE")){
					ecbRootBeanList = comToSubscription.createFraisPonctuel(com);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("REMV")){
					ecbRootBeanList = comToSubscription.createRemiseVolume(com);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("WIFIROAM")){
					ecbRootBeanList = comToSubscription.createWifiRoam(com,endpoint);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("INTERCOIN")){
					ecbRootBeanList = comToSubscription.createIntercoIn(com,endpoint);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("FORP")){
					ecbRootBeanList = comToSubscription.createForfaitPartage(com);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("FORL")){
					ecbRootBeanList = comToSubscription.createForfaitLigne(com,endpoint);
				}else if(com.getCODE_PRODUIT_RAFAEL().startsWith("DEST")){
					ecbRootBeanList = comToSubscription.createSurcharge(com);
				}else{
					comToSubscription.errorCOM(com, "Code produit RAFAEL non connu :"+com.getCODE_PRODUIT_RAFAEL());
				}
				//			else if(com.getCODE_PRODUIT_RAFAEL().equals("REMPP")){
				//				ecbCom = createRemisePiedDePage(com);



				if(ecbRootBeanList!=null && ecbRootBeanList.size()!=0)
					list.addAll(ecbRootBeanList);
			}

			if(list.size()==0)
				return null;			
			return list;

		}


		return null;
	}








}
