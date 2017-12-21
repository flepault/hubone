@echo off

echo "###############################################"
echo "############### START ACTIVE MQ ###############"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\apache-activemq-5.15.0\\bin
start activemq-admin.bat start
echo "###############################################"
echo "############## ACTIVE MQ STARTED ##############"
echo "###############################################"

echo "###############################################"
echo "########## RUN TRANSFORMATION TOOLS ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\
cmd /c RunTransformationTools.bat
echo "###############################################"
echo "######### TRANSFORMATION TOOLS ENDED ##########"
echo "###############################################"
pause

echo "###############################################"
echo "########### DEPLOY TRANSFORMED DATA ###########"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\output\\
cmd /c D:\\MigrationTools\\TransformationTools\\output\\DeployOutputFile.bat
echo "###############################################"
echo "########## TRANSFORMED DATA DEPLOYED ##########"
echo "###############################################"
pause

echo "###############################################"
echo "##### DEPLOY ECB MIGRATION TOOLS BINARIES #####"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Bin\\
cmd /c DeployBin.bat
echo "###############################################"
echo "#### ECB MIGRATION TOOLS BINARIES DEPLOYED ####"
echo "###############################################"
pause

echo "###############################################"
echo "############### RUN ECB MAPPER  ###############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\
cmd /c RunMapper.bat
echo "###############################################"
echo "############## ECB MAPPER ENDED  ##############"
echo "###############################################"
pause

echo "###############################################"
echo "############# DEPLOY MAPPED DATA  #############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Mapper\\output\\
cmd /c DeployOutputFile.bat
echo "###############################################"
echo "############# MAPPED DATA DEPLOYED ############"
echo "###############################################"
pause

echo "###############################################"
echo "############### RUN ECB LOADER  ###############"
echo "###############################################"
cd D:\\MigrationTools\\ECBDataMigration\\Loader\\
cmd /c RunLoader.bat
echo "###############################################"
echo "############### ECB LOADER ENDED ##############"
echo "###############################################"
pause

echo "###############################################"
echo "####### RUN ECB MIGRATION TOOLS REPORT ########"
echo "###############################################"
cd D:\\MigrationTools\\LogReader\\
cmd /c RunLogReader.bat
echo "###############################################"
echo "###### ECB MIGRATION TOOLS REPORT ENDED #######"
echo "###############################################"
pause

echo "###############################################"
echo "############# RUN BME SQL LOADING #############"
echo "###############################################"
sqlcmd -Q "BULK INSERT NetMeter.dbo.t_be_hub_pdc_serviceidaudit FROM 'D:\MigrationTools\TransformationTools\output\EPBME.csv' WITH (FIELDTERMINATOR = '|', ROWTERMINATOR = '\n')"
echo "###############################################"
echo "############ BME SQL LOADING ENDED ############"
echo "###############################################"
pause

cd D:\\MigrationTools\\
mkdir Reports
cd D:\\MigrationTools\\Reports
mv D:\\MigrationTools\\TransformationTools\\reports\\technical\\*.xlsx .
mv D:\\MigrationTools\\TransformationTools\\reports\\functional\\*.xlsx .
mv D:\\MigrationTools\\TransformationTools\\reports\\transformation\\*.xlsx .
mv D:\\MigrationTools\\LogReader\\report\\*.xlsx .

