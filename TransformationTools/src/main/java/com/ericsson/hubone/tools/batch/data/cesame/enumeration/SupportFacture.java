package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum SupportFacture {
		
	Papier("PAPIER"),
	Email("EMAIL"),
	EDI("EDI"),
	PapierEmail("PAPIER+EMAIL"),
	EDIEmail("EDI+EMAIL");

	private String name = "";

	SupportFacture(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}
	
	public static boolean match(String value){

		for(SupportFacture o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}

}
