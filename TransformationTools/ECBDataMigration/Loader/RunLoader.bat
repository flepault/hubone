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

cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat