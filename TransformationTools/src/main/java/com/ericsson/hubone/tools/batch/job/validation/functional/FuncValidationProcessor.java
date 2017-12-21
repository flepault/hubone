package com.ericsson.hubone.tools.batch.job.validation.functional;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.report.func.FunctionalReport;
import com.ericsson.hubone.tools.report.func.FunctionalReportLine;

@Component
public class FuncValidationProcessor<T extends CesameRootBean> implements ItemProcessor<T, T> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public FuncValidationProcessor() {
	}

	@Override
	public T process(T t) throws Exception {		
		return control(t);
	}

	private T control(T t){		

		boolean error = false;

		if(t instanceof Cli){

			Cli cli = (Cli)t;

			String errorCode = "";
			String errorMessage = "";

			if(!cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){

				if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
					errorCode = "ERR-FUNC-01";
					errorMessage = "Regroup CF";
				}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
					errorCode = "ERR-FUNC-02";
					errorMessage = "CF";
				}

				List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ? and NO_PARENT_ERROR is NULL and NIV_HIERARCHIE_CLIENT !=?", cli.getCODE_CLIENT_PARENT(),cli.getNIV_HIERARCHIE_CLIENT());
				if(rows.size()==1){
					if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
						FunctionalReport.getIntance().increaseRegroupCF();
					}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
						FunctionalReport.getIntance().increaseCF();
					}
					return t;
				}
					
				else if(rows.size()==0){
					error = true;	
					jdbcTemplate.update("update CLI set NO_PARENT_ERROR = 'Y' where CODE_CLIENT=?",cli.getCODE_CLIENT());
					if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
						FunctionalReport.getIntance().increaseRegroupCFError(new FunctionalReportLine(errorCode, cli.getROW_ID_SIEBEL(), "Le compte parent "+cli.getCODE_CLIENT()+" du "+errorMessage+" n'est pas valide"));
					}else if(cli.getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
						FunctionalReport.getIntance().increaseCFError(new FunctionalReportLine(errorCode, cli.getROW_ID_SIEBEL(), "Le compte parent "+cli.getCODE_CLIENT()+" du "+errorMessage+" n'est pas valide"));
					}					
				}
			}


		}else if(t instanceof Com){
			Com com = (Com)t;


			List<Map<String,Object>> rows = jdbcTemplate.queryForList("select * from CLI where CODE_CLIENT = ? and NO_PARENT_ERROR is NULL", com.getCODE_CLIENT());
			if(rows.size()==1){
				FunctionalReport.getIntance().increaseSouscription();
				return t;
			} else if(rows.size()==0){
				error = true;
				FunctionalReport.getIntance().increaseSouscriptionError(new FunctionalReportLine("ERR-FUNC-03", com.getID_SIEBEL_LIGNE(), "Le compte parent "+com.getCODE_CLIENT()+" du produit n'est pas valide"));
			}

		}

		if(error)
			return null;
		return t;
	}





}
