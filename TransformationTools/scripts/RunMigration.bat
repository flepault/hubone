@echo off

set DBServerName=VM-Migration
set DBInstanceName=NetMeter
set DBInstanceStagingName=NetMeter_VM_Migration

set BackUpFolder=D:\MigrationTools\DBBackUp\

REM PROD=Y or N (N pour environnement Dev, Y pour environnement Prod/VP/VABF/Integration HubOne
set PROD=N

echo "###############################################"
echo "########## RUN TRANSFORMATION TOOLS ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\
cmd /c RunTransformationTools.bat

echo "###############################################"
echo "########### DEPLOY TRANSFORMED DATA ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\output\\
cmd /c D:\\MigrationTools\\TransformationTools\\output\\DeployOutputFile.bat

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
echo "############### RUN ECB LOADER  ###############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoader.bat %DBServerName% %DBInstanceName% %DBInstanceStagingName% %BackUpFolder% %PROD%

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
pause