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
5- Update GroupSubscription_Mapping.xml and Subscription_Mapping.xml
6- Reduce log level to 2 in :
	- D:\MetraTech\RMP\Config\Logging\logging.xml
	- D:\MetraTech\RMP\Config\Logging\ActivityServices\logging.xml
	- D:\MetraTech\RMP\Config\Logging\HighResolutionTimer\logging.xml
	- D:\MetraTech\RMP\Config\Logging\ECBMigrationLogConfig\logging.xml ( And log path)
7- Verify PartitionId,effective date and start date of PO 
8- Verify PartitionId of Pricelist
9- Start Activity Service
10- Run RunMigration.bat script
11- During Transformation step follow progress here : http://localhost:8161/admin/queues.jsp (admin/admin)