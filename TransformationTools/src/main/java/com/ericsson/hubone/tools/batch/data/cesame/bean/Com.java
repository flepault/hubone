package com.ericsson.hubone.tools.batch.data.cesame.bean;

import java.io.Serializable;

public class Com extends CesameRootBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725223833935135247L;	
	
	public static String[] column = new String[] { "ID_SIEBEL_LIGNE","FILE_TYPE","ID_SIEBEL_PRESTATION","DATE_APPLI_FACTU","DATE_MODIFICATION",
			"CODE_PRODUIT_SIEBEL","CODE_PRODUIT_RAFAEL",
			"MEDIA", "INTEGRATION_ID", "CODE_COMMANDE", "ROW_ID_SIEBEL_CLIENT","CODE_CLIENT",
    		"SERVICE_ID","QUANTITE_PRODUIT","PRIX_APPLIQUE_MANUEL",
    		"PRIX_CATALOGUE", "MONTANT_REMISE_MANUEL","POURCENT_REMISE_MANUEL",
    		"REF_COMMANDE_CLIENT","ADRESSE_ID","STATUT_COMMANDE","DATE_VALID_CMD","PARAM_PRODUIT_ADD"};
	
	public CesameBeanField ID_SIEBEL_LIGNE;
	public CesameBeanField FILE_TYPE;
	public CesameBeanField ID_SIEBEL_PRESTATION;
	public CesameBeanField DATE_APPLI_FACTU;
	public CesameBeanField DATE_MODIFICATION;
	
	public CesameBeanField CODE_PRODUIT_SIEBEL;
	public CesameBeanField CODE_PRODUIT_RAFAEL;	
	
	public CesameBeanField MEDIA;
	public CesameBeanField INTEGRATION_ID;
	public CesameBeanField CODE_COMMANDE;
	public CesameBeanField ROW_ID_SIEBEL_CLIENT;
	public CesameBeanField CODE_CLIENT;
	
	public CesameBeanField SERVICE_ID;
	public CesameBeanField QUANTITE_PRODUIT;
	public CesameBeanField PRIX_APPLIQUE_MANUEL;
	
	public CesameBeanField PRIX_CATALOGUE;
	public CesameBeanField MONTANT_REMISE_MANUEL;	
	public CesameBeanField POURCENT_REMISE_MANUEL;
	
	public CesameBeanField REF_COMMANDE_CLIENT;	
	public CesameBeanField ADRESSE_ID;
	public CesameBeanField STATUT_COMMANDE;	
	public CesameBeanField DATE_VALID_CMD;
	public CesameBeanField PARAM_PRODUIT_ADD;
	
	public String getID_SIEBEL_LIGNE() {
		return ID_SIEBEL_LIGNE.getValue();
	}
	public void setID_SIEBEL_LIGNE(String ID_SIEBEL_LIGNE) {
		this.ID_SIEBEL_LIGNE = new CesameBeanField(ID_SIEBEL_LIGNE, true, new String[]{}, "STRING15");
	}
	public String getFILE_TYPE() {
		return FILE_TYPE.getValue();
	}
	public void setFILE_TYPE(String FILE_TYPE) {
		this.FILE_TYPE = new CesameBeanField(FILE_TYPE, true, new String[]{}, "STRING30");
	}
	public String getID_SIEBEL_PRESTATION() {
		return ID_SIEBEL_PRESTATION.getValue();
	}
	public void setID_SIEBEL_PRESTATION(String ID_SIEBEL_PRESTATION) {
		this.ID_SIEBEL_PRESTATION = new CesameBeanField(ID_SIEBEL_PRESTATION, true, new String[]{}, "STRING15");
	}
	public String getDATE_APPLI_FACTU() {
		return DATE_APPLI_FACTU.getValue();
	}
	public void setDATE_APPLI_FACTU(String DATE_APPLI_FACTU) {
		this.DATE_APPLI_FACTU = new CesameBeanField(DATE_APPLI_FACTU, true, new String[]{}, "DATE");
	}
	public String getDATE_MODIFICATION() {
		return DATE_MODIFICATION.getValue();
	}
	public void setDATE_MODIFICATION(String DATE_MODIFICATION) {
		this.DATE_MODIFICATION = new CesameBeanField(DATE_MODIFICATION, true, new String[]{}, "DATE");
	}
	public String getCODE_PRODUIT_SIEBEL() {
		return CODE_PRODUIT_SIEBEL.getValue();
	}
	public void setCODE_PRODUIT_SIEBEL(String CODE_PRODUIT_SIEBEL) {
		this.CODE_PRODUIT_SIEBEL = new CesameBeanField(CODE_PRODUIT_SIEBEL, true, new String[]{}, "STRING20");
	}
	public String getCODE_PRODUIT_RAFAEL() {
		return CODE_PRODUIT_RAFAEL.getValue();
	}
	public void setCODE_PRODUIT_RAFAEL(String CODE_PRODUIT_RAFAEL) {
		this.CODE_PRODUIT_RAFAEL = new CesameBeanField(CODE_PRODUIT_RAFAEL, true, new String[]{}, "STRING20");
	}
	public String getMEDIA() {
		return MEDIA.getValue();
	}	
	public void setMEDIA(String MEDIA) {
		this.MEDIA = new CesameBeanField(MEDIA, false, new String[]{}, "STRING20");
	}
	public String getINTEGRATION_ID() {
		return INTEGRATION_ID.getValue();
	}
	public void setINTEGRATION_ID(String INTEGRATION_ID) {
		this.INTEGRATION_ID = new CesameBeanField(INTEGRATION_ID, true, new String[]{}, "STRING15");
	}
	public String getCODE_COMMANDE() {
		return CODE_COMMANDE.getValue();
	}
	public void setCODE_COMMANDE(String CODE_COMMANDE) {
		this.CODE_COMMANDE = new CesameBeanField(CODE_COMMANDE, true, new String[]{}, "STRING15");
	}
	public String getROW_ID_SIEBEL_CLIENT() {
		return ROW_ID_SIEBEL_CLIENT.getValue();
	}
	public void setROW_ID_SIEBEL_CLIENT(String ROW_ID_SIEBEL_CLIENT) {
		this.ROW_ID_SIEBEL_CLIENT = new CesameBeanField(ROW_ID_SIEBEL_CLIENT, true, new String[]{}, "STRING15");
	}
	public String getCODE_CLIENT() {
		return CODE_CLIENT.getValue();
	}
	public void setCODE_CLIENT(String CODE_CLIENT) {
		this.CODE_CLIENT = new CesameBeanField(CODE_CLIENT, true, new String[]{}, "STRING30");
	}
	public String getSERVICE_ID() {
		return SERVICE_ID.getValue();
	}
	public void setSERVICE_ID(String SERVICE_ID) {
		this.SERVICE_ID = new CesameBeanField(SERVICE_ID, false, new String[]{"RG19"}, "STRING16");
	}
	public String getQUANTITE_PRODUIT() {
		return QUANTITE_PRODUIT.getValue();
	}
	public void setQUANTITE_PRODUIT(String QUANTITE_PRODUIT) {
		this.QUANTITE_PRODUIT = new CesameBeanField(QUANTITE_PRODUIT, true, new String[]{"RG15"},null);
	}
	public String getPRIX_APPLIQUE_MANUEL() {
		return PRIX_APPLIQUE_MANUEL.getValue();
	}
	public void setPRIX_APPLIQUE_MANUEL(String PRIX_APPLIQUE_MANUEL) {
		this.PRIX_APPLIQUE_MANUEL = new CesameBeanField(PRIX_APPLIQUE_MANUEL, false, new String[]{"RG16"}, null);
	}
	public String getPRIX_CATALOGUE() {
		return PRIX_CATALOGUE.getValue();
	}
	public void setPRIX_CATALOGUE(String PRIX_CATALOGUE) {
		this.PRIX_CATALOGUE = new CesameBeanField(PRIX_CATALOGUE, false, new String[]{"RG16"}, null);
	}
	public String getMONTANT_REMISE_MANUEL() {
		return MONTANT_REMISE_MANUEL.getValue();
	}
	public void setMONTANT_REMISE_MANUEL(String MONTANT_REMISE_MANUEL) {
		this.MONTANT_REMISE_MANUEL = new CesameBeanField(MONTANT_REMISE_MANUEL, false, new String[]{"RG16"}, null);
	}
	public String getPOURCENT_REMISE_MANUEL() {
		return POURCENT_REMISE_MANUEL.getValue();
	}
	public void setPOURCENT_REMISE_MANUEL(String POURCENT_REMISE_MANUEL) {
		this.POURCENT_REMISE_MANUEL = new CesameBeanField(POURCENT_REMISE_MANUEL, false, new String[]{"RG17"}, null);
	}
	public String getREF_COMMANDE_CLIENT() {
		return REF_COMMANDE_CLIENT.getValue();
	}
	public void setREF_COMMANDE_CLIENT(String REF_COMMANDE_CLIENT) {
		this.REF_COMMANDE_CLIENT = new CesameBeanField(REF_COMMANDE_CLIENT, false, new String[]{}, "STRING30");
	}
	public String getADRESSE_ID() {
		return ADRESSE_ID.getValue();
	}
	public void setADRESSE_ID(String ARESSE_ID) {
		this.ADRESSE_ID = new CesameBeanField(ARESSE_ID, false, new String[]{}, "STRING15");
	}
	public String getSTATUT_COMMANDE() {
		return STATUT_COMMANDE.getValue();
	}
	public void setSTATUT_COMMANDE(String STATUT_COMMANDE) {
		this.STATUT_COMMANDE = new CesameBeanField(STATUT_COMMANDE, false, new String[]{}, "STRING30");
	}
	public String getDATE_VALID_CMD() {
		return DATE_VALID_CMD.getValue();
	}
	public void setDATE_VALID_CMD(String DATE_VALID_CMD) {
		this.DATE_VALID_CMD = new CesameBeanField(DATE_VALID_CMD, false, new String[]{}, "DATE");
	}
	public String getPARAM_PRODUIT_ADD() {
		return PARAM_PRODUIT_ADD.getValue();
	}
	public void setPARAM_PRODUIT_ADD(String PARAM_PRODUIT_ADD) {
		this.PARAM_PRODUIT_ADD = new CesameBeanField(PARAM_PRODUIT_ADD, false, new String[]{}, "STRING1000");
	}
	
	

}
