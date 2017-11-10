package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum HierarchieClient {

	Client("Client"),
	RegroupCF("Regroup. facturation"),
	CF("Facturation"),
	Endpoint("Endpoint");

	private String name = "";

	HierarchieClient(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}
	
	public static boolean match(String value){
		
		for(HierarchieClient h:values()){
			if(h.toString().equals(value))
				return true;
		}
		return false;
		
	}
}
