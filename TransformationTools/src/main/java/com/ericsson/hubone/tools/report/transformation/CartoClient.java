package com.ericsson.hubone.tools.report.transformation;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartoClient {
	
	boolean done = false;
	
	private static CartoClient instance;

	public static CartoClient getIntance(){
		if(instance==null)
			instance = new CartoClient();
		return instance;
	}
	
	private volatile Map<String,Integer> cartoStrangeAccount = new HashMap<String,Integer>();
	
	private volatile Map<String,Integer> carto = new HashMap<String,Integer>();
	
	private volatile Map<String,ArrayList<String>> accountLevel = new HashMap<String,ArrayList<String>>();
	
	public synchronized void addAccount(String account,String level) {
		carto.put(account, 0);
		
		if(accountLevel.get(level)==null)
			accountLevel.put(level, new ArrayList<String>());
		accountLevel.get(level).add(account);
	}
	
	public synchronized void addSub(String account) {
		
		if(carto.get(account)==null) {
			if(cartoStrangeAccount.get(account)==null)
			cartoStrangeAccount.put(account, 0);
			else
				cartoStrangeAccount.put(account, cartoStrangeAccount.get(account)+1);
		}else		
			carto.put(account,carto.get(account)+1);
	}
	
	public void printCarto() {
		
		if(done)
			return;
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\Carto.txt", "UTF-8");
			for(String level:accountLevel.keySet()) {
				writer.println("Niveau Hierarchique"+" : "+level);
				
				for(String account:accountLevel.get(level)) {
					if(carto.get(account)>=1000)
						writer.println(account+" : "+carto.get(account));
				}
				
				writer.println("");
				
			}	
			
			writer.println("Compte référencé uniquement dans les souscriptions");
			
			for(String account:cartoStrangeAccount.keySet()) {
				writer.println(account+" : "+cartoStrangeAccount.get(account));
			}
			
			writer.close();
			
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		
		done = true;
		
	}

}
