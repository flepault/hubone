@echo off

mkdir D:\\MigrationTools\\DBBackUp

echo "###############################################"
echo "#######   DB BACKUP BEFORE MIGRATION   ########"
echo "###############################################"
sqlcmd -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_BeforeMigration.bak'"
sqlcmd -Q "BACKUP DATABASE NetMeter_VM_Migration TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_VM_Migration_BeforeMigration.bak'"
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
sqlcmd -Q "BULK INSERT NetMeter.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
echo "###############################################"
echo "############ BME SQL LOADING ENDED ############"
echo "###############################################"
pause

echo "###############################################"
echo "#####   DB BACKUP WITH ACCOUNT MIGRATED   #####"
echo "###############################################"
sqlcmd -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated.bak'"
sqlcmd -Q "BACKUP DATABASE NetMeter_VM_Migration TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_VM_Migration_WithAccountsMigrated.bak'"
echo "###############################################"
echo "##### DB BACKUP WITH ACCOUNT MIGRATED DONE ####"
echo "###############################################"
pause

echo "###############################################"
echo "#### CLOSE INTERVAL & INSTANT RC TO FALSE #####"
echo "###############################################"
sqlcmd -Q "update NetMeter.dbo.t_db_values set value='false' where parameter='Instantrc'"
sqlcmd -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from NetMeter.dbo.t_usage_interval where dt_end < DATEADD(month, -1, GETDATE()) and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
cmd /c MigrationCloseInterval.bat
echo "###############################################"
echo "#### INTERVAL CLOSED & INSTANT RC TO FALSE ####"
echo "###############################################"
pause

cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

echo "###############################################"
echo "#   DB BACKUP ACCOUNT & SUBS/NO ICB MIGRATED  #"
echo "###############################################"
sqlcmd -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated&WithSubscriptionsNoICBMigrated.bak'"
sqlcmd -Q "BACKUP DATABASE NetMeter_VM_Migration TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_VM_Migration_WithAccountsMigrated&WithSubscriptionsNoICBMigrated.bak'"
echo "###############################################"
echo "#    DB BACKUP ACCOUNT & SUBS/NO ICB DONE     #"
echo "###############################################"
pause

echo "Please update the following file D:\MetraTech\RMP\Bin\ECB.Loader.exe.config."
echo "Change the 'MaxDegreeOfParallelismSize' properties from 4 to 1."
echo "Do not continue before doing this update !!!"
pause

cmd /c Rates\\02ICBRates\\RunICBRatesLoader.bat

echo "###############################################"
echo "###### RUN SUBSCRIPTION BME SQL LOADING #######"
echo "###############################################"
sqlcmd -Q "BULK INSERT NetMeter.dbo.t_be_hub_cat_subscriptionin FROM 'D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
sqlcmd -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from t_enum_data where nm_enum_data like '%applicationlevel/CG%') where c_SharedBucketScope = 0"
sqlcmd -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from t_enum_data where nm_enum_data like '%applicationlevel/GCF%') where c_SharedBucketScope = 1"
sqlcmd -Q "update NetMeter.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from t_enum_data where nm_enum_data like '%applicationlevel/CF%') where c_SharedBucketScope = 2"
echo "###############################################"
echo "######### SUBSCRIPTION BME SQL  ENDED #########"
echo "###############################################"
pause

echo "###############################################"
echo "#DB BACKUP WITH ACCOUNT AND ALL SUBS MIGRATED #"
echo "###############################################"
sqlcmd -Q "BACKUP DATABASE NetMeter TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_WithAccountsMigrated&WithAllSubscription.bak'"
sqlcmd -Q "BACKUP DATABASE NetMeter_VM_Migration TO DISK = 'D:\MigrationTools\DBBackUp\NetMeter_VM_Migration_WithAccountsMigrated&WithAllSubscription.bak'"
echo "###############################################"
echo "### DB BACKUP WITH ACCOUNT AND ALL SUBS DONE ##"
echo "###############################################"
pause