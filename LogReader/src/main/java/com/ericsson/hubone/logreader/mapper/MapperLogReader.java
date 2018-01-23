package com.ericsson.hubone.logreader.mapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.logreader.report.ECBMigrationToolsReport;
import com.ericsson.hubone.logreader.report.MapperReportLine;

@Component
public class MapperLogReader {
	
	@Autowired
	ECBMigrationToolsReport report;
	
	public void extractAllError(String path) {
		
		extractAccountError(path);
		
		extractSubError(path);
		
	}
	
	private void extractAccountError(String path) {
		extract(path+"/Mapper/rejected/ClientAccount.input_rejected.csv","ERR-MAP-ACCOUNT",48);
		extract(path+"/Mapper/rejected/RegroupCFAccount.input_rejected.csv","ERR-MAP-ACCOUNT",48);
		extract(path+"/Mapper/rejected/CFAccount.input_rejected.csv","ERR-MAP-ACCOUNT",48);
		extract(path+"/Mapper/rejected/EPAccount.input_rejected.csv","ERR-MAP-ACCOUNT",48);
	}
	
	private void extractSubError(String path) {
		extract(path+"/Mapper/rejected/Subscription.Old.input_rejected.csv","ERR-MAP-SUB",15);
		extract(path+"/Mapper/rejected/Subscription.New.input_rejected.csv","ERR-MAP-SUB",15);
		extract(path+"/Mapper/rejected/GroupSubscription.Old.input_rejected.csv","ERR-MAP-SUB",15);
		extract(path+"/Mapper/rejected/GroupSubscription.New.input_rejected.csv","ERR-MAP-SUB",15);
	}
	

	private void extract(String csvFile,String errorCode, int rowIdPlace) {
        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(csvFile));
           
            //first line
            br.readLine();
            
            while ((line = br.readLine()) != null) {

            	String errorMessage = line.split("!!")[1];
                // use comma as separator
                String[] errorMessageSplit = errorMessage.split("\\.");
                
                MapperReportLine reportLine = new MapperReportLine(errorCode, csvFile, line.split("\\|")[rowIdPlace], errorMessageSplit[2].split(":")[1].split(",")[0], errorMessage);
                report.increaseMapperError(reportLine,errorMessageSplit[2].split(":")[2].split(",")[0]);

            }

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

}
