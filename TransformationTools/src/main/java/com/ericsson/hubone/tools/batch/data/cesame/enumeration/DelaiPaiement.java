package com.ericsson.hubone.tools.batch.data.cesame.enumeration;

public enum DelaiPaiement {
		
	DP_30("30"),
	DP_30F("30F"),
	DP_30M("30M"),
	DP_45("45"),
	DP_45F("45F"),
	DP_45M("45M"),
	DP_60("60"),
	DP_60F("60F"),
	DP_60M("60M"),
	DP_90("90"),
	DP_90F("90F"),
	DP_90M("90M"),;

	private String name = "";

	DelaiPaiement(String name){
		this.name = name;
	}

	public String toString(){
		return name;
	}

	public static boolean match(String value){

		for(DelaiPaiement o:values()){
			if(o.toString().equals(value))
				return true;
		}
		return false;

	}
	
}
