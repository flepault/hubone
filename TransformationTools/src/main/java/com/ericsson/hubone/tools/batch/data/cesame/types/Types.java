package com.ericsson.hubone.tools.batch.data.cesame.types;

public class Types {
	
	public static boolean STRING15(String value){
		return value==null||value.matches(".{0,15}");
	}
	
	public static boolean STRING16(String value){
		return value==null||value.matches(".{0,16}");
	}
	
	public static boolean STRING30(String value){		
		return value==null||value.matches(".{0,30}");
	}
	
	public static boolean STRING64(String value){		
		return value==null||value.matches(".{0,64}");
	}
	
	public static boolean STRING17(String value){		
		return value==null||value.matches(".{0,17}");
	}
	
	public static boolean STRING50(String value){		
		return value==null||value.matches(".{0,50}");
	}
	
	public static boolean STRING1(String value){		
		return value.matches(".{1}");
	}
	
	public static boolean STRING150(String value){		
		return value==null||value.matches(".{0,150}");
	}	
	
	public static boolean STRING2(String value){		
		return value==null||value.matches(".{0,2}");
	}
	
	public static boolean STRING31(String value){		
		return value==null||value.matches(".{0,31}");
	}
	
	public static boolean STRING11(String value){		
		return value==null||value.matches(".{0,11}");
	}
	
	public static boolean STRING100(String value){		
		return value==null||value.matches(".{0,100}");
	}
	
	
	public static boolean STRING20(String value){		
		return value==null||value.matches(".{0,20}");
	}
	
	public static boolean STRING1000(String value){		
		return value==null||value.matches(".{0,1000}");
	}
	
	public static boolean DATE(String value){		
		return value==null||value.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}");
	}
	
//	public static boolean DATE(String value){		
//	return value==null||value.matches("[0-9]{2}-[A-Z]{3}-[0-9]{2}");
//}
	
}
