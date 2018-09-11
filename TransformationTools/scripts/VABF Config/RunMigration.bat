@echo off

set DBServerName=SV652HTRAFDB1
set DBInstanceName=NetMeter
set DBInstanceStagingName1=NetMeter_SV652HTRAFECB1
set DBInstanceStagingName2=NetMeter_SV652HTRAFECB2

set BackUpFolder=H:\MigrationTools\DBBackUp\

REM PROD=Y or N (N pour environnement Dev, Y pour environnement Prod/VP/VABF/Integration HubOne
set PROD=Y

set ECBServerName=SV652HTRAFECB1
set DB_OPTION_USER=nmdbo
set DB_OPTION_PASS=MetraTech@2017

set NB_MONTH_BACK=1

echo "###############################################"
echo "##########      PREPARE FOLDER      ###########"
echo "###############################################"
if %PROD%==Y (
	mkdir \\%DBServerName%\h$\MigrationTools\DBBackUp\
	mkdir \\%DBServerName%\h$\Bulk	
) else (
	mkdir %BackUpFolder%
)

echo "###############################################"
echo "########## RUN TRANSFORMATION TOOLS ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\
cmd /c RunTransformationTools.bat %NB_MONTH_BACK%

echo "###############################################"
echo "########### DEPLOY TRANSFORMED DATA ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\output\\
cmd /c D:\\MigrationTools\\TransformationTools\\output\\DeployOutputFile.bat

if %PROD%==Y (
	cp D:\MigrationTools\TransformationTools\output\EPBME.csv \\%DBServerName%\h$\Bulk\	
	cp D:\MigrationTools\TransformationTools\output\SubscriptionInfoBME.csv \\%DBServerName%\h$\Bulk\
) 

echo "###############################################"
echo "##### DEPLOY ECB MIGRATION TOOLS BINARIES #####"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Bin\\
cmd /c DeployBin.bat

cd D:\\MigrationTools\\ECBDataMigration\\Scripts\\
cmd /c DeployScript.bat

echo "###############################################"
echo "############### RUN ECB MAPPER  ###############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\
cmd /c RunMapper.bat

echo "###############################################"
echo "############# DEPLOY MAPPED DATA  #############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\output\\
cmd /c DeployOutputFile.bat

echo "###############################################"
echo "########## RUN ECB LOADER ACCOUNTS  ###########"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoaderAccounts.bat %DBServerName% %DBInstanceName% %DBInstanceStagingName1% %BackUpFolder% %PROD% %ECBServerName% %DBInstanceStagingName2%  %DB_OPTION_USER% %DB_OPTION_PASS% 

echo "###############################################"
echo "##      GENERATE CLOSING INTERVAL SCRIPT     ##"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
sqlcmd -b -S %DBServerName% -U %DB_OPTION_USER% -P %DB_OPTION_PASS% -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from %DBInstanceName%.dbo.t_usage_interval where dt_end < DATEADD(month, -%NB_MONTH_BACK%, GETDATE()) and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
IF ERRORLEVEL 1 (
	echo Generate MigrationCloseInterval script : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Generate MigrationCloseInterval script : OK 

echo "###############################################"
echo "####### RUN ECB LOADER SUBSCRIPTIONS  #########"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoaderSubscriptions.bat %DBServerName% %DBInstanceName% %DBInstanceStagingName1% %BackUpFolder% %PROD% %ECBServerName% %DBInstanceStagingName2%  %DB_OPTION_USER% %DB_OPTION_PASS% 

echo "###############################################"
echo "#######      RUN ECB LOADER RATES     #########"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoaderRates.bat %DBServerName% %DBInstanceName% %DBInstanceStagingName1% %BackUpFolder% %PROD% %ECBServerName% %DBInstanceStagingName2%  %DB_OPTION_USER% %DB_OPTION_PASS% 

echo "###############################################"
echo "####### RUN ECB MIGRATION TOOLS REPORT ########"
echo "###############################################"
cd D:\\MigrationTools\\LogReader\\
cmd /c RunLogReader.bat

echo "###############################################"
echo "########### MOVING MIGRATION REPORTS ##########"
echo "###############################################"
cd D:\\MigrationTools\\
mkdir Reports
cd D:\\MigrationTools\\Reports
for %%f in (D:\MigrationTools\TransformationTools\reports\technical\*.xlsx) do mv %%~f  .
for %%f in (D:\MigrationTools\TransformationTools\reports\functional\*.xlsx) do mv %%~f  .
for %%f in (D:\MigrationTools\TransformationTools\reports\transformation\*.xlsx) do mv %%~f  .
for %%f in (D:\MigrationTools\LogReader\report\*.xlsx) do mv %%~f  .

echo "###############################################"
echo "###########     MIGRATION ENDED      ##########"
echo "###############################################"

rem cd D:\\MigrationTools\\
rem cmd /c RunBilling.bat