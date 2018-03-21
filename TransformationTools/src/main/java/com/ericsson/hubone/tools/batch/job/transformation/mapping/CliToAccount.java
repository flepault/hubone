package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.CycleFacturation;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.batch.data.ecb.Account;
import com.ericsson.hubone.tools.batch.data.ecb.SimpleSubscription;
import com.ericsson.hubone.tools.batch.data.ecb.Subscription;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReportLine;

@Component
public class CliToAccount extends MappingConstants{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Subscription createMinFactSouscription(Cli cli){

		Subscription subscription = new SimpleSubscription();

		subscription.setAccountId(cli.getCODE_CLIENT());
		subscription.setStartDate(format.format(migrationHubOneSubscriptionStartDate));
		subscription.setNewCOM(false);
		subscription.setEndDate("");
		subscription.setCommercialProdCode("");
		subscription.setProductOfferingId("MINFAC");
		subscription.setMigrationId("");		

		TransformationReport.getIntance().increaseSouscription();

		return subscription;
	}
	

	public Account createAccount(Cli cli){		

		Account account = new Account();
		account.setAccountType(cli.getNIV_HIERARCHIE_CLIENT());
		account.setUserName(cli.getCODE_CLIENT());
		account.setAccountStartDate(format.format(migrationHubOneAccountStartDate));
		account.setAccountEndDate(format.format(migrationHubOneFarEndDate));
		account.setPayment_StartDate(format.format(migrationHubOneAccountStartDate));
		account.setPayment_EndDate(format.format(migrationHubOneFarEndDate));
		account.setAccountStatus("Active");

		if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){
			account.setAncestorAccount("HubOne");
		}else{
			account.setAncestorAccount(cli.getCODE_CLIENT_PARENT());
		}

		account.setPayerAccount(determinePayerAccount(cli));
		
		
		if(cli.getFREQ_CYCLE_FACTU().equals(CycleFacturation.Trimestriel.toString())) {
			account.setStartDay("1");
			account.setFirstDayOfMonth("1");
			account.setSecondDayOfMonth("2");
			account.setStartMonth("1");
		}else if(cli.getFREQ_CYCLE_FACTU().equals(CycleFacturation.Annuel.toString())) {
			account.setStartDay("1");
			account.setStartMonth("1");
			account.setStartYear("2001");
		}else if(cli.getFREQ_CYCLE_FACTU().equals(CycleFacturation.Semestriel.toString())) {
			account.setStartDay("1");
			account.setStartMonth("1");
		}
		

		account.setInternal_Billable(cli.getCLIENT_FACTURE());
		account.setInternal_TaxExempt(cli.getEXEMPTION_TVA());
		account.setInternal_UsageCycleType(cli.getFREQ_CYCLE_FACTU());
		
		account.setInternal_MetraTaxHasOverrideBand(cli.getEXEMPTION_TVA());
		if(cli.getEXEMPTION_TVA().equals("Y"))
			account.setInternal_MetraTaxOverrideBand("exempt");		
		else
			account.setInternal_MetraTaxOverrideBand("STD");	
		
		account.setBilling_InvoiceTemplate(cli.getMODELE_FACTURE());
		account.setBilling_InvoiceSupport(cli.getSUPPORT_FACTURE());
		account.setBilling_BillingGroupName(cli.getGROUPE_FACTURATION());
		account.setBilling_IsToCheckInvoice(cli.getA_CONTROLLER());
		account.setBilling_HasJustifDetCom(cli.getJUSTIF_DET_COM());
		account.setBilling_PaymentDelay(cli.getDELAI_PAIEMENT());
		account.setBilling_PaymentMode(cli.getMODE_PAIEMENT());
		account.setBilling_IbanCodeIso(cli.getIBAN_CODE_ISO());
		account.setBilling_IbanCodeCtl(cli.getIBAN_CODE_CTL());
		account.setBilling_IbanCodeCompte(cli.getIBAN_CODE_COMPTE());
		account.setBilling_Bic(cli.getBIC());
		account.setCommon_BillComment(cli.getCOMMENTAIRE_FACTURE());
		account.setContact_Email(cli.getDIFLIST_INVOICE());
		account.setContact_AccountName(cli.getNOM_CLIENT());
		account.setContact_BillingAddressID(cli.getID_ADRESSE());
		account.setContact_ShipmentAddressID(cli.getID_ADRESSE_EXP());
		account.setContact_BillingContactID(cli.getID_INTERLOCUTEUR());
		account.setContact_CommercialContactID(cli.getID_EMPLOYE());
		account.setCommon_RowIdSiebel(cli.getROW_ID_SIEBEL());
		account.setCommon_CrmStatus(cli.getSTATUT_CRM());
		account.setCommon_CustomerRole(cli.getROLE_CLIENT());
		account.setCommon_GroupName(cli.getNOM_GROUPE());
		account.setCommon_TvaIntra(cli.getNO_TVA_INTRA());
		account.setCommon_CodeApe(cli.getCODE_APE());
		account.setCommon_Organization("TELECOM");
		account.setCommon_Siret(cli.getSIRET());
		account.setCommon_CodeService(cli.getCODE_SERVICE_UO());
		account.setCommon_CodeCC(cli.getCODE_CC());
		
		account.setCommon_ClientRootId(determineClientRootId(cli));
		
		account.setCommon_GroupId(cli.getID_GROUPE());
		account.setCommon_ClientParentName(cli.getNOM_CLIENT_PARENT());
		account.setCommon_CodeMarche(cli.getCODE_MARCHE());


		if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){
			TransformationReport.getIntance().increaseClient();
		}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
			TransformationReport.getIntance().increaseRegroupCF();
		}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
			TransformationReport.getIntance().increaseCF();
		}else
			System.err.println("Probleme :" + cli.getROW_ID_SIEBEL());
		
		return account;
	}
	
	private String determinePayerAccount(Cli cli){
		if(cli.getCLIENT_FACTURE().equals("Y")){				
			return cli.getCODE_CLIENT();
		}else{
			
			String payerAccount = null;

			String code_client_parent = cli.getCODE_CLIENT_PARENT();

			if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
				List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ?", code_client_parent);

				//TODO gestion exception
				if(rows.size()!=1){
					errorCLI(cli,"Niveau CF : Problème de détermination du PayerAccount");
					return null;
				}else{

					Map<String,Object> ancestor = rows.get(0);

					if(ancestor.get("CLIENT_FACTURE").equals("Y"))
						payerAccount = ancestor.get("CODE_CLIENT").toString();
					else
						code_client_parent = ancestor.get("CODE_CLIENT_PARENT").toString();
				}
			}

			if(payerAccount==null){

				List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ?", code_client_parent);

				if(rows.size()!=1){
					errorCLI(cli,"Niveau supérieur au CF : Problème de détermination du PayerAccount");
					return null;
				}else{

					Map<String,Object> ancestor = rows.get(0);

					if(ancestor.get("CLIENT_FACTURE").equals("Y"))
						return ancestor.get("CODE_CLIENT").toString();
				}
			}

			return payerAccount;			
		}
	}
	
	private String determineClientRootId(Cli cli){
		if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){
			return cli.getCODE_CLIENT();
		}else{
			String code_client_parent = cli.getCODE_CLIENT_PARENT();
			if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
				List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ?", code_client_parent);

				//TODO gestion exception
				if(rows.size()!=1){
					errorCLI(cli,"Niveau CF : Problème de détermination du ClientRootId");
					return null;
				}else{

					Map<String,Object> ancestor = rows.get(0);

					code_client_parent = ancestor.get("CODE_CLIENT_PARENT").toString();
					if(code_client_parent==null || code_client_parent.equals(""))
						code_client_parent = ancestor.get("CODE_CLIENT").toString();
				}
			}
			return code_client_parent;
		}
	}

	private void errorCLI(Cli cli,String str){

		TransformationReportLine trl = new TransformationReportLine("ERR-TRSF-01", cli.getROW_ID_SIEBEL(),str);

		if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){
			TransformationReport.getIntance().increaseClientError(trl);
		}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
			TransformationReport.getIntance().increaseRegroupCFError(trl);
		}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
			TransformationReport.getIntance().increaseCFError(trl);;
		}
	}





}
