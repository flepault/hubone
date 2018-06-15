@echo off

echo "###############################################"
echo "#             INSTANT RC TO FALSE             #"
echo "###############################################"
sqlcmd -b -S %1 -U %8 -P %9 -Q "update %2.dbo.t_db_values set value='false' where parameter='Instantrc'"
IF ERRORLEVEL 1 (
	echo InstantRC to False : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo InstantRC to False : OK 

echo "###############################################"
echo "#          OLD SUBSCRIPTION INJECTION         #"
echo "###############################################"
cmd /c Subscriptions\\OldSubscriptions\\RunSubscriptionsLoader.bat

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where id_group is null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbOldSubDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\Subscription.Old.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\Subscription.Old.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbOldSubDB%" == "%nbFile%" (
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

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where id_group is not null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbOldGSubDB= < nbDbTmp
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\GroupSubscription.Old.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\GroupSubscription.Old.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbOldGSubDB%" == "%nbFile%" (
	echo Injection des Old GroupSubscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des Old GroupSubscriptions : OK 
)

echo "###############################################"
echo "# DB BACKUP WITH ACCOUNT & OLD SUBS MIGRATED  #"
echo "###############################################"
sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_WithAccounts&OldSubsMigrated.bak'"
IF ERRORLEVEL 1 (
	echo BackUp %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUp %2 : OK 

echo "###############################################"
echo "#       NON RECURRING CHARGE ADAPTER          #"
echo "###############################################"
usm run /event:NonRecurringCharges
timeout /t 600

echo "###############################################"
echo "#               CLOSE INTERVAL                #"
echo "###############################################"
cmd /c MigrationCloseInterval.bat

echo "###############################################"
echo "#             INSTANT RC TO TRUE              #"
echo "###############################################"
sqlcmd -b -S %1 -U %8 -P %9 -Q "update %2.dbo.t_db_values set value='true' where parameter='Instantrc'"
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

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub where id_group is null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
set /a nbNewSubDB = "%nbDB%"-"%nbOldSubDB%"
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\Subscription.New.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\Subscription.New.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbNewSubDB%" == "%nbFile%" (
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
sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;select count(*) FROM %2.dbo.t_sub sub where id_group is not null"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
set /p nbDB= < nbDbTmp
set /a nbNewGSubDB = "%nbDB%"-"%nbOldGSubDB%"
del nbDbTmp

wc -l D:\MigrationTools\TransformationTools\output\GroupSubscription.New.input.csv | awk '{print $1-1}' > nbTransformedTmp
set /p nbTransformed= < nbTransformedTmp
del nbTransformedTmp

wc -l D:\MigrationTools\ECBDataMigration\Mapper\rejected\GroupSubscription.New.input_rejected.csv  | awk '{print $1-1}' > nbRejectedTmp
set /p nbRejected= < nbRejectedTmp
del nbRejectedTmp

set /a nbFile = "%nbTransformed%"-"%nbRejected%"

if NOT "%nbNewGSubDB%" == "%nbFile%" (
	echo Injection des New GroupSubscriptions : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
) else (
	echo Injection des New GroupSubscriptions : OK 
)


echo "###############################################"
echo "#        SUBSCRIPTION BME SQL LOADING         #"
echo "###############################################"
if %5==N (
	sqlcmd -b -S %1 -U %8 -P %9 -Q "BULK INSERT %2.dbo.t_be_hub_cat_subscriptionin FROM 'D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
) else (
	sqlcmd -b -S %1 -U %8 -P %9 -Q "BULK INSERT %2.dbo.t_be_hub_cat_subscriptionin FROM 'H:\Bulk\SubscriptionInfoBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
)
IF ERRORLEVEL 1 (
	echo Injection des Subscriptions BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Injection des Subscriptions BME : OK 

sqlcmd -b -S %1 -U %8 -P %9 -Q "update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CG%') where c_SharedBucketScope = 0;update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/GCF%') where c_SharedBucketScope = 1;update %2.dbo.t_be_hub_cat_subscriptionin set c_SharedBucketScope = (select id_enum_data from %2.dbo.t_enum_data where nm_enum_data like '%applicationlevel/CF%') where c_SharedBucketScope = 2"
IF ERRORLEVEL 1 (
	echo Maj des Subscriptions BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des Subscriptions BME : OK 

sqlcmd -b -S %1 -U %8 -P %9 -Q "update %2.dbo.t_be_hub_cat_subscriptionin set c_SubId= t1.id_sub from (select id_sub as id_sub,nm_value as migration_id from %2.dbo.t_sub sub, %2.dbo.t_char_values sla where sla.id_scv = 1001 and sla.id_entity = sub.id_sub  and (nm_value is not null and nm_value != '')) t1 where c_MigrationId = t1.migration_id"
IF ERRORLEVEL 1 (
	echo Maj des c_SubId dans la BME Subscriptions: KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des c_SubId dans la BME Subscriptions : OK 

sqlcmd -b -S %1 -U %8 -P %9 -Q "update %2.dbo.t_be_hub_cat_subscriptionin set c_GroupId= t1.id_group from (select id_group as id_group,nm_value as migration_id from %2.dbo.t_sub sub, %2.dbo.t_char_values sla where sla.id_scv = 1001 and sla.id_entity = sub.id_sub  and (nm_value is not null and nm_value != '') and sub.id_group is not null) t1 where c_MigrationId = t1.migration_id"
IF ERRORLEVEL 1 (
	echo Maj des c_GroupId dans la BME Subscriptions: KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des c_GroupId dans la BME Subscriptions : OK 

echo "###############################################"
echo "#   DB BACKUP ACCOUNT & SUBS/NO ICB MIGRATED  #"
echo "###############################################"
sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_WithAccountsMigrated&WithSubscriptionsNoICBMigrated.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 
