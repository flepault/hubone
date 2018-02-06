package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.FlatRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.NonRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
import com.ericsson.hubone.tools.batch.data.ecb.SubscriptionInfoBME;
//import com.ericsson.hubone.tools.report.transformation.CartoClient;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReportLine;

@Component
public class ComToSubscription extends MappingConstants{	

	private Date firstDayOfMonthDate; 

	public ComToSubscription() {
		Calendar c = Calendar.getInstance();   // this takes current date
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, -1);
		firstDayOfMonthDate = c.getTime(); 
	}

	private SubscriptionInfoBME createSubcriptionInfoBME(Com com,String migrationID){

		SubscriptionInfoBME subscriptionInfoBME = new SubscriptionInfoBME();

		subscriptionInfoBME.setQuantity(com.getQUANTITE_PRODUIT());
		subscriptionInfoBME.setCatalogPrice(com.getPRIX_CATALOGUE());
		subscriptionInfoBME.setDiscountPercent(com.getPOURCENT_REMISE_MANUEL());
		subscriptionInfoBME.setDiscountAmount(com.getMONTANT_REMISE_MANUEL());
		subscriptionInfoBME.setIsOnDemand("N");		
		subscriptionInfoBME.setAppliedPrice(com.getPRIX_APPLIQUE_MANUEL());
		subscriptionInfoBME.setSiteAddressId(com.getADRESSE_ID());
		subscriptionInfoBME.setProductIntegrationId(com.getINTEGRATION_ID());
		subscriptionInfoBME.setProductId(com.getID_SIEBEL_LIGNE());
		subscriptionInfoBME.setParentProductId(com.getID_SIEBEL_PRESTATION());
		subscriptionInfoBME.setMedia(com.getMEDIA());
		subscriptionInfoBME.setCommandId(com.getCODE_COMMANDE());
		subscriptionInfoBME.setAttributs(com.getPARAM_PRODUIT_ADD());
		subscriptionInfoBME.setServiceId(com.getSERVICE_ID());	
		subscriptionInfoBME.setClientCommandRef(com.getREF_COMMANDE_CLIENT());
		subscriptionInfoBME.setModifyAppliedDate("");		
		subscriptionInfoBME.setResiliateAppliedDate("");
		try {
			Date lastBillApplicationDate = formatCOM.parse(com.getDATE_APPLI_FACTU());

			if(migrationHubOneStartDate.after(lastBillApplicationDate))
				lastBillApplicationDate = migrationHubOneStartDate;
			
			subscriptionInfoBME.setCreateAppliedDate(format.format(lastBillApplicationDate));
		} catch (ParseException e) {
			errorCOM(com,e.getMessage());
			return null;
		}


		subscriptionInfoBME.setMigrationId("{"+migrationID+"}");		

		return subscriptionInfoBME;

	}


	private Subscription createSouscription(Com com,boolean gsub){

		Subscription subscription = null;

		if(gsub){
			subscription = new GroupSubscription();
			((GroupSubscription)subscription).setName(com.getCODE_CLIENT()+"_"+com.getCODE_PRODUIT_RAFAEL()+"_"+com.getINTEGRATION_ID());
			((GroupSubscription)subscription).setDescription(com.getCODE_CLIENT()+"_"+com.getCODE_PRODUIT_RAFAEL()+"_"+com.getINTEGRATION_ID());
		}else
			subscription = new SimpleSubscription();

		subscription.setAccountId(com.getCODE_CLIENT());
		try {

			Date lastModificationDate = formatCOM.parse(com.getDATE_MODIFICATION());

			if(migrationHubOneStartDate.after(lastModificationDate))
				lastModificationDate = migrationHubOneStartDate;

			subscription.setStartDate(format.format(lastModificationDate));

			if(firstDayOfMonthDate.before(lastModificationDate))
				subscription.setNewCOM(true);
			else
				subscription.setNewCOM(false);



		} catch (ParseException e) {
			errorCOM(com,e.getMessage());
			return null;
		}

		subscription.setEndDate("");
		subscription.setCommercialProdCode(com.getCODE_PRODUIT_SIEBEL());
		subscription.setProductOfferingId(com.getCODE_PRODUIT_RAFAEL());
		subscription.setMigrationId(UUID.randomUUID().toString());		

		TransformationReport.getIntance().increaseSouscription();

		return subscription;
	}


	public List<EcbRootBean> createFraisPonctuel(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		BigDecimal icbValue = BigDecimal.valueOf(new Double(com.getPRIX_APPLIQUE_MANUEL())*new Double(com.getQUANTITE_PRODUIT()))
				.setScale(2, RoundingMode.HALF_UP);

		if(icbValue.doubleValue() > 0) {

			NonRecurringCharge nonRecurringCharge = new NonRecurringCharge();
			nonRecurringCharge.setiCBAccountId(ecbCOM.getAccountId());
			nonRecurringCharge.setMigrationId(ecbCOM.getMigrationId());
			nonRecurringCharge.setnRCAmount(icbValue.toString());
			nonRecurringCharge.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");
			nonRecurringCharge.setPoName(com.getCODE_PRODUIT_RAFAEL());
			nonRecurringCharge.setPriceListName("");
			nonRecurringCharge.setRateType("ICBRate");

			listEcbRootBeans.add(nonRecurringCharge);
		}

		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createAbonnement(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		BigDecimal icbValue = BigDecimal.valueOf(new Double(com.getPRIX_APPLIQUE_MANUEL())*new Double(com.getQUANTITE_PRODUIT()))
				.setScale(2, RoundingMode.HALF_UP);

		if(icbValue.doubleValue() > 0) {

			FlatRecurringCharge flatRecurringCharge = new FlatRecurringCharge();
			flatRecurringCharge.setiCBAccountId(ecbCOM.getAccountId());
			flatRecurringCharge.setMigrationId(ecbCOM.getMigrationId());
			flatRecurringCharge.setrCAmount(icbValue.toString());
			flatRecurringCharge.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");
			flatRecurringCharge.setPoName(com.getCODE_PRODUIT_RAFAEL());
			flatRecurringCharge.setPriceListName("");
			flatRecurringCharge.setRateType("ICBRate");

			listEcbRootBeans.add(flatRecurringCharge);
		}

		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createGrilleTarifaire(Com com,Endpoint endpoint){

		boolean gsub = false;
		//TODO Replace with PARTAGE

		String PARTAGE = null;

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("PARTAGE")){
					PARTAGE = param.split("=")[1];
					if(!param.split("=")[1].equals("N")){
						gsub = true;
						break;
					}
				}
			}
		}

		//		if(endpoint!=null && endpoint.getUserName()!=null)				
		//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());
		//		
		if(PARTAGE==null) {
			errorCOM(com, "Aucune information de PARTAGE disponible");
			return null;
		}

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,gsub);
		if(ecbCOM==null)
				return null;
		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return  null;

		if(!gsub) {
			if(endpoint==null) {
				errorCOM(com,"Aucun endpoint détecté pour la souscription de la grille tarifaire");
				return null;
			}else {
				ecbCOM.setAccountId(endpoint.getUserName());
				//subscriptionInfoBME.setGroupId(groupId);
			}

		}else {
			subscriptionInfoBME.setIsSharedTariffGrid("Y");		
		}

		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);


		return listEcbRootBeans;
	}

	public List<EcbRootBean> createForfaitLigne(Com com,Endpoint endpoint) {
		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);		
		if(ecbCOM==null)
			return null;
		
		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour le forfait ligne");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());

		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		//		if(endpoint.getUserName()!=null)				
		//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());

		String CREDIT_FORFAIT = null;

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("CREDIT_FORFAIT")){
					CREDIT_FORFAIT = param.split("=")[1];
					break;
				}
			}
		}

		if(CREDIT_FORFAIT==null) {
			errorCOM(com, "Aucun CREDIT_FORFAIT disponible");
			return null;
		}

		//ICB
		//ecbCOM.setiCBValue(com.getPRIX_APPLIQUE_MANUEL());
		//ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createForfaitPartage(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());				
		if(subscriptionInfoBME==null)
			return null;
		
		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		String CREDIT_FORFAIT = null;
		String NIVEAU_APPLICATION = null;

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("CREDIT_FORFAIT")){
					CREDIT_FORFAIT = param.split("=")[1];
				}
				if(param.split("=")[0].equals("NIVEAU_APPLICATION")){
					NIVEAU_APPLICATION = param.split("=")[1];
				}
			}
		}
		if(CREDIT_FORFAIT==null || NIVEAU_APPLICATION==null) {

			if(CREDIT_FORFAIT==null) {
				errorCOM(com, "Aucun CREDIT_FORFAIT disponible");				
			}

			if(NIVEAU_APPLICATION==null) {
				errorCOM(com, "Aucun NIVEAU_APPLICATION disponible");				
			}

			return null;
		}else {
			if(NIVEAU_APPLICATION=="CG")
				subscriptionInfoBME.setSharedBucketScope("0");
			else if(NIVEAU_APPLICATION=="GCF")
				subscriptionInfoBME.setSharedBucketScope("1");
			else if(NIVEAU_APPLICATION=="CF")
				subscriptionInfoBME.setSharedBucketScope("2");
			else {
				errorCOM(com, "NIVEAU_APPLICATION incorrecte");	
				return null;
			}
		}

		//ICB
		//	Client/RegroupCF/CF	
		//ecbCOM.setiCBValue(com.getPRIX_APPLIQUE_MANUEL());
		//ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");		

		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		return listEcbRootBeans;
	}

	public Subscription createSurcharge(Com com,boolean gsub){

		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		//subscription.setAccountId(com.getCODE_CLIENT());

		//ecbCOM.setTargetTariffGridId(targetTariffGridId);

		String DESTINATION = null;
		String PRIX = null;

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("DESTINATION")){
					DESTINATION = param.split("=")[1];
				}else if(param.split("=")[0].equals("PRIX")){
					PRIX = param.split("=")[1];
				}
			}
		}

		if(DESTINATION==null) {
			errorCOM(com, "Aucune DESTINATION disponible");
			return null;
		}
		if(PRIX==null) {
			errorCOM(com, "Aucun PRIX disponible");
			return null;
		}		

		return ecbCOM;
	}

	public Subscription createRemisePiedDePage(Com com){
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		String POURCENTAGE = new String();

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("POURCENTAGE")){

					POURCENTAGE = param.split("=")[1];
					break;
				}
			}
		}

		if(POURCENTAGE==null) {
			errorCOM(com, "Aucun POURCENTAGE disponible");
			return null;
		}

		return ecbCOM;
	}

	public List<EcbRootBean> createRemiseVolume(Com com){
		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createWifiRoam(Com com,Endpoint endpoint) {
		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		//		if(endpoint!=null && endpoint.getUserName()!=null)				
		//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());

		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour la souscription de wifi roaming");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());

		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createIntercoIn(Com com,Endpoint endpoint) {
		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		//		if(endpoint!=null && endpoint.getUserName()!=null)				
		//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());

		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour la souscription d'interco in");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());

		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;
		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);
		return listEcbRootBeans;
	}

	public void errorCOM(Com com,String str){

		TransformationReportLine trl = new TransformationReportLine("ERR-TRSF-03", com.getID_SIEBEL_LIGNE(),str);

		TransformationReport.getIntance().increaseSouscriptionError(trl);

	}




}
