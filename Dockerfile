FROM jboss/wildfly
COPY Spring-Boot-REST-0.0.1-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
