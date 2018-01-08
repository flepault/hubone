package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
//import com.ericsson.hubone.tools.report.transformation.CartoClient;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReportLine;

@Component
public class ComToSubscription extends MappingConstants{	

	private Date firstDayOfMonthDate; 

	public ComToSubscription() {
		Calendar c = Calendar.getInstance();   // this takes current date
		c.set(Calendar.DAY_OF_MONTH, 1);
		firstDayOfMonthDate = c.getTime(); 
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

		subscription.setAppliedPrice(com.getPRIX_APPLIQUE_MANUEL());
		subscription.setCatalogPrice(com.getPRIX_CATALOGUE());
		subscription.setClientCommandRef(com.getREF_COMMANDE_CLIENT());
		subscription.setCommandId(com.getCODE_COMMANDE());
		subscription.setDiscountAmount(com.getMONTANT_REMISE_MANUEL());
		subscription.setDiscountPercent(com.getPOURCENT_REMISE_MANUEL());
		subscription.setIsOnDemand("N");

		subscription.setMedia(com.getMEDIA());
		subscription.setParentProductId(com.getID_SIEBEL_PRESTATION());
		subscription.setProductId(com.getID_SIEBEL_LIGNE());
		subscription.setProductIntegrationId(com.getINTEGRATION_ID());
		subscription.setQuantity(com.getQUANTITE_PRODUIT());
		subscription.setServiceId(com.getSERVICE_ID());

		subscription.setSiteAddressId(com.getADRESSE_ID());		

		TransformationReport.getIntance().increaseSouscription();

		return subscription;
	}


	public Subscription createFraisPonctuel(Com com){

		Subscription ecbCOM = createSouscription(com,false);

		ecbCOM.setiCBValue(BigDecimal.valueOf(new Double(com.getPRIX_APPLIQUE_MANUEL())*new Double(com.getQUANTITE_PRODUIT()))
			    .setScale(2, RoundingMode.HALF_UP)
			    .toString());
		ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");
		
//		if(com.getCODE_CLIENT()!=null)				
//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return ecbCOM;
	}

	public Subscription createAbonnement(Com com){

		Subscription ecbCOM = createSouscription(com,false);

		ecbCOM.setiCBValue(BigDecimal.valueOf(new Double(com.getPRIX_APPLIQUE_MANUEL())*new Double(com.getQUANTITE_PRODUIT()))
			    .setScale(2, RoundingMode.HALF_UP)
			    .toString());
		ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");
		
//		if(com.getCODE_CLIENT()!=null)				
//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return ecbCOM;
	}

	public Subscription createGrilleTarifaire(Com com,Endpoint endpoint){

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

		Subscription ecbCOM = createSouscription(com,gsub);
		ecbCOM.setProductOfferingId("TETRA0001U");
		if(!gsub) {
			if(endpoint==null) {
				errorCOM(com,"Aucun endpoint détecté pour la souscription de la grille tarifaire");
				return null;
			}else
				ecbCOM.setAccountId(endpoint.getUserName());
		}else {
			ecbCOM.setIsSharedTariffGrid("Y");		
		}




		return ecbCOM;
	}

	public Subscription createForfaitLigne(Com com,Endpoint endpoint) {
		Subscription ecbCOM = createSouscription(com,false);

		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour le forfait ligne");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());
		
//		if(endpoint.getUserName()!=null)				
//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());

		//ecbCOM.setiCBValue(com.getPRIX_APPLIQUE_MANUEL());
		//ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");

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

		return ecbCOM;
	}

	public Subscription createForfaitPartage(Com com){
		Subscription ecbCOM = createSouscription(com,false);
		
//		if(com.getCODE_CLIENT()!=null)				
//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		//	Client/RegroupCF/CF	
		//ecbCOM.setiCBValue(com.getPRIX_APPLIQUE_MANUEL());
		//ecbCOM.setPiName(com.getCODE_PRODUIT_RAFAEL()+"_PI");

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
		}else
			ecbCOM.setSharedBucketScope(NIVEAU_APPLICATION);


		return ecbCOM;
	}

	public Subscription createSurcharge(Com com,boolean gsub){

		Subscription ecbCOM = createSouscription(com,false);
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

	public Subscription createRemiseVolume(Com com){
		Subscription ecbCOM = createSouscription(com,false);
		
//		if(com.getCODE_CLIENT()!=null)				
//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return ecbCOM;
	}

	public Subscription createWifiRoam(Com com,Endpoint endpoint) {
		Subscription ecbCOM = createSouscription(com,false);
		
//		if(endpoint!=null && endpoint.getUserName()!=null)				
//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());

		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour la souscription de wifi roaming");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());

		return ecbCOM;
	}

	public Subscription createIntercoIn(Com com,Endpoint endpoint) {
		Subscription ecbCOM = createSouscription(com,false);
		
//		if(endpoint!=null && endpoint.getUserName()!=null)				
//			CartoClient.getIntance().addSub(endpoint.getUserName(),com.getCODE_PRODUIT_RAFAEL());
		
		if(endpoint==null) {
			errorCOM(com,"Aucun endpoint détecté pour la souscription d'interco in");
			return null;
		}else
			ecbCOM.setAccountId(endpoint.getUserName());
		return ecbCOM;
	}

	public void errorCOM(Com com,String str){

		TransformationReportLine trl = new TransformationReportLine("ERR-TRSF-03", com.getID_SIEBEL_LIGNE(),str);

		TransformationReport.getIntance().increaseSouscriptionError(trl);

	}




}
