package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum RoleClient {

	Entreprise("Entreprise"),
	Operateur("Operateur"),
	Administration("Administration"),
	Partenaire("Partenaire");

	private String name = "";

	RoleClient(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}
	
	public static boolean match(String value){
		
		for(RoleClient o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}
	
}
