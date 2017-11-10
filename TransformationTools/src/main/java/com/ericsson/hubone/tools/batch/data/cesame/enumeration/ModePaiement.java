package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum ModePaiement {	
	
	VIREMENT("Virement"),
	PRELEVEMENT("Prélèvement"),
	CHEQUE("Chèque");	

	private String name = "";

	ModePaiement(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}

	public static boolean match(String value){

		for(ModePaiement o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}

}
