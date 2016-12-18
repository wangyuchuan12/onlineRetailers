mvn clean package
sh /Users/dawang/bin/apache-tomcat-7.0.70/bin/shutdown.sh
rm -R /Users/dawang/bin/apache-tomcat-7.0.70/webapps/ROOT*
cp oa-manager/oa-manager-mainweb/target/oa.war /Users/dawang/bin/apache-tomcat-7.0.70/webapps/
sh /Users/dawang/bin/apache-tomcat-7.0.70/bin/startup.sh
