

D:
cd Migration\TransformationTools\apache-activemq-5.15.0\bin
.\activemq-admin.bat start

D:
cd Migration\TransformationTools\
java -jar .\TransformationTools-1.0.0.jar --spring.profiles.active=full

java -jar .\TransformationTools-1.0.0.jar --spring.profiles.active=fast