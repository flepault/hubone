@echo off

if %5==N (
	mkdir %4
) else (
	echo Create %4 on %1 server
	pause
	
	cp D:\MigrationTools\TransformationTools\output\EPBME.csv \\SV652HTRAFECB1\hshare
	echo "On DB Server copy manually \\SV652HTRAFECB1\hshare\EPBME.csv to E: Drive"
	pause
	
	cp D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv \\SV652HTRAFECB1\hshare
	echo "On DB Server copy manually \\SV652HTRAFECB1\hshare\SubscriptionInfoBME.csv to E: Drive"
	pause	
)

echo "###############################################"
echo "#         DB BACKUP BEFORE MIGRATION          #"
echo "###############################################"
sqlcmd -b -S %1 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_BeforeMigration.bak'"
IF ERRORLEVEL 1 ( 
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 

sqlcmd -b -S %1 -Q "BACKUP DATABASE %3 TO DISK = '%4%3_BeforeMigration.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %3 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %3 : OK 

echo "###############################################"
echo "#               CLIENT INJECTION              #"
echo "###############################################"
cmd /c Accounts\\01ClientAccounts\\RunAccountsLoader.bat

sqlcmd -S %1 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (12)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\ClientAccount.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\ClientAccount.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des Clients : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des Clients : OK 
)

echo "###############################################"
echo "#            REGROUPCF INJECTION              #"
echo "###############################################"
cmd /c Accounts\\02RegroupCFAccounts\\RunAccountsLoader.bat

sqlcmd -S %1 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (14)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\RegroupCFAccount.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\RegroupCFAccount.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des RegroupCFs : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des RegroupCFs : OK 
)


echo "###############################################"
echo "#                 CF INJECTION                #"
echo "###############################################"
cmd /c Accounts\\03CFAccounts\\RunAccountsLoader.bat

sqlcmd -S %1 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (11)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\CFAccount.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\CFAccount.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des CFs : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des CFs : OK 
)

echo "###############################################"
echo "#              ENDPOINT INJECTION             #"
echo "###############################################"
cmd /c Accounts\\04EPAccounts\\RunAccountsLoader.bat

sqlcmd -S %1 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (13)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\EPAccount.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\EPAccount.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des Endpoints : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des Endpoints : OK 
)

echo "###############################################"
echo "#          ENDPOINT BME SQL LOADING           #" 
echo "###############################################"

if %5==N (
	sqlcmd -b -S %1 -Q "BULK INSERT %2.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
) else (
	sqlcmd -b -S %1 -Q "BULK INSERT %2.dbo.t_be_hub_pdc_serviceidaudit FROM 'E:\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
)
IF ERRORLEVEL 1 (
	echo Injection des Endpoint BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Injection des Endpoint BME : OK 

sqlcmd -b -S %1 -Q "update sidaudit set c_EPAccountId = am.id_acc from %2.dbo.t_be_hub_pdc_serviceidaudit sidaudit join %2.dbo.t_account_mapper am on am.nm_login = sidaudit.c_Username"
IF ERRORLEVEL 1 (
	echo Maj des Endpoint BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des Endpoint BME : OK 

echo "###############################################"
echo "#       DB BACKUP WITH ACCOUNT MIGRATED       #"
echo "###############################################"
sqlcmd -b -S %1 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_WithAccountsMigrated.bak'"
IF ERRORLEVEL 1 (
	echo BackUp %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUp %2 : OK 

echo "###############################################"
echo "#             INSTANT RC TO FALSE             #"
echo "###############################################"
sqlcmd -b -S %1 -Q "update %2.dbo.t_db_values set value='false' where parameter='Instantrc'"
IF ERRORLEVEL 1 (
	echo InstantRC to False : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo InstantRC to False : OK 

echo "###############################################"
echo "#               CLOSE INTERVAL                #"
echo "###############################################"
sqlcmd -b -S %1 -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from %2.dbo.t_usage_interval where dt_end < DATEADD(month, -1, GETDATE()) and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
IF ERRORLEVEL 1 (
	echo Generate MigrationCloseInterval script : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Generate MigrationCloseInterval script : OK 
cmd /c MigrationCloseInterval.bat

echo "###############################################"
echo "#          OLD SUBSCRIPTION INJECTION         #"
echo "###############################################"
cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

sqlcmd -S %1 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where vt_start < DATEADD(DAY,1,EOMONTH(SYSDATETIME (),-2)) and id_group is null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\Subscription.Old.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\Subscription.Old.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des Old Subscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des Old Subscriptions : OK 
)

echo "###############################################"
echo "#      OLD GROUP SUBSCRIPTION INJECTION       #"
echo "###############################################"
cmd /c GroupSubscriptions\\OldGroupSubscriptions\\RunGroupSubscriptionsLoader.bat

sqlcmd -S %1 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where vt_start < DATEADD(DAY,1,EOMONTH(SYSDATETIME (),-2))  and id_group is not null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\GroupSubscription.Old.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\GroupSubscription.Old.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des Old GroupSubscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des Old GroupSubscriptions : OK 
)

echo "###############################################"
echo "#             INSTANT RC TO TRUE              #"
echo "###############################################"
sqlcmd -b -S %1 -Q "update %2.dbo.t_db_values set value='true' where parameter='Instantrc'"
IF ERRORLEVEL 1 (
	echo InstantRC to True : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo InstantRC to True : OK 

echo "###############################################"
echo "#          NEW SUBSCRIPTION INJECTION         #"
echo "###############################################"
cmd /c Subscriptions\\NewSubscriptions\\RunSubscriptionsLoader.bat

sqlcmd -S %1 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub where vt_start >= DATEADD(DAY,1,EOMONTH(SYSDATETIME (),-2))   and id_group is null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\Subscription.New.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\Subscription.New.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des New Subscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des New Subscriptions : OK 
)


echo "###############################################"
echo "#      NEW GROUP SUBSCRIPTION INJECTION       #"
echo "###############################################"
cmd /c GroupSubscriptions\\NewGroupSubscriptions\\RunGroupSubscriptionsLoader.bat
sqlcmd -S %1 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where vt_start < DATEADD(DAY,1,EOMONTH(SYSDATETIME (),-2))  and id_group is not null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\GroupSubscription.New.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\GroupSubscription.New.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des New GroupSubscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des New GroupSubscriptions : OK 
)

echo "###############################################"
echo "#   DB BACKUP ACCOUNT & SUBS/NO ICB MIGRATED  #"
echo "###############################################"
sqlcmd -b -S %1 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_WithAccountsMigrated&WithSubscriptionsNoICBMigrated.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 

echo "###############################################"
echo "#                ICB INJECTION                #"
echo "###############################################"
cmd /c Rates\\02ICBRates\\RunICBRatesLoader.bat

sqlcmd -S %1 -Q "set nocount on;SELECT count(*) FROM %2.dbo.t_pricelist  where n_type = 0"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\t_pt_FlatRecurringCharge.input.csv  | awk '{print $1-1}' > nbTransformedFlatRecTmp
set /p nbTransformedFlatRec= < nbTransformedFlatRecTmp
del nbTransformedFlatRecTmp

wc -l D:\MigrationTools\TransformationTools\output\t_pt_NonRecurringCharge.input.csv  | awk '{print $1-1}' > nbTransformedNonRecTmp
set /p nbTransformedNonRec= < nbTransformedNonRecTmp
del nbTransformedNonRecTmp

wc -l D:\MigrationTools\TransformationTools\output\t_pt_RampBucket.input.csv  | awk '{print $1-1}' > nbTransformedRampTmp
set /p nbTransformedRamp= < nbTransformedRampTmp
del nbTransformedRampTmp

wc -l D:\MigrationTools\TransformationTools\output\t_pt_XPCMS.input.csv  | awk '{print $1-1}' > nbTransformedXPCMSTmp
set /p nbTransformedXPCMS= < nbTransformedXPCMSTmp
del nbTransformedXPCMSTmp

set /a nbTransformed = "%nbTransformedFlatRec%"+"%nbTransformedNonRec%"+"%nbTransformedRamp%"+"%nbTransformedXPCMS%"

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\t_pt_FlatRecurringCharge.input_rejected.csv  | awk '{print $1-1}' > nbRejectedFlatRecTmp
set /p nbFlatRecRejected= < nbRejectedFlatRecTmp
del nbRejectedFlatRecTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\t_pt_NonRecurringCharge.input_rejected.csv  | awk '{print $1-1}' > nbRejectedNonRecTmp
set /p nbRejectedNonRec= < nbRejectedNonRecTmp
del nbRejectedNonRecTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\t_pt_RampBucket.input_rejected.csv  | awk '{print $1-1}' > nbRejectedRampTmp
set /p nbRejectedRamp= < nbRejectedRampTmp
del nbRejectedRampTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\t_pt_XPCMS.input_rejected.csv  | awk '{print $1-1}' > nbRejectedXPCMSTmp
set /p nbRejectedXPCMS= < nbRejectedXPCMSTmp
del nbRejectedXPCMSTmp

set /a nbRejected = "%nbFlatRecRejected%"+"%nbRejectedNonRec%"+"%nbRejectedRamp%"+"%nbRejectedXPCMS%"

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbDB%" == "%nbFile%" (
	echo Injection des ICB : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des ICB : OK 
)

echo "###############################################"
echo "#        SUBSCRIPTION BME SQL LOADING         #"
echo "###############################################"
if %5==N (
	sqlcmd -b -S %1 -Q "BULK INSERT %2.dbo.t_be_hub_cat_subscriptionin FROM 'D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
) else (
	sqlcmd -b -S %1 -Q "BULK INSERT %2.dbo.t_be_hub_cat_subscriptionin FROM 'E:\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
)
IF ERRORLEVEL 1 (
	echo Injection des Subscriptions BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Injection des Subscriptions BME : OK 

sqlcmd -b -S %1 -Q "update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CG%') where c_SharedBucketScope = 0;update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/GCF%') where c_SharedBucketScope = 1;update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CF%') where c_SharedBucketScope = 2"
IF ERRORLEVEL 1 (
	echo Maj des Subscriptions BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des Subscriptions BME : OK 

echo "###############################################"
echo "#          DB BACKUP POST MIGRATION           #"
echo "###############################################"
sqlcmd -b -S %1 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_PostMigration.bak'"
IF ERRORLEVEL 1 ( 
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 

sqlcmd -b -S %1 -Q "BACKUP DATABASE %3 TO DISK = '%4%3_PostMigration.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %3 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %3 : OK 