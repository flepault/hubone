package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
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

	private Subscription createSouscription(Com com){
		Subscription subscription = new Subscription();
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
			return errorCOM(com,e.getMessage());
		}

		subscription.setEndDate("");
		subscription.setCommercialProdCode(com.getCODE_PRODUIT_SIEBEL());

		subscription.setCatalogPrice(com.getPRIX_CATALOGUE());
		subscription.setClientCommandRef(com.getCODE_CLIENT());
		subscription.setCommandId(com.getCODE_COMMANDE());
		subscription.setDiscountAmount(com.getMONTANT_REMISE_MANUEL());
		subscription.setDiscountPercent(com.getPOURCENT_REMISE_MANUEL());
		//ecbCOM.setIsOnDemand(com.get);

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

		Subscription ecbCOM = createSouscription(com);
		ecbCOM.setProductOfferingId("PONCT");


		return ecbCOM;
	}

	public Subscription createAbonnement(Com com){

		Subscription ecbCOM = createSouscription(com);
		ecbCOM.setProductOfferingId("REC");
		//ecbCOM.setSharedBucketScope(sharedBucketScope);

		return ecbCOM;
	}

	public Subscription createGrilleTarifaire(Com com){

		Subscription ecbCOM = createSouscription(com);
		ecbCOM.setProductOfferingId("TETRA0001U");

		//IF IsSharedTariffGrid TRUE -> use TargetTariffGridId ELSE EP
		//ecbCOM.setIsSharedTariffGrid(com.get);		
		//ecbCOM.setTargetTariffGridId(targetTariffGridId);

		return ecbCOM;
	}

	public Subscription createForfaitLigne(Com com){
		return new Subscription();
	}

	public Subscription createForfaitPartage(Com com){
		return new Subscription();
	}

	public Subscription createSurcharge(Com com){
		return new Subscription();
	}

	public Subscription createRemisePiedDePage(Com com){
		return new Subscription();
	}

	public Subscription createRemiseVolume(Com com){
		return new Subscription();
	}

	private Subscription errorCOM(Com com,String str){

		TransformationReportLine trl = new TransformationReportLine("ERR-TRSF-03", com.getID_SIEBEL_LIGNE(),str);

		TransformationReport.getIntance().increaseSouscriptionError(trl);

		return null;
	}


}
