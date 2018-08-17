package com.ericsson.hubone.tools.batch.job.storage;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.enumeration.HierarchieClient;
import com.ericsson.hubone.tools.report.func.FunctionalReport;
import com.ericsson.hubone.tools.report.func.FunctionalReportLine;

@Component
public class StorageProcessor<T extends CesameRootBean> implements ItemProcessor<T, T> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public StorageProcessor() {
	}

	@Override
	public T process(T t) throws Exception {		
		return store(t);
	}

	private T store(T t) throws DataAccessException, IllegalArgumentException, IllegalAccessException, SecurityException{	

		if(t instanceof Com)
			return t;

		try{
			jdbcTemplate.update("insert into CLI(ROW_ID_SIEBEL,CODE_CLIENT,NOM_CLIENT,STATUT_CRM,ROLE_CLIENT,NIV_HIERARCHIE_CLIENT,"
					+ "NOM_GROUPE, ID_GROUPE, NOM_CLIENT_PARENT, CODE_CLIENT_PARENT,"
					+ "CLIENT_FACTURE,A_CONTROLLER,GROUPE_FACTURATION,FREQ_CYCLE_FACTU) values ("
					+ "?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?)", ((Cli)t).toSmallObjectArray());
		}catch (Exception e) {

			if(((Cli)t).getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.Client.toString())){
				FunctionalReport.getIntance().increaseClientError(new FunctionalReportLine("ERR-FUNC-00", ((Cli)t).getROW_ID_SIEBEL(), "Probleme d'unicité du code client pour ce compte"));
			}else if(((Cli)t).getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.RegroupCF.toString())){
				FunctionalReport.getIntance().increaseRegroupCFError(new FunctionalReportLine("ERR-FUNC-01", ((Cli)t).getROW_ID_SIEBEL(), "Probleme d'unicité du code client pour ce compte"));
			}else if(((Cli)t).getNIV_HIERARCHIE_CLIENT().equals(HierarchieClient.CF.toString())){
				FunctionalReport.getIntance().increaseCFError(new FunctionalReportLine("ERR-FUNC-02", ((Cli)t).getROW_ID_SIEBEL(), "Probleme d'unicité du code client pour ce compte"));
			}
			
			return null;
		}

		return t;
	}


}
