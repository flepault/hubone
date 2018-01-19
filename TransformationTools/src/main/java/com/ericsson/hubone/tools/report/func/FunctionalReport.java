package com.ericsson.hubone.tools.report.func;

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

import com.ericsson.hubone.tools.report.tech.GeneralLine;

public class FunctionalReport{

	private static final Logger log = LoggerFactory.getLogger(FunctionalReport.class);

	private static FunctionalReport instance;

	public static FunctionalReport getIntance(){
		if(instance==null)
			instance = new FunctionalReport();
		return instance;
	}

	private FunctionalReport() {
		super();
		errorList = new ArrayList<FunctionalReportLine>(); 

		errorTotalRegroupCF = 0;	
		errorTotalCF = 0;	
		errorTotalSouscription = 0;	
		
		totalRegroupCF = 0;	
		totalCF = 0;	
		totalSouscription = 0;	
		
	}

	Integer errorTotalRegroupCF;
	Integer errorTotalCF;
	Integer errorTotalSouscription;
	
	Integer totalRegroupCF;
	Integer totalCF;
	Integer totalSouscription;

	List<FunctionalReportLine> errorList;
	
	public void increaseRegroupCF(){
		totalRegroupCF++;
	}
	
	public void increaseCF(){
		totalCF++;
	}
	
	public void increaseSouscription(){
		totalSouscription++;
	}

	private void increaseError(FunctionalReportLine functionalReportLine){

		errorList.add(functionalReportLine);						
	}
	
	public void increaseWarning(FunctionalReportLine functionalReportLine){
		increaseError(functionalReportLine);
	}
	
	public void increaseRegroupCFError(FunctionalReportLine functionalReportLine){
		errorTotalRegroupCF++;
		increaseError(functionalReportLine);
	}

	public void increaseCFError(FunctionalReportLine functionalReportLine){
		errorTotalCF++;
		increaseError(functionalReportLine);
	}

	public void increaseSouscriptionError(FunctionalReportLine functionalReportLine){
		errorTotalSouscription++;
		increaseError(functionalReportLine);				
	}

	public void printReport(){

		log.info("Validation Hiérarchique Regroup CF :"+totalRegroupCF);
		log.info("Validation Hiérarchique CF :"+totalCF);
		log.info("Validation Hiérarchique Souscriptions :"+totalSouscription);
		log.info("NB Erreur total RegroupCF :"+errorTotalRegroupCF);
		log.info("NB Erreur total CF :"+errorTotalCF);
		log.info("NB Erreur total Souscriptions :"+errorTotalSouscription);
		

		List<GeneralLine> generalLineList = new ArrayList<GeneralLine>();
		generalLineList.add(new GeneralLine("Validation Hiérarchique Regroup CF :",totalRegroupCF));
		generalLineList.add(new GeneralLine("Validation Hiérarchique CF :",totalCF));
		generalLineList.add(new GeneralLine("Validation Hiérarchique Souscriptions :",totalSouscription));
		generalLineList.add(new GeneralLine("Erreurs Regroup CF", errorTotalRegroupCF));
		generalLineList.add(new GeneralLine("Erreurs CF", errorTotalCF));
		generalLineList.add(new GeneralLine("Erreurs Souscriptions", errorTotalSouscription));

		LocalDateTime  date = LocalDateTime.now();

		try(FileInputStream is = new FileInputStream("reports/template/FunctionalReportTemplate.xlsx")) {
			try (OutputStream os = new FileOutputStream("reports/functional/FunctionalReport"+date.format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"))+".xlsx")) {
				Context context = new Context();
				if(errorList.size()==0){						
					errorList.add(new FunctionalReportLine("", "", ""));						
				}	

				context.putVar("generallines", generalLineList);				

				context.putVar("functionalreportlines", errorList);

				JxlsHelper.getInstance().processTemplate(is, os, context);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
