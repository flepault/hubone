package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum CycleFacturation {

	Mensuel("Mensuel"),
	Trimestriel("Trimestriel"),
	Annuel("Annuel"),
	Bimestriel("Bimestriel"),
	Semestriel("Semestriel");

	private String name = "";

	CycleFacturation(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}

	public static boolean match(String value){

		for(CycleFacturation o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}

}
