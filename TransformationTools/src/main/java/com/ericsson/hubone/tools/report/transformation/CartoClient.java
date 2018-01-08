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

	private volatile Map<String,Map<String,Integer>> carto = new HashMap<String,Map<String,Integer>>();

	private volatile Map<String,ArrayList<String>> accountLevel = new HashMap<String,ArrayList<String>>();

	public synchronized void addAccount(String account,String level) {
		carto.put(account, new HashMap<String,Integer>());

		if(accountLevel.get(level)==null)
			accountLevel.put(level, new ArrayList<String>());
		accountLevel.get(level).add(account);
	}

	public synchronized void addSub(String account,String codeProduitRafael) {

		if(carto.get(account)==null) {
			if(cartoStrangeAccount.get(account)==null)
				cartoStrangeAccount.put(account, 1);
			else
				cartoStrangeAccount.put(account, cartoStrangeAccount.get(account)+1);
		}else {	
			if(carto.get(account).get(codeProduitRafael)==null) {
				carto.get(account).put(codeProduitRafael,1);
			}else
				carto.get(account).put(codeProduitRafael,carto.get(account).get(codeProduitRafael)+1);
		}
	}

	public void printCarto() {

		if(done)
			return;

		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\Carto.txt", "UTF-8");
			//writer = new PrintWriter("C:\\Users\\efrelep\\OneDrive - Ericsson AB\\Projets\\Hub One\\Carto.txt", "UTF-8");
			for(String level:accountLevel.keySet()) {
				writer.println("Niveau Hierarchique"+" : "+level);

				for(String account:accountLevel.get(level)) {

					for(String codeProduitRafael:carto.get(account).keySet()) {
						if(carto.get(account).get(codeProduitRafael)>1000)
							writer.println(account+","+codeProduitRafael+" : "+carto.get(account).get(codeProduitRafael));


					}

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
