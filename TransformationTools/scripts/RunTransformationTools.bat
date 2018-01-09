echo "###############################################"
echo "############### START ACTIVE MQ ###############"
echo "###############################################"
cd D:\\MigrationTools\\TransformationTools\\apache-activemq-5.15.0\\bin
start activemq-admin.bat start
echo "###############################################"
echo "############## ACTIVE MQ STARTED ##############"
echo "###############################################"

cd D:\\MigrationTools\\TransformationTools\\
java -jar D:\\MigrationTools\\TransformationTools\\TransformationTools-1.0.0.jar --spring.profiles.active=full

