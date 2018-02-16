@echo off

mkdir D:\\MigrationTools\\DBBackUp

echo "###############################################"
echo "#######   DB BACKUP BEFORE MIGRATION   ########"
echo "###############################################"
sqlcmd -S VM_Migration -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_BeforeMigration.bak'"
echo "###############################################"
echo "####### DB BACKUP BEFORE MIGRATION DONE #######"
echo "###############################################"
pause
 
cmd /c Accounts\\01ClientAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\02RegroupCFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\03CFAccounts\\RunAccountsLoader.bat

cmd /c Accounts\\04EPAccounts\\RunAccountsLoader.bat

echo "###############################################"
echo "############# RUN BME SQL LOADING #############"
echo "###############################################"
rem cp D:\MigrationTools\TransformationTools\output\EPBME.csv \\SV652HTRAFECB1\hshare
rem echo "On DB Server copy manually \\SV652HTRAFECB1\hshare\EPBME.csv to E: Drive"
rem pause
sqlcmd -S VM_Migration -Q "BULK INSERT NetMeter.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
sqlcmd -S VM_Migration -Q "update NetMeter.dbo.sidaudit set c_EPAccountId = am.id_acc from NetMeter.dbo.t_be_hub_pdc_serviceidaudit sidaudit join NetMeter.dbo.t_account_mapper am on am.nm_login = sidaudit.c_Username"
echo "###############################################"
echo "############ BME SQL LOADING ENDED ############"
echo "###############################################"
pause

echo "###############################################"
echo "#####   DB BACKUP WITH ACCOUNT MIGRATED   #####"
echo "###############################################"
sqlcmd -S VM_Migration -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated.bak'"
echo "###############################################"
echo "##### DB BACKUP WITH ACCOUNT MIGRATED DONE ####"
echo "###############################################"
pause

echo "###############################################"
echo "############# INSTANT RC TO FALSE #############"
echo "###############################################"
sqlcmd -S VM_Migration -Q "update NetMeter.dbo.t_db_values set value='false' where parameter='Instantrc'"
echo "###############################################"
echo "############# INSTANT RC TO FALSE #############"
echo "###############################################"
pause

cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

echo "###############################################"
echo "############### CLOSE INTERVAL  ###############"
echo "###############################################"
sqlcmd -S VM_Migration -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from NetMeter.dbo.t_usage_interval where dt_end < DATEADD(month, -1, GETDATE()) and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
cmd /c MigrationCloseInterval.bat
echo "###############################################"
echo "############### INTERVAL CLOSED  ##############"
echo "###############################################"
pause

cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

echo "###############################################"
echo "#   DB BACKUP ACCOUNT & SUBS/NO ICB MIGRATED  #"
echo "###############################################"
sqlcmd -S VM_Migration -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated&WithSubscriptionsNoICBMigrated.bak'"
echo "###############################################"
echo "#    DB BACKUP ACCOUNT & SUBS/NO ICB DONE     #"
echo "###############################################"
pause

rem echo "Please update the following file D:\MetraTech\RMP\Bin\ECB.Loader.exe.config."
rem echo "Change the 'MaxDegreeOfParallelismSize' properties from 4 to 1."
rem echo "Do not continue before doing this update !!!"
rem pause

cmd /c Rates\\02ICBRates\\RunICBRatesLoader.bat

echo "###############################################"
echo "###### RUN SUBSCRIPTION BME SQL LOADING #######"
echo "###############################################"
rem cp D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv \\SV652HTRAFECB1\hshare
rem echo "On DB Server copy manually \\SV652HTRAFECB1\hshare\SubscriptionInfoBME.csv to E: Drive"
rem pause
sqlcmd -S VM_Migration -Q "BULK INSERT NetMeter.dbo.t_be_hub_cat_subscriptionin FROM 'D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
sqlcmd -S VM_Migration -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from NetMeter.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CG%') where c_SharedBucketScope = 0"
sqlcmd -S VM_Migration -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from NetMeter.dbo.t_enum_data where nm_enum_data like '%applicationlevel/GCF%') where c_SharedBucketScope = 1"
sqlcmd -S VM_Migration -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from NetMeter.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CF%') where c_SharedBucketScope = 2"
echo "###############################################"
echo "######### SUBSCRIPTION BME SQL  ENDED #########"
echo "###############################################"
pause

echo "###############################################"
echo "#DB BACKUP WITH ACCOUNT AND ALL SUBS MIGRATED #"
echo "###############################################"
sqlcmd -S VM_Migration -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated&WithAllSubscription.bak'"
echo "###############################################"
echo "### DB BACKUP WITH ACCOUNT AND ALL SUBS DONE ##"
echo "###############################################"
pause