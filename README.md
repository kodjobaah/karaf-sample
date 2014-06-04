Karaf Sample Project Usig cxf, camel, activemq and openjpa
==================

From the parent Project:
mvn install

==Option 1: Using karaf kar

From karaf console

>feature:repo-add camel 2.13.1
>feature:repo-add activemq 5.9.0
>kar:install <parent-project-dir>/karaf-kar/karaf-kar-0.0.1.kar
>feature:install karaf-sample

==Option 2: Installing the individual features and bundles

Karaf Setup:

-- Active MQ ---
Place the following files from the karaf-etc folder into the etc folder of your karaf installation.
 activemq.xml
 org.apache.activemq.server-default.cfg
 org.apache.activemq.webconsole.cfg
In order to get the projects running the following features need to be installed:

--- FEATURES -------

>feature:repo-add camel 2.13.1
>feature:install war
>feature:install cxf
>feature:install camel-jaxb
>feature:install camel-blueprint
>feature:install camel-cxf
//In order to be able use velocity within routes
>feature:install camel-velocity

>feature:repo-add activemq 5.9.0
>feature:install activemq-broker
>feature:install activemq-camel


//This is needed so that the classpath can be used to request the wsdl
>install mvn:org.ops4j.pax.url/pax-url-commons/2.0.0
>install mvn:org.ops4j.pax.url/pax-url-classpath/2.0.0

These are required for the entities project
>features:install jndi jpa transaction
>install -s mvn:org.apache.geronimo.specs/geronimo-jpa_2.0_spec/1.1
>install -s mvn:commons-collections/commons-collections/3.2.1
>install -s mvn:commons-pool/commons-pool/1.5.4
>install -s mvn:commons-dbcp/commons-dbcp/1.4
>install -s mvn:commons-lang/commons-lang/2.6
>install -s wrap:mvn:net.sourceforge.serp/serp/1.13.1
>install -s mvn:org.apache.openjpa/openjpa/2.1.1

Install samples
>install -s file://<project-parent>/webservice-interface/target/webservice-interface-0.0.0.jar
>install -s file://<project-parent>/webservice-camel/target/webservice-camel-0.0.0.jar
>install -s file://<project-parent>/entities/target/entities-0.0.0.jar


After installation:

Accessing wsdl:
http://localhost:8181/cxf/video-info/webservices/video?wsdl

Accesing activemq brokers:
http://localhost:8181/activemqweb 

Client Application:
cd <parent-directory>/webservices-client
sbt stage
./target/universal/stage/bin/webservice-client

To test installation type:
http://localhost:8080
This will provide you with a test page that does a round trip test of all the components.


 