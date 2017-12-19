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
5- Reduce MTLog level in D:\MetraTech\RMP\Config\Logging\logging.xml and D:\MetraTech\RMP\Config\Logging\ActivityServices\logging.xml
6- Start Activity Service
7- Run RunMigration.bat script
8- During Transformation step follow progress here : http://localhost:8161/admin/queues.jsp (admin/admin)