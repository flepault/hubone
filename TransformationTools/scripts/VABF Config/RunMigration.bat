@echo off

set DBServerName=SV652HTRAFDB1
set DBInstanceName=NetMeter
set DBInstanceStagingName1=NetMeter_SV652HTRAFECB1
set DBInstanceStagingName2=NetMeter_SV652HTRAFECB2

set BackUpFolder=H:\MigrationTools\DBBackUp\

REM PROD=Y or N (N pour environnement Dev, Y pour environnement Prod/VP/VABF/Integration HubOne
set PROD=Y

set ECBServerName=SP652HTRAFECB1
set DB_OPTION_USER=nmdbo
set DB_OPTION_PASS=MetraTech@2017

set NB_MONTH_BACK=1

echo "###############################################"
echo "########## RUN TRANSFORMATION TOOLS ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\
cmd /c RunTransformationTools.bat %NB_MONTH_BACK%

REM echo "###############################################"
REM echo "########### DEPLOY TRANSFORMED DATA ###########"
REM echo "###############################################"
REM cd D:\\MigrationTools\\TransformationTools\\output\\
REM cmd /c D:\\MigrationTools\\TransformationTools\\output\\DeployOutputFile.bat

REM echo "###############################################"
REM echo "##### DEPLOY ECB MIGRATION TOOLS BINARIES #####"
REM echo "###############################################"
REM cd D:\\MigrationTools\\ECBDataMigration\\Bin\\
REM cmd /c DeployBin.bat

REM cd D:\\MigrationTools\\ECBDataMigration\\Scripts\\
REM cmd /c DeployScript.bat

REM echo "###############################################"
REM echo "############### RUN ECB MAPPER  ###############"
REM echo "###############################################"
REM cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\
REM cmd /c RunMapper.bat

REM echo "###############################################"
REM echo "############# DEPLOY MAPPED DATA  #############"
REM echo "###############################################"
REM cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\output\\
REM cmd /c DeployOutputFile.bat

REM echo "###############################################"
REM echo "##      GENERATE CLOSING INTERVAL SCRIPT     ##"
REM echo "###############################################"
REM cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
REM sqlcmd -b -S %DBServerName% -U %DB_OPTION_USER% -P %DB_OPTION_PASS% -Q "set nocount on;select 'usm close /interval:'+cast(id_interval as varchar)+' /hard+ /ignoreBG' from %DBInstanceName%.dbo.t_usage_interval where dt_end < DATEADD(month, -%NB_MONTH_BACK%, GETDATE()) and tx_interval_status!='H' order by dt_start" -h -1 -f 1252 -o MigrationCloseInterval.bat
REM IF ERRORLEVEL 1 (
	REM echo Generate MigrationCloseInterval script : KO 
	REM echo Verifier la cause des problemes avant de continuer !
	REM pause
REM )
REM echo Generate MigrationCloseInterval script : OK 


REM echo "###############################################"
REM echo "############### RUN ECB LOADER  ###############"
REM echo "###############################################"
REM cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
REM cmd /c RunLoader.bat %DBServerName% %DBInstanceName% %DBInstanceStagingName1% %BackUpFolder% %PROD% %ECBServerName% %DBInstanceStagingName2%  %DB_OPTION_USER% %DB_OPTION_PASS%

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

rem pause