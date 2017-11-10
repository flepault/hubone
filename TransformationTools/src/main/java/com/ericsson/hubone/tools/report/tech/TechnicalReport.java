package com.ericsson.hubone.tools.report.tech;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.hubone.tools.report.Report;

public class TechnicalReport extends Report{

	private static final Logger log = LoggerFactory.getLogger(TechnicalReport.class);

	private static TechnicalReport instance;

	public static TechnicalReport getIntance(){
		if(instance==null)
			instance = new TechnicalReport();
		return instance;
	}

	private TechnicalReport() {

		super();
		errorList = new ArrayList<TechnicalReportLine>(); 
		
		errorTotalRules = 0;	
		errorTotalType = 0;	
		errorTotalMandatory = 0;	
		errorTotalFile = 0;

		errorMandatoryMap = new HashMap<String,Integer>();
		errorTypeMap = new HashMap<String,Integer>();	
		errorRulesMap = new HashMap<String,Map<String,Integer>>();
	}

	Integer errorTotalFile;
	Integer errorTotalRules;
	Integer errorTotalType;
	Integer errorTotalMandatory;
	
	

	Map<String,Map<String,Integer>> errorRulesMap ;

	Map<String,Integer> errorMandatoryMap;

	List<TechnicalReportLine> errorList;

	Map<String,Integer> errorTypeMap ;

	public void increaseRulesError(String str, TechnicalReportLine technicalReportLine){
		errorTotalRules++;
		increaseError(str,technicalReportLine);
	}

	private void increaseError(String str, TechnicalReportLine technicalReportLine){
		if(!errorRulesMap.containsKey(technicalReportLine.getField())){
			errorRulesMap.put(technicalReportLine.getField(),new HashMap<String,Integer>());
			errorRulesMap.get(technicalReportLine.getField()).put(str, 0);
		}

		if(!errorRulesMap.get(technicalReportLine.getField()).containsKey(str)){
			errorRulesMap.get(technicalReportLine.getField()).put(str, 0);
		}

		errorRulesMap.get(technicalReportLine.getField()).put(str, errorRulesMap.get(technicalReportLine.getField()).get(str)+1);

		errorList.add(technicalReportLine);						
	}
	
	public void increaseFileError(TechnicalReportLine technicalReportLine){
		errorTotalFile++;
		increaseError("Fichiers",technicalReportLine);
	}

	public void increaseTypeError(String str,TechnicalReportLine technicalReportLine){
		errorTotalType++;
		increaseError(str,technicalReportLine);
	}

	public void increaseMandatoryError(TechnicalReportLine technicalReportLine){
		errorTotalMandatory++;
		increaseError("Obligatoire",technicalReportLine);				
	}

	public void printReport(){

		log.info("NB Erreur total noms de fichiers :"+errorTotalFile);
		log.info("NB Erreur total règles de gestion :"+errorTotalRules);
		log.info("NB Erreur total typage :"+errorTotalType);
		log.info("NB Erreur total champs obligatoire :"+errorTotalMandatory);
		log.info("NB CLI en entrée :"+ cli);
		log.info("NB COM en entrée :"+ com);	
		log.info("NB CLI OK :"+ cliOK);
		log.info("NB CLI KO :"+ cliKO);	
		log.info("NB COM OK :"+ comOK);
		log.info("NB COM KO :"+ comKO);	

		List<GeneralLine> generalLineList = new ArrayList<GeneralLine>();
		generalLineList.add(new GeneralLine("Erreur Nom de fichier", errorTotalFile));
		generalLineList.add(new GeneralLine("Erreur Règles de gestion", errorTotalRules));
		generalLineList.add(new GeneralLine("Erreur Typage", errorTotalType));
		generalLineList.add(new GeneralLine("Erreur Champs obligatoire", errorTotalMandatory));
		generalLineList.add(new GeneralLine("Nombre CLI en entrée", cli));
		generalLineList.add(new GeneralLine("Nombre COM en entrée", com));	
		generalLineList.add(new GeneralLine("Nombre CLI OK", cliOK));
		generalLineList.add(new GeneralLine("Nombre CLI KO", cliKO));	
		generalLineList.add(new GeneralLine("Nombre COM OK", comOK));
		generalLineList.add(new GeneralLine("Nombre COM KO", comKO));	

		List<DetailledRulesLine> detailledRulesErrorList = new ArrayList<DetailledRulesLine>();
		for(String field:errorRulesMap.keySet()){
			for(String rule:errorRulesMap.get(field).keySet()){
				detailledRulesErrorList.add(new DetailledRulesLine(field, rule, errorRulesMap.get(field).get(rule)));
			}
		}

		LocalDateTime  date = LocalDateTime.now();
				
		try(FileInputStream is = new FileInputStream("reports/template/TechnicalReportTemplate.xlsx")) {
			try (OutputStream os = new FileOutputStream("reports/technical/TechnicalReport"+date.format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"))+".xlsx")) {
				Context context = new Context();
				if(errorList.size()==0){						
					errorList.add(new TechnicalReportLine("", "", "", "", ""));						
				}	

				context.putVar("generallines", generalLineList);				

				context.putVar("technicalreportlines", errorList);

				if(detailledRulesErrorList.size()==0){						
					detailledRulesErrorList.add(new DetailledRulesLine("", "", 0));
				}	

				context.putVar("detailledruleslines", detailledRulesErrorList);



				JxlsHelper.getInstance().processTemplate(is, os, context);
				//JxlsHelper.getInstance().processTemplateAtCell(is, os, context,"Détaillé!A1");
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
