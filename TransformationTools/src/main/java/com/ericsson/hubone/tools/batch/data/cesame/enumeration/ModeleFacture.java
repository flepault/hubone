package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum ModeleFacture {
	
	Produit("produit"),
	Client("client"),
	Administration("administration"),
	IntercoVoixEntrant("interco-voix-entrant"),
	WifiRoaming("wifi-roaming"),
	WifiRoamingEn("wifi-roaming_en"),
	WifiRoamingFr("wifi-roaming_fr");


	private String name = "";

	ModeleFacture(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}
	
	public static boolean match(String value){

		for(ModeleFacture o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}

}
