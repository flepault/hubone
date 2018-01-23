################ MIGRATION ###################

0- Configure cmd properties
	- Screen Buffer Size W : 250
	- Screen Buffer Size H : 9999
	- Window Size W : 250
	- Window Size H : 100
	- Font Size : 6x8
	
1- Copy MigrationTools.zip into D:

2- Unzip it in D:

3- Deploy input data from Hub One into D:\MigrationTools\TransformationTools\input

4- Update static data in D:\MigrationTools\ECBDataMigration\Mapper\staticData.csv 
   by using "Request STATIC DATA" from D:\MigrationTools\Requests\StaticData.sql
   
5- Update inside field <DynamicFieldsValues></DynamicFieldsValues> of 
   D:\MigrationTools\ECBDataMigration\Mapper\_mappingfiles\GroupSubscription_Mapping.xml 
   and D:\MigrationTools\ECBDataMigration\Mapper\_mappingfiles\Subscription_Mapping.xml
   by using "Request DYNAMIC FIELD MAPPER" from D:\MigrationTools\Requests\StaticData.sql

6- Reduce log level to 2 in :
	- D:\MetraTech\RMP\Config\Logging\logging.xml
	- D:\MetraTech\RMP\Config\Logging\ActivityServices\logging.xml
	- D:\MetraTech\RMP\Config\Logging\HighResolutionTimer\logging.xml
	- D:\MetraTech\RMP\Config\Logging\PluginDebug\logging.xml
	
7- Modify log path in D:\MetraTech\RMP\Config\Logging\ECBMigrationLogConfig\logging.xml
   to D:\MigrationTools\ECBDataMigration\Loader\ECBMigrationAPILog.txt

#### OPTIONAL IF INSTALLATION IS CORRECT :) ####
8- Verify PartitionId,effective date and start date of PO 
9- Verify PartitionId of Pricelist
10- Verify in MetraOffer if REC/RECCA/DEDPRE/PONCT have default ratescheduled to 0.
11- VERIFIER D:\MetraTech\RMP\Config\pipeline\listener.xml
12- VERIFIER D:\MetraTech\RMP\Config\meter\route.xml
################################################

13- Start Activity Service, Pipeline Service, and Billing Service

14- Run RunMigration.bat script

15- During Transformation step follow progress here : http://localhost:8161/admin/queues.jsp (admin/admin)

16- After Loading EP BME,backup & restart database.
17- After Loading Old Subscription, restart database.

18- After the end of subscription use D:\MigrationTools\Requests\RequeteDeControle.sql to check data migrated.


################ VALORISATION ###################

0- Mettre les fichiers CSV ( CDR) au niveau des répertoires du serveur FTP ( déjà configuré sous http://localhost/MetraNet/Default.aspx  => MetraCONTROL => BME RAFAEL => CollectConfigAdapter)

1- go to  : http://localhost/MetraNet/Default.aspx  => MetraCONTROL => Scheduled Adpaters => Run Scheduled Adpater now

2- Lancer dans l'ordre :

a-Collect Format for FLS
b-Filter Cdr from Field by Value
c-Collecte File unicity Check
d-Collecte File Obsolescence Check
e-FTP Retrieve

3- Vérifier au niveau du répertoire "Collecte" le status des fichiers créer apres traitement
4- Vérifier au niveau du répertoire "IncomingFLS" le status des fichiers traités.
