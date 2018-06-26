package com.ericsson.hubone.tools.batch.job.validation.technical;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameBeanField;
import com.ericsson.hubone.tools.batch.data.cesame.bean.CesameRootBean;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Cli;
import com.ericsson.hubone.tools.batch.data.cesame.bean.Com;
import com.ericsson.hubone.tools.batch.data.cesame.rules.Rules;
import com.ericsson.hubone.tools.batch.data.cesame.types.Types;
import com.ericsson.hubone.tools.batch.job.superjob.FilesNames;
import com.ericsson.hubone.tools.report.tech.TechnicalReport;
import com.ericsson.hubone.tools.report.tech.TechnicalReportLine;
import com.ericsson.hubone.tools.report.transformation.CartoClient;

public class TechValidationProcessor<T extends CesameRootBean> implements ItemProcessor<T, T>{	

	private List<String> resiliationCMDList;
	
	@Override
	public T process(T t) throws Exception {	
		if(file==null)
			return t;
		return control(t);
	}

	public TechValidationProcessor(String file,List<String> resiliationCMDList) {
		this.file=file;
		this.resiliationCMDList = resiliationCMDList;
	}

	public TechValidationProcessor() {
		this.file=null;
	}

	protected String file;	

	protected T control(T t){	

		String id = null;

		if(t instanceof Cli){							
			id = ((Cli)t).getROW_ID_SIEBEL();
			TechnicalReport.getIntance().increaseCLI();
			CartoClient.getIntance().addAccount(((Cli)t).getCODE_CLIENT(),((Cli)t).getNIV_HIERARCHIE_CLIENT());		

		}else{
			
			if(file.startsWith(FilesNames.RN8) || file.startsWith(FilesNames.RN9)) {
				
				String ACTION = null;		

				if(((Com)t).getPARAM_PRODUIT_ADD()!=null || !((Com)t).getPARAM_PRODUIT_ADD().equals("")){

					boolean error = false;

					for(String param:((Com)t).getPARAM_PRODUIT_ADD().split(";")){
						if(param != null && !param.equals("")) {
							String[] paramSplit = param.split("=");
							
							if(paramSplit.length==0) {
								TechnicalReport.getIntance().increaseError("Action", new TechnicalReportLine("ERR-TECH-04",file, id, "Com.PARAM_PRODUIT_ADD", "Problème de structure d'attributs additionnels"));
								return null;
							}else if(paramSplit.length==1) {
								TechnicalReport.getIntance().increaseError("Action", new TechnicalReportLine("ERR-TECH-04",file, id, "Com.PARAM_PRODUIT_ADD", "Valeur null pour l'attribut additionel"));
								return null;
							}else if(paramSplit.length>2) {
								TechnicalReport.getIntance().increaseError("Action", new TechnicalReportLine("ERR-TECH-04",file, id, "Com.PARAM_PRODUIT_ADD", "Problème de structure d'attributs additionnels"));
								return null;
							}							
							
							String value = paramSplit[1];	
							
							if(value==null)
								error = true;
							else {
								if(paramSplit[0].equals("ACTION")){
									ACTION = value;									
								}
							}
						}
					}

					if(error)
						return null;
				}
				
				if(ACTION.equals("RESILIATION")) {
					resiliationCMDList.add(((Com)t).getINTEGRATION_ID());
					return null;
				} else if(ACTION.equals("MODIFICATION"))
					return null;
				
			}else {
				
				if(resiliationCMDList.contains(((Com)t).getINTEGRATION_ID()))
					return null;
				
			}
			
			
			id = ((Com)t).getID_SIEBEL_LIGNE();
			TechnicalReport.getIntance().increaseCOM();			
		}

		boolean error = false;


		for(Field f:t.getClass().getFields()){

			if(f.getName()!="column"){
				try {
					CesameBeanField field = (CesameBeanField)f.get(t);					

					if(field.isMandatory()){
						if(field.getValue()==null || field.getValue().equals("")){							

							TechnicalReport.getIntance().increaseMandatoryError(new TechnicalReportLine("ERR-TECH-01",file, id, t.getClass().getSimpleName()+"."+f.getName(), "Null Value"));
							error = true;
						}else {

							if(checkTypeAndRules(t,f,field,id))
								error = true;
						}
					} else {

						if(field.getValue()==null || field.getValue().equals("")){							

							if(field.isDependentMandatory()){
								if(checkRules(t,f,field,id))
									error = true;
							}
						}else {

							if(checkTypeAndRules(t,f,field,id))
								error = true;
						}
					}

				}catch (Exception e) {
					error = true;
					e.printStackTrace();
				} 




			}
		}
		if(error){
			if(t instanceof Cli){							
				TechnicalReport.getIntance().increaseCLIKO();
			}else{
				TechnicalReport.getIntance().increaseCOMKO();
			}

			return null;
		}

		if(t instanceof Cli){							
			TechnicalReport.getIntance().increaseCLIOK();
		}else{
			TechnicalReport.getIntance().increaseCOMOK();
		}
		return t;
	}

	private boolean checkTypeAndRules(T t,Field f,CesameBeanField field,String id) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		boolean error = false;

		if(field.getType()!=null && !field.getType().equals("")){
			if(!(Boolean)(Types.class.getMethod(field.getType(), String.class)).invoke(null,field.getValue())){
				TechnicalReport.getIntance().increaseTypeError(field.getType(), new TechnicalReportLine("ERR-TECH-02",file, id, t.getClass().getSimpleName()+"."+f.getName(), "Error with type : "+field.getType()));
				error = true;
			}
		}

		if(checkRules(t,f,field,id))
			error = true;

		return error;
	}


	private boolean checkRules(T t,Field f,CesameBeanField field,String id){

		boolean error = false;

		try{
			for(String rule:field.getRules()){
				boolean ok = (Boolean)(Rules.class.getMethod(rule, String.class, t.getClass())).invoke(null,field.getValue(),t);

				if(!ok){
					TechnicalReport.getIntance().increaseRulesError(rule, new TechnicalReportLine("ERR-RG-"+rule,file, id, t.getClass().getSimpleName()+"."+f.getName(), "Error with rule : "+rule));
					error = true;
				}
			}
		} catch (Exception e) {
			error = true;
			e.printStackTrace();
		} 

		return error;
	}
	
	
}
