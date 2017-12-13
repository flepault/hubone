package com.ericsson.hubone.tools.batch.job.transformation.mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.batch.data.ecb.Endpoint;
import com.ericsson.hubone.tools.batch.data.ecb.EndpointBME;
import com.ericsson.hubone.tools.batch.job.transformation.exception.EndpointException;
import com.ericsson.hubone.tools.report.transformation.TransformationReport;
import com.ericsson.hubone.tools.report.transformation.TransformationReportLine;

@Component
public class CliToEndpoint extends MappingConstants{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private volatile Set<String> epSet;
	
	public CliToEndpoint() {
		epSet = new HashSet<String>();
	}

	public EndpointBME createEndpointBME(Endpoint endpoint,Com com){
			
		EndpointBME endpointBME = new EndpointBME();
		endpointBME.setEndDate(endpoint.getAccountEndDate());
		endpointBME.setParentAccountName(endpoint.getAncestorAccount());		
		endpointBME.setStartDate(endpoint.getAccountStartDate());
		endpointBME.setUserName(endpoint.getUserName());
		endpointBME.setServiceId(com.getSERVICE_ID());
		
		return endpointBME;
		
		
	}
	

	public Endpoint createEndpoint(Com com) throws EndpointException{

		String userName = com.getCODE_CLIENT()+"_"+com.getSERVICE_ID();

		if(!addEP(userName)){
			Endpoint ecbEP = new Endpoint();
			ecbEP.setUserName(userName);	
			ecbEP.setNewEP(false);
			return ecbEP;
		}else {

			Endpoint ecbEP = new Endpoint();
			ecbEP.setNewEP(true);
			ecbEP.setAccountType(HierarchieClient.Endpoint.toString());
			ecbEP.setUserName(userName);
			ecbEP.setAccountStartDate(format.format(migrationHubOneStartDate));
			ecbEP.setAccountEndDate(format.format(migrationHubOneFarEndDate));
			ecbEP.setPayment_StartDate(format.format(migrationHubOneStartDate));
			ecbEP.setPayment_EndDate(format.format(migrationHubOneFarEndDate));
			ecbEP.setAccountStatus("Active");


			ecbEP.setAncestorAccount(com.getCODE_CLIENT());

			String code_client_parent = com.getCODE_CLIENT();
			while(ecbEP.getPayerAccount()==null){
				List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ? and NO_PARENT_ERROR is NULL ", code_client_parent);

				if(rows.size()!=1){
					errorEP(com,"Endpoint : Problème de détermination du PayerAccount");
					throw new EndpointException("Endpoint : Problème de détermination du PayerAccount");
				}else{

					Map<String,Object> ancestor = rows.get(0);

					if(ancestor.get("CLIENT_FACTURE").equals("Y"))
						ecbEP.setPayerAccount(ancestor.get("CODE_CLIENT").toString());
					else
						code_client_parent = ancestor.get("CODE_CLIENT_PARENT").toString();
				}
			}		


			TransformationReport.getIntance().increaseEndpoint();

			return ecbEP;
		}
	}

	private void errorEP(Com com,String str){

		TransformationReportLine trl = new TransformationReportLine("ERR-TRSF-02", com.getID_SIEBEL_LIGNE(),str);

		TransformationReport.getIntance().increaseEndpointError(trl);

	}

	private synchronized Boolean addEP(String userName){

		return epSet.add(userName);
		
	}

}
