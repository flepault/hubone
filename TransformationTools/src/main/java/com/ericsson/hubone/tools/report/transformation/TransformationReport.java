package com.ericsson.hubone.tools.report.transformation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformationReport{

	private static final Logger log = LoggerFactory.getLogger(TransformationReport.class);

	private static TransformationReport instance;

	public static TransformationReport getIntance(){
		if(instance==null)
			instance = new TransformationReport();
		return instance;
	}

	private TransformationReport() {

		errorList = new ArrayList<TransformationReportLine>(); 

		errorTotalClient = 0;
		errorTotalRegroupCF = 0;	
		errorTotalCF = 0;	
		errorTotalEndpoint = 0;
		errorTotalSouscription = 0;	
		
		totalClient = 0;
		totalRegroupCF = 0;
		totalCF = 0;
		totalEndpoint = 0;
		totalSouscription = 0;

		
	}

	Integer errorTotalClient;
	Integer errorTotalRegroupCF;
	Integer errorTotalCF;
	Integer errorTotalEndpoint;
	Integer errorTotalSouscription;
	
	Integer totalClient;
	Integer totalRegroupCF;
	Integer totalCF;
	Integer totalEndpoint;
	Integer totalSouscription;

	List<TransformationReportLine> errorList;
	
	public void increaseClient(){
		totalClient++;
	}
	
	public void increaseRegroupCF(){
		totalRegroupCF++;
	}
	
	public void increaseCF(){
		totalCF++;
	}
	
	public void increaseEndpoint(){
		totalEndpoint++;
	}
	
	public void increaseSouscription(){
		totalSouscription++;
	}
	
	

	private void increaseError(TransformationReportLine transformationReportLine){

		errorList.add(transformationReportLine);						
	}
	
	public void increaseClientError(TransformationReportLine transformationReportLine){
		errorTotalClient++;
		increaseError(transformationReportLine);
	}
	
	
	public void increaseRegroupCFError(TransformationReportLine transformationReportLine){
		errorTotalRegroupCF++;
		increaseError(transformationReportLine);
	}

	public void increaseCFError(TransformationReportLine transformationReportLine){
		errorTotalCF++;
		increaseError(transformationReportLine);
	}
	
	public void increaseEndpointError(TransformationReportLine transformationReportLine){
		errorTotalEndpoint++;
		increaseError(transformationReportLine);
	}

	public void increaseSouscriptionError(TransformationReportLine transformationReportLine){
		errorTotalSouscription++;
		increaseError(transformationReportLine);				
	}

	public void printReport(){		
		
		log.info("Transformation Client :"+totalClient);
		log.info("Transformation Regroup CF :"+totalRegroupCF);
		log.info("Transformation CF :"+totalCF);
		log.info("Transformation Endpoint :"+totalEndpoint);
		log.info("Transformation Souscriptions :"+totalSouscription);
		log.info("NB Erreur total Client :"+errorTotalClient);
		log.info("NB Erreur total RegroupCF :"+errorTotalRegroupCF);
		log.info("NB Erreur total CF :"+errorTotalCF);
		log.info("NB Erreur total Endpoint :"+errorTotalEndpoint);
		log.info("NB Erreur total Souscriptions :"+errorTotalSouscription);

		List<GeneralLine> generalLineList = new ArrayList<GeneralLine>();
		generalLineList.add(new GeneralLine("Transformation Client", totalClient));
		generalLineList.add(new GeneralLine("Transformation Regroup CF", totalRegroupCF));
		generalLineList.add(new GeneralLine("Transformation CF", totalCF));
		generalLineList.add(new GeneralLine("Transformation Endpoint", totalEndpoint));
		generalLineList.add(new GeneralLine("Transformation Souscriptions", totalSouscription));
		generalLineList.add(new GeneralLine("Erreurs Transformation Client", errorTotalClient));
		generalLineList.add(new GeneralLine("Erreurs Transformation Regroup CF", errorTotalRegroupCF));
		generalLineList.add(new GeneralLine("Erreurs Transformation CF", errorTotalCF));
		generalLineList.add(new GeneralLine("Erreurs Transformation Endpoint", errorTotalEndpoint));
		generalLineList.add(new GeneralLine("Erreurs Transformation Souscriptions", errorTotalSouscription));	

		LocalDateTime  date = LocalDateTime.now();

		try(FileInputStream is = new FileInputStream("reports/template/TransformationReportTemplate.xlsx")) {
			try (OutputStream os = new FileOutputStream("reports/transformation/TransformationReport"+date.format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"))+".xlsx")) {
				Context context = new Context();
				if(errorList.size()==0){						
					errorList.add(new TransformationReportLine("", "", ""));						
				}	

				context.putVar("generallines", generalLineList);				

				context.putVar("transformationreportlines", errorList);

				JxlsHelper.getInstance().processTemplate(is, os, context);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
