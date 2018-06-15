@echo off


echo "###############################################"
echo "#         DB BACKUP BEFORE MIGRATION          #"
echo "###############################################"

sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_BeforeMigration.bak'"
IF ERRORLEVEL 1 ( 
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 

sqlcmd -b -S %1 -U %8 -P %9  -Q "BACKUP DATABASE %3 TO DISK = '%4%3_BeforeMigration.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %3 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %3 : OK 
if %5==Y (

	sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %7 TO DISK = '%4%7_BeforeMigration.bak'"
	IF ERRORLEVEL 1 (
		echo BackUP %7 : KO 
		echo Verifier la cause des problemes avant de continuer !
		pause
	)
	echo BackUP %7 : OK 
)

echo "###############################################"
echo "#               CLIENT INJECTION              #"
echo "###############################################"
cmd /c Accounts\\01ClientAccounts\\RunAccountsLoader.bat

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (12)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
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

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (14)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
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

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (11)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
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

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;SELECT count(*) as Nombre FROM %2.dbo.t_account WHERE id_type in (13)"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
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
	sqlcmd -b -S %1 -U %8 -P %9 -Q "BULK INSERT %2.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
) else (
	sqlcmd -b -S %1 -U %8 -P %9 -Q "BULK INSERT %2.dbo.t_be_hub_pdc_serviceidaudit FROM 'H:\Bulk\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
)
IF ERRORLEVEL 1 (
	echo Injection des Endpoint BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Injection des Endpoint BME : OK 

sqlcmd -b -S %1 -U %8 -P %9 -Q "update sidaudit set c_EPAccountId = am.id_acc from %2.dbo.t_be_hub_pdc_serviceidaudit sidaudit join %2.dbo.t_account_mapper am on am.nm_login = sidaudit.c_Username"
IF ERRORLEVEL 1 (
	echo Maj des Endpoint BME : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo Maj des Endpoint BME : OK 

echo "###############################################"
echo "#       DB BACKUP WITH ACCOUNT MIGRATED       #"
echo "###############################################"
sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_WithAccountsMigrated.bak'"
IF ERRORLEVEL 1 (
	echo BackUp %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUp %2 : OK 
