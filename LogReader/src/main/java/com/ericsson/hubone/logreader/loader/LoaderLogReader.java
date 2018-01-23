package com.ericsson.hubone.logreader.loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.logreader.report.ECBMigrationToolsReport;
import com.ericsson.hubone.logreader.report.LoaderReportLine;

@Component
public class LoaderLogReader {

	@Autowired
	ECBMigrationToolsReport report;

	public void extractAllError(String path) {

		extract(path+"/Loader/ECBMigrationAPILog.txt","ERR-LOAD");

	}



	private void extract(String csvFile,String errorCode) {
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null) {
				String errorMessage = null;
				if(line.contains("ERROR")) {
					errorMessage = line.split("\\[ERROR\\]")[1];
				}else if(line.contains("DEBUG") && line.contains("Error")){
					errorMessage = line.split("\\[DEBUG\\]")[1];
				}

				if(errorMessage!=null) {
					// use comma as separator
					String[] errorMessageSplit = errorMessage.split("\\|");

					LoaderReportLine reportLine = new LoaderReportLine(errorMessageSplit[0], "ECBMigrationAPILog.txt", "No PK for now", "No Field for now", errorMessage);
					report.increaseLoaderError(reportLine);

				}

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
