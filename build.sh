export EZYFOX_SERVER_HOME=C:\\Game\\EzyProject\\ezyfox-server-full-1.2.1\\ezyfox-server
mvn -pl . clean install
mvn -pl EUN-common -Pexport clean install
mvn -pl EUN-app-api -Pexport clean install
mvn -pl EUN-app-entry -Pexport clean install
mvn -pl EUN-plugin -Pexport clean install
cp EUN-zone-settings.xml $EZYFOX_SERVER_HOME/settings/zones/
