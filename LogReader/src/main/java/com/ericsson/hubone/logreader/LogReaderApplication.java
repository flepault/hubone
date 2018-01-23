package com.ericsson.hubone.logreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ericsson.hubone.logreader.loader.LoaderLogReader;
import com.ericsson.hubone.logreader.mapper.MapperLogReader;
import com.ericsson.hubone.logreader.report.ECBMigrationToolsReport;

@SpringBootApplication
public class LogReaderApplication {
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LogReaderApplication.class, args);
		
		MapperLogReader mapperLogReader = ctx.getBean(MapperLogReader.class);
		LoaderLogReader loaderLogReader = ctx.getBean(LoaderLogReader.class);
		
		ECBMigrationToolsReport report = ctx.getBean(ECBMigrationToolsReport.class);
		
		String path = "D:/MigrationTools/ECBDataMigration";
		
		mapperLogReader.extractAllError(path);
		
		loaderLogReader.extractAllError(path);
		report.printReport();
		
	}
}
