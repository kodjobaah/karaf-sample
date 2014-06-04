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

> activemq.xml<br/>
> org.apache.activemq.server-default.cfg<br/>
> org.apache.activemq.webconsole.cfg<br/>

In order to get the projects running the following features need to be installed:

--- FEATURES -------

>feature:repo-add camel 2.13.1<br/>
>feature:install war<br/>
>feature:install cxf<br/>
>feature:install camel-jaxb<br/>
>feature:install camel-blueprint<br/>
>feature:install camel-cxf<br/>
//In order to be able use velocity within routes<br/>
>feature:install camel-velocity<br/>



>feature:repo-add activemq 5.9.0<br/>
>feature:install activemq-broker<br/>
>feature:install activemq-camel<br/>


//This is needed so that the classpath can be used to request the wsdl<br/>
>install mvn:org.ops4j.pax.url/pax-u<br/>
rl-commons/2.0.0<br/>
>install mvn:org.ops4j.pax.url/pax-url-classpath/2.0.0<br/>




These are required for the entities project
>features:install jndi jpa transaction<br/>
>install -s mvn:org.apache.geronimo.specs/geronimo-jpa_2.0_spec/1.1<br/>
>install -s mvn:commons-collections/commons-collections/3.2.1<br/>
>install -s mvn:commons-pool/commons-pool/1.5.4<br/>
>install -s mvn:commons-dbcp/commons-dbcp/1.4<br/>
>install -s mvn:commons-lang/commons-lang/2.6<br/>
>install -s wrap:mvn:net.sourceforge.serp/serp/1.13.1<br/>
>install -s mvn:org.apache.openjpa/openjpa/2.1.1<br/>

Install samples<br/>
>install -s file://<project-parent>/webservice-interface/target/webservice-interface-0.0.0.jar<br/>
>install -s file://<project-parent>/webservice-camel/target/webservice-camel-0.0.0.jar<br/>
>install -s file://<project-parent>/entities/target/entities-0.0.0.jar<br/>


After installation:<br/>

Accessing wsdl:<br/>
>http://localhost:8181/cxf/video-info/webservices/video?wsdl<br/>

Accesing activemq brokers:<br/>
>http://localhost:8181/activemqweb <br/>

Build client application:<br/>
>cd <parent-directory>/webservices-client<br/>
>sbt stage<br/>
To run the client application<br/>
>./target/universal/stage/bin/webservice-client<br/>

To test installation type:<br/>
>http://localhost:8080<br/>
This will provide you with a test page that does a round trip test of all the components.<br/>


 