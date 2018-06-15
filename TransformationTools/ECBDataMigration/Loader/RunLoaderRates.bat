@echo off

echo "###############################################"
echo "#                ICB INJECTION                #"
echo "###############################################"
cmd /c Rates\\02ICBRates\\RunICBRatesLoader.bat

sqlcmd -S %1 -U %8 -P %9 -Q "set nocount on;SELECT count(*) FROM %2.dbo.t_pricelist  where n_type = 0"  -h -1 -f 1252 | awk '{print $1}' > nbDbTmp
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
echo "#          DB BACKUP POST MIGRATION           #"
echo "###############################################"


sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %2 TO DISK = '%4%2_AfterMigration.bak'"
IF ERRORLEVEL 1 ( 
	echo BackUP %2 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %2 : OK 

sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %3 TO DISK = '%4%3_AfterMigration.bak'"
IF ERRORLEVEL 1 (
	echo BackUP %3 : KO 
	echo Verifier la cause des problemes avant de continuer !
	pause
)
echo BackUP %3 : OK 

if %5==Y  (

	sqlcmd -b -S %1 -U %8 -P %9 -Q "BACKUP DATABASE %7 TO DISK = '%4%7_AfterMigration.bak'"
	IF ERRORLEVEL 1 (
		echo BackUP %7 : KO 
		echo Verifier la cause des problemes avant de continuer !
		pause
	)
	echo BackUP %7 : OK 
)