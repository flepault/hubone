package com.ericsson.hubone.logreader.report;

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
import org.springframework.context.annotation.Configuration;

@Configuration
public class ECBMigrationToolsReport{

	private static final Logger log = LoggerFactory.getLogger(ECBMigrationToolsReport.class);

	public ECBMigrationToolsReport() {

		super();
		mapperErrorList = new ArrayList<MapperReportLine>(); 
		loaderErrorList = new ArrayList<LoaderReportLine>(); 
		
		mapperErrorMap = new HashMap<String,Integer>();
		loaderErrorMap = new HashMap<String,Integer>();	
	}
	
	
	Map<String,Integer> mapperErrorMap;
	Map<String,Integer> loaderErrorMap;

	List<MapperReportLine> mapperErrorList;
	List<LoaderReportLine> loaderErrorList;
	
	
	Map<String,Map<String,Integer>> mapperDetailledErrorMap = new HashMap<String,Map<String,Integer>>();


	public void increaseMapperError(MapperReportLine mapperReportLine,String value){
		mapperErrorList.add(mapperReportLine);		
		
		if(mapperErrorMap.get(mapperReportLine.getErrorType())==null)
			mapperErrorMap.put(mapperReportLine.getErrorType(), 1);
		else
			mapperErrorMap.put(mapperReportLine.getErrorType(),mapperErrorMap.get(mapperReportLine.getErrorType())+1);
		
		
		if(!mapperDetailledErrorMap.containsKey(mapperReportLine.getField())){
			mapperDetailledErrorMap.put(mapperReportLine.getField(),new HashMap<String,Integer>());
			mapperDetailledErrorMap.get(mapperReportLine.getField()).put(value, 0);
		}

		if(!mapperDetailledErrorMap.get(mapperReportLine.getField()).containsKey(value)){
			mapperDetailledErrorMap.get(mapperReportLine.getField()).put(value, 0);
		}

		mapperDetailledErrorMap.get(mapperReportLine.getField()).put(value, mapperDetailledErrorMap.get(mapperReportLine.getField()).get(value)+1);

		
	}
	
	public void increaseLoaderError(LoaderReportLine loaderReportLine){
		loaderErrorList.add(loaderReportLine);
		if(loaderErrorMap.get(loaderReportLine.getErrorType())==null)
			loaderErrorMap.put(loaderReportLine.getErrorType(), 1);
		else
			loaderErrorMap.put(loaderReportLine.getErrorType(),loaderErrorMap.get(loaderReportLine.getErrorType())+1);
		
		
//		if(!loaderDetailledErrorMap.containsKey(loaderReportLine.getField())){
//			loaderDetailledErrorMap.put(loaderReportLine.getField(),new HashMap<String,Integer>());
//			loaderDetailledErrorMap.get(loaderReportLine.getField()).put(value, 0);
//		}
//
//		if(!loaderDetailledErrorMap.get(loaderReportLine.getField()).containsKey(value)){
//			loaderDetailledErrorMap.get(loaderReportLine.getField()).put(value, 0);
//		}
//
//		loaderDetailledErrorMap.get(loaderReportLine.getField()).put(value, loaderDetailledErrorMap.get(loaderReportLine.getField()).get(value)+1);
//
//		
	}
	
	
	
	public void printReport(){

		log.info("NB Erreur total Mapper :"+mapperErrorList.size());
		log.info("NB Erreur total Loader :"+loaderErrorList.size());
		

		List<GeneralLine> generalLineList = new ArrayList<GeneralLine>();
		generalLineList.add(new GeneralLine("Erreur Mapper", mapperErrorList.size()));
		generalLineList.add(new GeneralLine("Erreur Loader", loaderErrorList.size()));

		LocalDateTime  date = LocalDateTime.now();
				
		try(FileInputStream is = new FileInputStream("report/template/ECBMigrationToolsReportTemplate.xlsx")) {
			try (OutputStream os = new FileOutputStream("report/ECBMigrationToolsReport"+date.format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmm"))+".xlsx")) {
				Context context = new Context();
				if(mapperErrorList.size()==0){						
					mapperErrorList.add(new MapperReportLine("", "", "", "", ""));						
				}	
				
				if(loaderErrorList.size()==0){						
					loaderErrorList.add(new LoaderReportLine("", "", "", "", ""));						
				}	

				context.putVar("generallines", generalLineList);				

				context.putVar("mapperreportlines", mapperErrorList);
				
				context.putVar("loaderreportlines", loaderErrorList);


				List<MapperDetailledLine> mapperdetailledlines = new ArrayList<MapperDetailledLine>();
				
				for(String field:mapperDetailledErrorMap.keySet()){
					for(String value:mapperDetailledErrorMap.get(field).keySet()){
						mapperdetailledlines.add(new MapperDetailledLine(field, value, mapperDetailledErrorMap.get(field).get(value)));
					}
				}
				
				if(mapperdetailledlines.size()==0){						
					mapperdetailledlines.add(new MapperDetailledLine("", "", 0));
				}	

				context.putVar("mapperdetailledlines", mapperdetailledlines);
				
				List<LoaderDetailledLine> loaderdetailledlines = new ArrayList<LoaderDetailledLine>();
				if(loaderdetailledlines.size()==0){						
					loaderdetailledlines.add(new LoaderDetailledLine("", "", 0));
				}	

				context.putVar("mapperdetailledlines", mapperdetailledlines);
				context.putVar("loaderdetailledlines", loaderdetailledlines);
				
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
