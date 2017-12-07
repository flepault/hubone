package com.ericsson.hubone.tools.report;

public class Report {

	protected volatile Integer cli;
	protected volatile Integer com;
	protected volatile Integer cliKO;
	protected volatile Integer comKO;
	protected volatile Integer cliOK;
	protected volatile Integer comOK;
	
	public Report() {
		cli= 0;	
		com= 0;	
		cliKO= 0;	
		comKO= 0;	
		cliOK= 0;	
		comOK= 0;	
		
	}
	
	public synchronized void increaseCLI() {
		cli++;		
	}

	public synchronized void increaseCOM() {
		com++;	
	}

	public synchronized void increaseCLIKO() {
		cliKO++;	
	}

	public synchronized void increaseCOMKO() {
		comKO++;	
	}

	public synchronized void increaseCLIOK() {
		cliOK++;	
	}

	public synchronized void increaseCOMOK() {
		comOK++;
	}
	

}
