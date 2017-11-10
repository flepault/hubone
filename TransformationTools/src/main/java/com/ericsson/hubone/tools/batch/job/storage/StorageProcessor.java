package com.ericsson.hubone.tools.batch.job.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;

@Component
public class StorageProcessor<T extends CesameRootBean> implements ItemProcessor<T, T> {
	
	private static final Logger log = LoggerFactory.getLogger(StorageProcessor.class);

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
				+ "CLIENT_FACTURE,A_CONTROLLER,GROUPE_FACTURATION) values ("
				+ "?,?,?,?,?,?,?,?,?,?,"
				+ "?,?,?)", ((Cli)t).toSmallObjectArray());
		}catch (Exception e) {
			log.error("ROW ID SIEBEL : "+((Cli)t).getROW_ID_SIEBEL()+ ", CODE CLIENT"+((Cli)t).getCODE_CLIENT()+": "+e.getMessage());
			return null;
		}
		
		return t;
	}


}
