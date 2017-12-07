package com.ericsson.hubone.tools.batch.data.cesame.bean;

import java.io.Serializable;

public class Cli extends CesameRootBean implements Serializable {	
	
	private static final long serialVersionUID = 3574198022713649629L;

	public Cli() {
	}
	
	public Object[] toSmallObjectArray() throws IllegalArgumentException, IllegalAccessException, SecurityException{
		
		Object[] o = new Object[]{this.ROW_ID_SIEBEL.getValue(),this.CODE_CLIENT.getValue(),this.NOM_CLIENT.getValue(),this.STATUT_CRM.getValue(),
				this.ROLE_CLIENT.getValue(),this.NIV_HIERARCHIE_CLIENT.getValue(),this.NOM_GROUPE.getValue(), this.ID_GROUPE.getValue(),
				this.NOM_CLIENT_PARENT.getValue(), this.CODE_CLIENT_PARENT.getValue(),this.CLIENT_FACTURE.getValue(),this.A_CONTROLLER.getValue(),
				this.GROUPE_FACTURATION.getValue()};
		
		return o; 
	}

	public static String[] column = new String[] { "ROW_ID_SIEBEL","FILE_TYPE","CODE_CLIENT","NOM_CLIENT","STATUT_CRM","ROLE_CLIENT","NIV_HIERARCHIE_CLIENT",
			"NOM_GROUPE", "ID_GROUPE", "NOM_CLIENT_PARENT", "CODE_CLIENT_PARENT",
    		"NO_TVA_INTRA","CODE_APE","CLIENT_FACTURE",
    		"ID_ADRESSE", "ID_ADRESSE_EXP",
			"CODE_CC","EXEMPTION_TVA","FREQ_CYCLE_FACTU","MODELE_FACTURE","JUSTIF_DET_COM","SUPPORT_FACTURE",
			"DELAI_PAIEMENT","MODE_PAIEMENT","IBAN_CODE_ISO","IBAN_CODE_CTL","IBAN_CODE_COMPTE","BIC",
			"ID_INTERLOCUTEUR","ID_EMPLOYE",
			"COMMENTAIRE_FACTURE","DIFLIST_INVOICE","SIRET",
			"CODE_SERVICE_UO","CODE_MARCHE","A_CONTROLLER","GROUPE_FACTURATION"};
	
	public CesameBeanField ROW_ID_SIEBEL;
	public CesameBeanField FILE_TYPE;
	public CesameBeanField CODE_CLIENT;
	public CesameBeanField NOM_CLIENT;
	public CesameBeanField STATUT_CRM;
	public CesameBeanField ROLE_CLIENT;
	public CesameBeanField NIV_HIERARCHIE_CLIENT;	
	public CesameBeanField NOM_GROUPE;
	public CesameBeanField ID_GROUPE;
	public CesameBeanField NOM_CLIENT_PARENT;
	public CesameBeanField CODE_CLIENT_PARENT;
	public CesameBeanField NO_TVA_INTRA;
	public CesameBeanField CODE_APE;
	public CesameBeanField CLIENT_FACTURE;
	public CesameBeanField ID_ADRESSE;
	public CesameBeanField ID_ADRESSE_EXP;
	public CesameBeanField CODE_CC;	
	public CesameBeanField EXEMPTION_TVA;
	public CesameBeanField FREQ_CYCLE_FACTU;	
	public CesameBeanField MODELE_FACTURE;
	public CesameBeanField JUSTIF_DET_COM;	
	public CesameBeanField SUPPORT_FACTURE;
	public CesameBeanField DELAI_PAIEMENT;
	public CesameBeanField MODE_PAIEMENT;
	public CesameBeanField IBAN_CODE_ISO;
	public CesameBeanField IBAN_CODE_CTL;
	public CesameBeanField IBAN_CODE_COMPTE;
	public CesameBeanField BIC;	
	public CesameBeanField ID_INTERLOCUTEUR;
	public CesameBeanField ID_EMPLOYE;
	public CesameBeanField COMMENTAIRE_FACTURE;
	public CesameBeanField DIFLIST_INVOICE;
	public CesameBeanField SIRET;	
	public CesameBeanField CODE_SERVICE_UO;
	public CesameBeanField CODE_MARCHE;
	public CesameBeanField A_CONTROLLER;
	public CesameBeanField GROUPE_FACTURATION;

	public Cli(String ROW_ID_SIEBEL, String FILE_TYPE,String CODE_CLIENT, String NOM_CLIENT, String STATUT_CRM, String ROLE_CLIENT,
			String NIV_HIERARCHIE_CLIENT, String NOM_GROUPE,String ID_GROUPE,String NOM_CLIENT_PARENT,String CODE_CLIENT_PARENT, 
			String NO_TVA_INTRA, String CODE_APE,String CLIENT_FACTURE,
			String ID_ADRESSE, String ID_ADRESSE_EXP, 
			String CODE_CC, String EXEMPTION_TVA, 
			String FREQ_CYCLE_FACTU, String MODELE_FACTURE,String JUSTIF_DET_COM, String SUPPORT_FACTURE,
			String DELAI_PAIEMENT, String MODE_PAIEMENT,
			String IBAN_CODE_ISO, String IBAN_CODE_CTL, String IBAN_CODE_COMPTE, String BIC,
			String ID_INTERLOCUTEUR,String ID_EMPLOYE,
			String COMMENTAIRE_FACTURE, String DIFLIST_INVOICE, String SIRET,
			String CODE_SERVICE_UO,String CODE_MARCHE,String A_CONTROLLER,String GROUPE_FACTURATION) {
		setROW_ID_SIEBEL(ROW_ID_SIEBEL);
		setFILE_TYPE(FILE_TYPE);
		setCODE_CLIENT(CODE_CLIENT);
		setNOM_CLIENT(NOM_CLIENT);
		setSTATUT_CRM(STATUT_CRM);
		setROLE_CLIENT(ROLE_CLIENT);
		setNIV_HIERARCHIE_CLIENT(NIV_HIERARCHIE_CLIENT);
		setNOM_GROUPE(NOM_GROUPE);
		setID_GROUPE(ID_GROUPE);
		setNOM_CLIENT_PARENT(NOM_CLIENT_PARENT);
		setCODE_CLIENT_PARENT(CODE_CLIENT_PARENT);
		setNO_TVA_INTRA(NO_TVA_INTRA) ;
		setCODE_APE(CODE_APE) ;
		setCLIENT_FACTURE(CLIENT_FACTURE);
		setID_ADRESSE(ID_ADRESSE);
		setID_ADRESSE_EXP(ID_ADRESSE_EXP) ;
		setCODE_CC(CODE_CC);
		setEXEMPTION_TVA(EXEMPTION_TVA);
		setFREQ_CYCLE_FACTU(FREQ_CYCLE_FACTU);
		setMODELE_FACTURE(MODELE_FACTURE);
		setJUSTIF_DET_COM(JUSTIF_DET_COM);
		setSUPPORT_FACTURE(SUPPORT_FACTURE);
		setDELAI_PAIEMENT(DELAI_PAIEMENT);
		setMODE_PAIEMENT(MODE_PAIEMENT);
		setIBAN_CODE_ISO(IBAN_CODE_ISO);
		setIBAN_CODE_CTL(IBAN_CODE_CTL);
		setIBAN_CODE_COMPTE(IBAN_CODE_COMPTE);
		setBIC(BIC);
		setID_INTERLOCUTEUR(ID_INTERLOCUTEUR);
		setID_EMPLOYE(ID_EMPLOYE);
		setCOMMENTAIRE_FACTURE(COMMENTAIRE_FACTURE);
		setDIFLIST_INVOICE(DIFLIST_INVOICE);
		setSIRET(SIRET);
		setCODE_SERVICE_UO(CODE_SERVICE_UO);
		setCODE_MARCHE(CODE_MARCHE);
		setA_CONTROLLER(A_CONTROLLER);
		setGROUPE_FACTURATION(GROUPE_FACTURATION);
	}
	
	public String getROW_ID_SIEBEL() {
		return ROW_ID_SIEBEL.getValue();
	}
	
	public String getFILE_TYPE() {
		return FILE_TYPE.getValue();
	}

	public String getCODE_CLIENT() {
		return CODE_CLIENT.getValue();
	}

	public String getNOM_CLIENT() {
		return NOM_CLIENT.getValue();
	}

	public String getSTATUT_CRM() {
		return STATUT_CRM.getValue();
	}

	public String getROLE_CLIENT() {
		return ROLE_CLIENT.getValue();
	}

	public String getNIV_HIERARCHIE_CLIENT() {
		return NIV_HIERARCHIE_CLIENT.getValue();
	}

	public String getNOM_GROUPE() {
		return NOM_GROUPE.getValue();
	}

	public String getID_GROUPE() {
		return ID_GROUPE.getValue();
	}

	public String getNOM_CLIENT_PARENT() {
		return NOM_CLIENT_PARENT.getValue();
	}

	public String getCODE_CLIENT_PARENT() {
		return CODE_CLIENT_PARENT.getValue();
	}

	public String getNO_TVA_INTRA() {
		return NO_TVA_INTRA.getValue();
	}

	public String getCODE_APE() {
		return CODE_APE.getValue();
	}

	public String getCLIENT_FACTURE() {
		return CLIENT_FACTURE.getValue();
	}

	public String getID_ADRESSE() {
		return ID_ADRESSE.getValue();
	}

	public String getID_ADRESSE_EXP() {
		return ID_ADRESSE_EXP.getValue();
	}

	public String getCODE_CC() {
		return CODE_CC.getValue();
	}

	public String getEXEMPTION_TVA() {
		return EXEMPTION_TVA.getValue();
	}

	public String getFREQ_CYCLE_FACTU() {
		return FREQ_CYCLE_FACTU.getValue();
	}

	public String getMODELE_FACTURE() {
		return MODELE_FACTURE.getValue();
	}

	public String getJUSTIF_DET_COM() {
		return JUSTIF_DET_COM.getValue();
	}

	public String getSUPPORT_FACTURE() {
		return SUPPORT_FACTURE.getValue();
	}

	public String getDELAI_PAIEMENT() {
		return DELAI_PAIEMENT.getValue();
	}

	public String getMODE_PAIEMENT() {
		return MODE_PAIEMENT.getValue();
	}

	public String getIBAN_CODE_ISO() {
		return IBAN_CODE_ISO.getValue();
	}

	public String getIBAN_CODE_CTL() {
		return IBAN_CODE_CTL.getValue();
	}

	public String getIBAN_CODE_COMPTE() {
		return IBAN_CODE_COMPTE.getValue();
	}

	public String getBIC() {
		return BIC.getValue();
	}

	public String getID_INTERLOCUTEUR() {
		return ID_INTERLOCUTEUR.getValue();
	}
	
	public String getID_EMPLOYE() {
		return ID_EMPLOYE.getValue();
	}

	public String getCOMMENTAIRE_FACTURE() {
		return COMMENTAIRE_FACTURE.getValue();
	}

	public String getDIFLIST_INVOICE() {
		return DIFLIST_INVOICE.getValue();
	}

	public String getSIRET() {
		return SIRET.getValue();
	}

	public String getCODE_SERVICE_UO() {
		return CODE_SERVICE_UO.getValue();
	}

	public String getCODE_MARCHE() {
		return CODE_MARCHE.getValue();
	}

	public String getA_CONTROLLER() {
		return A_CONTROLLER.getValue();
	}

	public String getGROUPE_FACTURATION() {
		return GROUPE_FACTURATION.getValue();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.ROW_ID_SIEBEL.getValue();
	}

	public void setROW_ID_SIEBEL(String ROW_ID_SIEBEL) {
		this.ROW_ID_SIEBEL = new CesameBeanField(ROW_ID_SIEBEL,true,new String[]{},"STRING15");
	}
	
	public void setFILE_TYPE(String FILE_TYPE) {
		this.FILE_TYPE = new CesameBeanField(FILE_TYPE,true,new String[]{},"STRING30");
	}
	
	public void setCODE_CLIENT(String CODE_CLIENT) {
		this.CODE_CLIENT = new CesameBeanField(CODE_CLIENT,true,new String[]{},"STRING30");
	}

	public void setNOM_CLIENT(String NOM_CLIENT) {
		this.NOM_CLIENT = new CesameBeanField(NOM_CLIENT,true,new String[]{},"STRING100");
	}

	public void setSTATUT_CRM(String STATUT_CRM) {
		this.STATUT_CRM = new CesameBeanField(STATUT_CRM,true,new String[]{},"STRING15");
	}

	public void setROLE_CLIENT(String ROLE_CLIENT) {
		this.ROLE_CLIENT = new CesameBeanField(ROLE_CLIENT,true,new String[]{"RG2"},"STRING15");
	}

	public void setNIV_HIERARCHIE_CLIENT(String NIV_HIERARCHIE_CLIENT) {
		this.NIV_HIERARCHIE_CLIENT = new CesameBeanField(NIV_HIERARCHIE_CLIENT,true,new String[]{"RG3"},"STRING20");
	}
	
	public void setNOM_GROUPE(String NOM_GROUPE) {
		this.NOM_GROUPE = new CesameBeanField(NOM_GROUPE,false,new String[]{},"STRING100");
	}

	public void setID_GROUPE(String ID_GROUPE) {
		this.ID_GROUPE = new CesameBeanField(ID_GROUPE,false,new String[]{},"STRING15");
	}

	public void setNOM_CLIENT_PARENT(String NOM_CLIENT_PARENT) {
		this.NOM_CLIENT_PARENT = new CesameBeanField(NOM_CLIENT_PARENT,false,new String[]{},"STRING100");
	}

	public void setCODE_CLIENT_PARENT(String CODE_CLIENT_PARENT) {
		this.CODE_CLIENT_PARENT = new CesameBeanField(CODE_CLIENT_PARENT,false,new String[]{"RG18"},"STRING30");
	}

	public void setNO_TVA_INTRA(String NO_TVA_INTRA) {
		this.NO_TVA_INTRA = new CesameBeanField(NO_TVA_INTRA,false,new String[]{"RG4"},"STRING30");
	}

	public void setCODE_APE(String CODE_APE) {
		this.CODE_APE = new CesameBeanField(CODE_APE,false,new String[]{},"STRING50");
	}
	
	public void setCLIENT_FACTURE(String CLIENT_FACTURE) {
		this.CLIENT_FACTURE = new CesameBeanField(CLIENT_FACTURE,true,new String[]{"RG7"},"STRING1");
	}
	
	public void setID_ADRESSE(String ID_ADRESSE) {
		this.ID_ADRESSE = new CesameBeanField(ID_ADRESSE,true,new String[]{},"STRING15");
	}

	public void setID_ADRESSE_EXP(String ID_ADRESSE_EXP) {
		this.ID_ADRESSE_EXP = new CesameBeanField(ID_ADRESSE_EXP,true,new String[]{},"STRING15");
	}
	
	public void setCODE_CC(String CODE_CC) {
		this.CODE_CC = new CesameBeanField(CODE_CC,false,new String[]{},"STRING30");
	}

	public void setEXEMPTION_TVA(String EXEMPTION_TVA) {
		this.EXEMPTION_TVA = new CesameBeanField(EXEMPTION_TVA,false,new String[]{"RG6","RG7"},"STRING1");
	}

	public void setFREQ_CYCLE_FACTU(String FREQ_CYCLE_FACTU) {
		this.FREQ_CYCLE_FACTU = new CesameBeanField(FREQ_CYCLE_FACTU,false,new String[]{"RG6","RG8"},"STRING30");
	}

	public void setMODELE_FACTURE(String MODELE_FACTURE) {
		this.MODELE_FACTURE = new CesameBeanField(MODELE_FACTURE,false,new String[]{"RG6","RG9"},"STRING30");
	}

	public void setJUSTIF_DET_COM(String JUSTIF_DET_COM) {
		this.JUSTIF_DET_COM = new CesameBeanField(JUSTIF_DET_COM,false,new String[]{"RG6","RG7"},"STRING1");
	}

	public void setSUPPORT_FACTURE(String SUPPORT_FACTURE) {
		this.SUPPORT_FACTURE = new CesameBeanField(SUPPORT_FACTURE,false,new String[]{"RG6","RG10"},"STRING30");
	}

	public void setDELAI_PAIEMENT(String DELAI_PAIEMENT) {
		this.DELAI_PAIEMENT = new CesameBeanField(DELAI_PAIEMENT,false,new String[]{"RG6","RG11"},"STRING30");
	}

	public void setMODE_PAIEMENT(String MODE_PAIEMENT) {
		this.MODE_PAIEMENT = new CesameBeanField(MODE_PAIEMENT,false,new String[]{"RG6","RG12"},"STRING30");
	}

	public void setIBAN_CODE_ISO(String IBAN_CODE_ISO) {
		this.IBAN_CODE_ISO = new CesameBeanField(IBAN_CODE_ISO,false,new String[]{"RG13"},"STRING2");
	}

	public void setIBAN_CODE_CTL(String IBAN_CODE_CTL) {
		this.IBAN_CODE_CTL = new CesameBeanField(IBAN_CODE_CTL,false,new String[]{"RG13"},"STRING2");
	}

	public void setIBAN_CODE_COMPTE(String IBAN_CODE_COMPTE) {
		this.IBAN_CODE_COMPTE = new CesameBeanField(IBAN_CODE_COMPTE,false,new String[]{"RG13"},"STRING31");
	}

	public void setBIC(String BIC) {
		this.BIC = new CesameBeanField(BIC,false,new String[]{"RG13"},"STRING11");
	}

	public void setID_INTERLOCUTEUR(String ID_INTERLOCUTEUR) {
		this.ID_INTERLOCUTEUR = new CesameBeanField(ID_INTERLOCUTEUR,false,new String[]{},"STRING15");
	}
	
	public void setID_EMPLOYE(String ID_EMPLOYE) {
		this.ID_EMPLOYE = new CesameBeanField(ID_EMPLOYE,false,new String[]{"RG6"},"STRING15");
	}

	public void setCOMMENTAIRE_FACTURE(String COMMENTAIRE_FACTURE) {
		this.COMMENTAIRE_FACTURE = new CesameBeanField(COMMENTAIRE_FACTURE,false,new String[]{},"STRING100");
	}

	public void setDIFLIST_INVOICE(String DIFLIST_INVOICE) {
		this.DIFLIST_INVOICE = new CesameBeanField(DIFLIST_INVOICE,false,new String[]{"RG14"},"STRING150");
	}

	public void setSIRET(String SIRET) {
		this.SIRET = new CesameBeanField(SIRET,false,new String[]{"RG4"},"STRING20");
	}

	public void setCODE_SERVICE_UO(String CODE_SERVICE_UO) {
		this.CODE_SERVICE_UO = new CesameBeanField(CODE_SERVICE_UO,false,new String[]{"RG4"},"STRING15");
	}

	public void setCODE_MARCHE(String CODE_MARCHE) {
		this.CODE_MARCHE = new CesameBeanField(CODE_MARCHE,false,new String[]{"RG4"},"STRING15");
	}

	public void setA_CONTROLLER(String A_CONTROLLER) {
		this.A_CONTROLLER = new CesameBeanField(A_CONTROLLER,false,new String[]{"RG7"},"STRING1");
	}

	public void setGROUPE_FACTURATION(String GROUPE_FACTURATION) {
		this.GROUPE_FACTURATION = new CesameBeanField(GROUPE_FACTURATION,true,new String[]{},"STRING30");
	}	
	
}
