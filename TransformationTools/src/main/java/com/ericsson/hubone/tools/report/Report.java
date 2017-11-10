package com.ericsson.hubone.tools.report;

public class Report {

	protected Integer cli;
	protected Integer com;
	protected Integer cliKO;
	protected Integer comKO;
	protected Integer cliOK;
	protected Integer comOK;
	
	public Report() {
		cli= 0;	
		com= 0;	
		cliKO= 0;	
		comKO= 0;	
		cliOK= 0;	
		comOK= 0;	
		
	}
	
	public void increaseCLI() {
		cli++;		
	}

	public void increaseCOM() {
		com++;	
	}

	public void increaseCLIKO() {
		cliKO++;	
	}

	public void increaseCOMKO() {
		comKO++;	
	}

	public void increaseCLIOK() {
		cliOK++;	
	}

	public void increaseCOMOK() {
		comOK++;
	}
	

}
