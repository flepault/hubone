@echo off

if %5==N (

	echo "###############################################"
	echo "#######       RUN BILLING ON DEV       ########"
	echo "###############################################"
	
	echo "###############################################"
	echo "#       NON RECURRING CHARGE ADAPTER          #"
	echo "###############################################"
	usm run /event:NonRecurringCharges
	timeout /t 600
	
	echo "###############################################"
	echo "#           CREATE BILLING GROUP              #"
	echo "###############################################"
	sqlcmd -b -S %1 -Q "set nocount on;select 'usm mbg /interval:'+cast(id_interval as varchar) from %2.dbo.t_usage_interval where dt_end < GETDATE() and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o GenerateBillingGroup.bat
	IF ERRORLEVEL 1 (
		echo Generate GenerateBillingGroup script : KO 
		echo Verifier la cause des problemes avant de continuer !
		pause
	)
	echo Generate GenerateBillingGroup script : OK 
	cmd /c GenerateBillingGroup.bat
	
	echo "###############################################"
	echo "#          SOFT CLOSE BILLING GROUP           #"
	echo "###############################################"
	sqlcmd -b -S %1 -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /soft+ ' from %2.dbo.t_usage_interval where dt_end < GETDATE() and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o SoftCloseBillingGroup.bat
	IF ERRORLEVEL 1 (
		echo Generate SoftCloseBillingGroup script : KO 
		echo Verifier la cause des problemes avant de continuer !
		pause
	)
	echo Generate SoftCloseBillingGroup script : OK 
	cmd /c SoftCloseBillingGroup.bat
	
	echo "###############################################"
	echo "#            RUN INVOICES ADAPTER             #"
	echo "###############################################"
	sqlcmd -b -S %1 -Q "set nocount on;select 'usm run /instance:'+cast(tab_t.id_instance as varchar) from (select inst.id_instance,inst.id_arg_interval from %2.dbo.t_recevent_inst inst, %2.dbo.t_usage_interval inter, %2.dbo.t_billgroup bg, %2.dbo.t_recevent rec where inst.tx_status = 'NotYetRun' and inter.id_interval = inst.id_arg_interval and inter.dt_end < GETDATE() and inter.tx_interval_status!='H' and inst.id_arg_billgroup = bg.id_billgroup and bg.tx_name not in ('Default','DEFAULT') and inst.id_event = rec.id_event and rec.tx_name in ('RecurringCharges','MarkCorrectionCharges','Aggregate Metrics Processor (EOP)', 'MetraTech.Custom.Adapters.HubOne.MetraTaxPreProcessor', 'MetraTech.Custom.Adapters.HubOne.MetraTaxFLSCheck', 'MetraTaxInputAdapter','MetraTaxCalculateAdapter','MetraTaxOutputAdapter', 'MetraTech.Custom.Adapters.HubOne.CorrectionInvoiceAdapter', 'Invoices') and rec.dt_deactivated is null) tab_t" -h -1 -f 1252 -o InvoicesAdapter.bat 
	IF ERRORLEVEL 1 (
		echo Generate InvoicesAdapter script : KO 
		echo Verifier la cause des problemes avant de continuer !
		pause
	)
	echo Generate InvoicesAdapter script : OK 
	cmd /c InvoicesAdapter.bat		
	
	echo "###############################################"
	echo "#######     BILLING LAUNCHED ON DEV    ########"
	echo "###############################################"			
) 
