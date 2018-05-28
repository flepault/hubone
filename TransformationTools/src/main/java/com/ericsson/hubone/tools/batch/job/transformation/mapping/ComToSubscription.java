package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.ecb.EcbRootBean;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.FlatRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.GroupSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.NonRecurringCharge;
import com.ericsson.hubone.tools.batch.data.ecb.RampBucket;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
import com.ericsson.hubone.tools.batch.data.ecb.SubscriptionInfoBME;
import com.ericsson.hubone.tools.batch.data.ecb.XPCMS;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;
//import com.ericsson.hubone.tools.report.transformation.CartoClient;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReportLine;

@Component
public class ComToSubscription extends MappingConstants{	

	private Date firstDayOfMonthDate; 

	private HashMap<String,String> tariffCodeMap = new HashMap<String,String>();

	private HashMap<String,String> destZoneIdtariffCodeMap = new HashMap<String,String>();

	private HashMap<String,HashMap<String,String[]>> xpcmsMap = new HashMap<String,HashMap<String,String[]>>();
	
	public ComToSubscription(@Value("${nb.month.back}")Integer nbMonthBack){
		Calendar c = Calendar.getInstance();   // this takes current date
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.MONTH, -nbMonthBack);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		firstDayOfMonthDate = c.getTime(); 
		init();
	}

	private void init() {
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(new FileReader(FilesNames.INPUT_FOLDER+"/"+"RampBucketMapping.csv"));              
			while ((line = br.readLine()) != null) {   
				String[] strLine = line.split("\\|");
				tariffCodeMap.put(strLine[0], strLine[1]);   
			}
			br.close();

			br = new BufferedReader(new FileReader(FilesNames.INPUT_FOLDER+"/"+"DesZoneIdTariffCodeMapping.csv"));                
			while ((line = br.readLine()) != null) {     
				String[] strLine = line.split("\\|");
				destZoneIdtariffCodeMap.put(strLine[0], strLine[1]);
			}
			br.close();

			br = new BufferedReader(new FileReader(FilesNames.INPUT_FOLDER+"/"+"XPCMSMapping.csv"));  
			while ((line = br.readLine()) != null) {  
				String[] strLine = line.split("\\|");
				if(xpcmsMap.get(strLine[0])==null) {            		
					HashMap<String,String[]> map = new HashMap<String,String[]>();
					map.put(strLine[1], new String[]{strLine[2],strLine[3],strLine[4],strLine[5],strLine[6]});
					xpcmsMap.put(strLine[0], map);
				}else {
					xpcmsMap.get(strLine[0]).put(strLine[1], new String[]{strLine[2],strLine[3],strLine[4],strLine[5],strLine[6]});
				}

			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
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
		subscriptionInfoBME.setResiliateAppliedDate("");
		subscriptionInfoBME.setResiliateAppliedDateUsed("");
		try {
			Date lastBillApplicationDate = formatCOM.parse(com.getDATE_APPLI_FACTU());

			if(migrationHubOneSubscriptionStartDate.after(lastBillApplicationDate))
				lastBillApplicationDate = migrationHubOneSubscriptionStartDate;

			subscriptionInfoBME.setCreateAppliedDate(format.format(lastBillApplicationDate));
			subscriptionInfoBME.setModifyAppliedDate(format.format(lastBillApplicationDate));
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(lastBillApplicationDate);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
						
			subscriptionInfoBME.setCreateAppliedDateUsed(format.format(c.getTime()));
			subscriptionInfoBME.setModifyAppliedDateUsed(format.format(c.getTime()));
			
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

			Date lastBillApplicationDate = formatCOM.parse(com.getDATE_APPLI_FACTU());

			if(migrationHubOneSubscriptionStartDate.after(lastBillApplicationDate))
				lastBillApplicationDate = migrationHubOneSubscriptionStartDate;	

			subscription.setStartDate(format.format(lastBillApplicationDate));

			Date lastModificationDate = formatCOM.parse(com.getDATE_MODIFICATION());
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

	public List<EcbRootBean> createBriqueVide(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;	
		ecbCOM.setMigrationId("");
		listEcbRootBeans.add(ecbCOM);

		//		if(com.getCODE_CLIENT()!=null)				
		//			CartoClient.getIntance().addSub(com.getCODE_CLIENT(),com.getCODE_PRODUIT_RAFAEL());

		return listEcbRootBeans;
	}


	public List<EcbRootBean> createFraisPonctuel(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();
		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;
		
		try{
			Calendar c = Calendar.getInstance();   // this takes current date		
			c.setTime(format.parse(ecbCOM.getStartDate()));
			c.add(Calendar.SECOND, 1);
			ecbCOM.setEndDate(format.format(c.getTime()));
		}
		catch (ParseException e) {
			errorCOM(com,e.getMessage());
			return null;
		}
				
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
				subscriptionInfoBME.setIsSharedTariffGrid("N");	
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
		}else {
			RampBucket rampBucket = new RampBucket();
			rampBucket.setiCBAccountId(ecbCOM.getAccountId());
			rampBucket.setMigrationId(ecbCOM.getMigrationId());
			rampBucket.setPoName(com.getCODE_PRODUIT_RAFAEL());
			rampBucket.setPiName("RampBucket");
			rampBucket.setRateType("ICBRate");
			rampBucket.setPriceListName("");
			rampBucket.setAccQualGroup("Self");

			if(com.getCODE_PRODUIT_RAFAEL().contains("TETRA"))
				rampBucket.setUsgQualGroup("Tetra_UQG");
			else
				rampBucket.setUsgQualGroup("Voix_UQG");

			rampBucket.setItemsToAggregate("units");
			rampBucket.setTierId("T01");
			rampBucket.setTierName("");
			rampBucket.setStartOfUnitRange("0");
			rampBucket.setEndOfUnitRange(CREDIT_FORFAIT);
			rampBucket.setPriority("1");
			String tariffCodeList = tariffCodeMap.get(com.getCODE_PRODUIT_RAFAEL());
			if(tariffCodeList==null) {
				errorCOM(com, "le FORFAIT ne possède pas de liste de code tarif");	
				return null;
			}else			
				rampBucket.setTariffCodesList(tariffCodeList);
			rampBucket.setNewRate("0");

			listEcbRootBeans.add(rampBucket);
		}

		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

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
			if(NIVEAU_APPLICATION.equals("CG"))
				subscriptionInfoBME.setSharedBucketScope("0");
			else if(NIVEAU_APPLICATION.equals("GCF"))
				subscriptionInfoBME.setSharedBucketScope("1");
			else if(NIVEAU_APPLICATION.equals("CF"))
				subscriptionInfoBME.setSharedBucketScope("2");
			else {
				errorCOM(com, "NIVEAU_APPLICATION incorrecte");	
				return null;
			}

			RampBucket rampBucket = new RampBucket();
			rampBucket.setiCBAccountId(ecbCOM.getAccountId());
			rampBucket.setMigrationId(ecbCOM.getMigrationId());
			rampBucket.setPoName(com.getCODE_PRODUIT_RAFAEL());
			rampBucket.setPiName("RampBucket");
			rampBucket.setRateType("ICBRate");
			rampBucket.setPriceListName("");
			rampBucket.setAccQualGroup("Payees");

			if(com.getCODE_PRODUIT_RAFAEL().contains("TETRA"))
				rampBucket.setUsgQualGroup("Tetra_UQG");
			else
				rampBucket.setUsgQualGroup("Voix_UQG");

			rampBucket.setItemsToAggregate("units");
			rampBucket.setTierId("T01");
			rampBucket.setTierName("");
			rampBucket.setStartOfUnitRange("0");

			if(com.CODE_PRODUIT_RAFAEL.getValue().contains("DUR")) {
				BigDecimal icbValue = BigDecimal.valueOf(new Double(CREDIT_FORFAIT)*new Double(3600))
						.setScale(10, RoundingMode.HALF_UP);
				rampBucket.setEndOfUnitRange(icbValue.toString());
			}else if(com.CODE_PRODUIT_RAFAEL.getValue().contains("VOL")) {
				BigDecimal icbValue = BigDecimal.valueOf(new Double(CREDIT_FORFAIT)*new Double(1000000))
						.setScale(10, RoundingMode.HALF_UP);
				rampBucket.setEndOfUnitRange(icbValue.toString());
			}if(com.CODE_PRODUIT_RAFAEL.getValue().contains("ACT")) {
				rampBucket.setEndOfUnitRange(CREDIT_FORFAIT);
			}


			rampBucket.setPriority("10");

			String tariffCodeList = tariffCodeMap.get(com.getCODE_PRODUIT_RAFAEL());
			if(tariffCodeList==null) {
				errorCOM(com, "le FORFAIT ne possède pas de liste de code tarif");	
				return null;
			}else			
				rampBucket.setTariffCodesList(tariffCodeList);
			rampBucket.setNewRate("0");

			listEcbRootBeans.add(rampBucket);

		}

		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);

		return listEcbRootBeans;
	}

	public List<EcbRootBean> createSurcharge(Com com){

		List<EcbRootBean> listEcbRootBeans = new ArrayList<EcbRootBean>();

		Subscription ecbCOM = createSouscription(com,false);
		if(ecbCOM==null)
			return null;		

		String DESTINATION = null;
		String PRIX = null;
		String GRILLE = null;
		String PARENT_ID = null;

		if(com.getPARAM_PRODUIT_ADD()!=null || !com.getPARAM_PRODUIT_ADD().equals("")){
			for(String param:com.getPARAM_PRODUIT_ADD().split(";")){
				if(param.split("=")[0].equals("DESTINATION")){
					DESTINATION = param.split("=")[1];
				}else if(param.split("=")[0].equals("PRIX")){
					PRIX = param.split("=")[1];
				}else if(param.split("=")[0].equals("GRILLE")){
					GRILLE = param.split("=")[1];
				}else if(param.split("=")[0].equals("PARENT_ID")){
					PARENT_ID = param.split("=")[1];
				}
			}
		}

		if(DESTINATION==null) {
			errorCOM(com, "Aucune DESTINATION disponible");
			return null;
		}else if(PRIX==null) {
			errorCOM(com, "Aucun PRIX disponible");
			return null;
		}else if(GRILLE==null) {
			errorCOM(com, "Aucune information sur la GRILLE surchargé");
			return null;
		}else if(PARENT_ID==null) {
			errorCOM(com, "Aucune information sur le PARENT_ID surchargé");
			return null;
		}else {

			XPCMS xpcms = new XPCMS();
			xpcms.setiCBAccountId(ecbCOM.getAccountId());
			xpcms.setMigrationId(ecbCOM.getMigrationId());
			xpcms.setPoName(GRILLE);
			if(com.getCODE_PRODUIT_RAFAEL().contains("TETRA"))
				xpcms.setPiName("Tetra_PI");
			else
				xpcms.setPiName("Voix_PI");
			xpcms.setRateType("ICBRate");
			xpcms.setOriginZoneId("");
			if(DESTINATION.startsWith("CT_")) {
				xpcms.setDestZoneId("");
				xpcms.setTariffCode(DESTINATION);
			}else {
				xpcms.setDestZoneId(DESTINATION);

				String tariffCode = destZoneIdtariffCodeMap.get(DESTINATION);
				if(tariffCode==null) {
					errorCOM(com, "La DESTINATION fourni est non défini dans le catalogue");
					return null;
				}

				xpcms.setTariffCode(tariffCode);
			}

			if(com.CODE_PRODUIT_RAFAEL.getValue().equals("DESTDUR")) {
				BigDecimal icbValue = BigDecimal.valueOf(new Double(PRIX)/new Double(60))
						.setScale(10, RoundingMode.HALF_UP);
				xpcms.setUnitPrice(icbValue.toString());	
			}else if(com.CODE_PRODUIT_RAFAEL.getValue().equals("DESTDUR")) {
				BigDecimal icbValue = BigDecimal.valueOf(new Double(PRIX)/new Double(1000))
						.setScale(10, RoundingMode.HALF_UP);
				xpcms.setUnitPrice(icbValue.toString());
			}else if(com.CODE_PRODUIT_RAFAEL.getValue().equals("DESTACT")) 
				xpcms.setUnitPrice(PRIX);	
			else {
				errorCOM(com, "Le code produit RAFAEL "+com.CODE_PRODUIT_RAFAEL.getValue()+" n'existe pas");
				return null;
			}

			HashMap<String,String[]> grilleMap = xpcmsMap.get(GRILLE);			
			if(grilleMap==null) {
				errorCOM(com, "La GRILLE fourni est non défini dans le catalogue");
				return null;
			}else {
				String[] infoXpcmsTab = null;
				if(DESTINATION.startsWith("CT_")) 
					infoXpcmsTab = grilleMap.get(DESTINATION);
				else
					infoXpcmsTab = grilleMap.get(destZoneIdtariffCodeMap.get(DESTINATION));
				if(infoXpcmsTab==null) {
					errorCOM(com, "La DESTINATION fourni n'est pas utilisé par la GRILLE surchargé");
					return null;
				}else {			
					xpcms.setTimeCredit(infoXpcmsTab[0]);
					xpcms.setUndividedPeriod(infoXpcmsTab[1]);
					xpcms.setConnectionPrice(infoXpcmsTab[2]);
					xpcms.setCodeGL(infoXpcmsTab[3]);
					xpcms.setAnalysisCode(infoXpcmsTab[4]);
				}
			}
			listEcbRootBeans.add(xpcms);

		}

		SubscriptionInfoBME subscriptionInfoBME = createSubcriptionInfoBME(com, ecbCOM.getMigrationId());		
		if(subscriptionInfoBME==null)
			return null;		
		subscriptionInfoBME.setTargetTariffGridId(PARENT_ID);

		listEcbRootBeans.add(ecbCOM);
		listEcbRootBeans.add(subscriptionInfoBME);
		return listEcbRootBeans;
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
