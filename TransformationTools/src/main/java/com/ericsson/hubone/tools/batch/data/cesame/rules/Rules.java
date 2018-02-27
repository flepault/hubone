package com.ericsson.hubone.tools.batch.data.cesame.rules;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.CycleFacturation;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.DelaiPaiement;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.ModePaiement;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.ModeleFacture;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.RoleClient;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.SupportFacture;

public class Rules {
	
	public static boolean RG1(String value,Cli cli){		
		return value.matches("[0-9]{0,6}");
	}
	
	public static boolean RG2(String value,Cli cli){
		return RoleClient.match(value);
	}
	
	public static boolean RG3(String value,Cli cli){	
		return HierarchieClient.match(value);
	}
	
	public static boolean RG4(String value,Cli cli){		
		if(cli.getROLE_CLIENT().equals(RoleClient.Administration.toString()))
			if(value == null || value.equals(""))
				return false;
		return true;
	}
	
	public static boolean RG5(String value,Cli cli){		
		return value.matches("(O|N)");
	}
	
	public static boolean RG6(String value,Cli cli){		
		if(cli.getCLIENT_FACTURE().equals("Y"))
			if(value == null || value.equals(""))
				return false;				
		return true;
	}
	
	public static boolean RG7(String value,Cli cli){
			
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return value.matches("(Y|N)");
		return true;
		
	}
	
	public static boolean RG8(String value,Cli cli){
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return CycleFacturation.match(value);
		return true;
	}
	
	public static boolean RG9(String value,Cli cli){		
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return ModeleFacture.match(value);
		return true;
	}
	
	public static boolean RG10(String value,Cli cli){		
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return SupportFacture.match(value);
		return true;
	}
	
	public static boolean RG11(String value,Cli cli){		
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return DelaiPaiement.match(value);
		return true;
	}
	
	public static boolean RG12(String value,Cli cli){	
		if(cli.getCLIENT_FACTURE().equals("Y"))
			return ModePaiement.match(value);
		return true;
	}
	
	public static boolean RG13(String value,Cli cli){	
		if(cli.getMODE_PAIEMENT().equals(ModePaiement.PRELEVEMENT.toString()))
			if(value == null || value.equals(""))
				return false;				
		return true;
	}
	
	public static boolean RG14(String value,Cli cli){		
		if(cli.getSUPPORT_FACTURE().contains(SupportFacture.Email.toString()))
			if(value == null || value.equals(""))
				return false;				
		return true;
	}
	
	public static boolean RG15(String value,Com com){		

		try{
			if(new Integer(value)>0)
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
	}

	public static boolean RG16(String value,Com com){		

		try{
			new Double(value);
		}catch(Exception e){
			return false;
		}

		return true;
	}

	public static boolean RG17(String value,Com com){		
		try{
			new Double(value);
		}catch(Exception e){
			return false;
		}

		return true;
	}
	
	public static boolean RG18(String value,Cli cli){		
		
		if(value == null || value.equals("")){
			if(cli.getNOM_CLIENT_PARENT()==null || cli.getNOM_CLIENT_PARENT().equals(""))
				return true;
			else
				return false;
		}
		return true;
	}
	
	public static boolean RG19(String value,Com com){
		
		
		if(value == null || value.equals("")){
			if(com.getMEDIA()==null || com.getMEDIA().equals(""))
				return true;
			else
				return false;
		}
		return true;

	}
	
	public static boolean RG20(String value,Cli cli){		
		if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())) {
			if(value == null)
				return false;
			else if (value.equals("Y"))
				return true;
			else
				return false;
		}else
			return true;
				
	}
	
}
