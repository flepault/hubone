@echo off
 
cmd /c Accounts\\01ClientAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\02RegroupCFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\03CFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\04EPAccounts\\RunAccountsLoader.bat

echo "###############################################"
echo "############# RUN BME SQL LOADING #############"
echo "###############################################"
sqlcmd -Q "BULK INSERT NetMeter.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
echo "###############################################"
echo "############ BME SQL LOADING ENDED ############"
echo "###############################################"
pause

echo "###############################################"
echo "#### CLOSE INTERVAL & INSTANT RC TO FALSE #####"
echo "###############################################"
sqlcmd -Q "update NetMeter.dbo.t_db_values set value='false' where parameter='Instantrc'"
sqlcmd -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from NetMeter.dbo.t_usage_interval where dt_end < GETDATE() and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
cmd /c MigrationCloseInterval.bat
echo "###############################################"
echo "#### INTERVAL CLOSED & INSTANT RC TO FALSE ####"
echo "###############################################"
pause


cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

echo "###############################################"
echo "############## INSTANT RC TO TRUE #############"
echo "###############################################"
sqlcmd -Q "update NetMeter.dbo.t_db_values set value='true' where parameter='Instantrc'"
echo "###############################################"
echo "############## INSTANT RC TO TRUE #############"
echo "###############################################"
pause

cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat
