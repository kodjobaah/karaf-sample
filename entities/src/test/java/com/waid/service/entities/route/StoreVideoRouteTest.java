package com.waid.service.entities.route;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Dictionary;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;
import org.osgi.framework.BundleContext;

import com.waid.service.entities.entity.Video;
import com.waid.service.entities.video.VideoService;

public class StoreVideoRouteTest extends CamelBlueprintTestSupport {

	@EndpointInject(uri = "mock:output")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "seda:test")
	protected ProducerTemplate template;

	private VideoService videoService;

	private BundleContext bundleContext;

	private Video vid;

	// override this method, and return the location of our Blueprint XML file
	// to be used for testing
	@Override
	protected String getBlueprintDescriptor() {
		return "camelContextTest.xml";
	}

	
	/*
	@SuppressWarnings("deprecation")
	@Configuration
	public static Option[] configure() throws Exception {
		return new Option[] {
				karafDistributionConfiguration()
						.frameworkUrl(
								maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip")
										.versionAsInProject()).useDeployFolder(false).karafVersion("3.0.0")
						.unpackDirectory(new File("target/paxexam/unpack/")),

				logLevel(LogLevel.WARN),

				features(
						maven().groupId("org.apache.karaf.features").artifactId("standard").type("xml")
								.classifier("features").versionAsInProject(), "http-whiteboard"),
				features(
						maven().groupId("org.apache.karaf.features").artifactId("enterprise").type("xml")
								.classifier("features").versionAsInProject(), "transaction", "jpa", "jndi"),
				features(
						maven().groupId("org.apache.activemq").artifactId("activemq-karaf").type("xml")
								.classifier("features").versionAsInProject(), "activemq-blueprint", "activemq-camel"),
				features(
						maven().groupId("org.apache.cxf.karaf").artifactId("apache-cxf").type("xml")
								.classifier("features").versionAsInProject(), "cxf-jaxws"),
				features(
						maven().groupId("org.apache.camel.karaf").artifactId("apache-camel").type("xml")
								.classifier("features").versionAsInProject(), "camel-blueprint", "camel-jms",
						"camel-jpa", "camel-mvel", "camel-jdbc", "camel-cxf", "camel-test"),

				KarafDistributionOption.editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
						"org.ops4j.pax.url.mvn.proxySupport", "true"),
				keepRuntimeFolder(),

				mavenBundle().groupId("mysql").artifactId("mysql-connector").version("5.1.19"),
				streamBundle(
						bundle().add("OSGI-INF/blueprint/datasource.xml",
								new File("src/main/resources/datasource-waid.xml").toURL())
								.set(Constants.BUNDLE_SYMBOLICNAME, "com.waid.datasource")
								.set(Constants.DYNAMICIMPORT_PACKAGE, "*").build()).start(),
				streamBundle(
						bundle().add("OSGI-INF/blueprint/camel-main-context.xml",
										new File("src/main/resources/camelContextTest.xml")
												.toURL())
								.set(Constants.BUNDLE_SYMBOLICNAME, "com.waid.entities")
								.set(Constants.DYNAMICIMPORT_PACKAGE, "*")
								.set(Constants.EXPORT_PACKAGE, "*").build())
						.start() };
	}

*/
	

	
	@Override
	protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
			super.addServicesOnStartup(services);
			videoService = mock(VideoService.class);
			 vid = new Video("test-title");
			when(videoService.addVideo(any(Video.class))).thenReturn(vid);
			services.put("videoService", asService(videoService, "videoService","videoService"));
			System.out.println("added video service");
	}
	  
	
	/**
	 * Return the system bundle context
	 */

	@Override
	protected BundleContext getBundleContext() {
		bundleContext = mock(BundleContext.class);
		return bundleContext;
	}

	@Test
	public void testStoreVideoRoute() throws InterruptedException {

		
		
		System.out.println("just here");
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.expectedBodiesReceived(vid);

		
		String body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+
				"<ns2:inputFetchVideo xmlns:ns2=\"http://webservice.waid.com\">\n"+
				"    <videoId>kodjo</videoId>\n"+
				"</ns2:inputFetchVideo>\n";
		
		//InputFetchVideo body = new InputFetchVideo();
		template.sendBody(body);

		assertMockEndpointsSatisfied();
		verify(videoService).addVideo(any(Video.class));

	}

	/*
	 * @Override protected RouteBuilder createRouteBuilder() { return new
	 * FetchVideoRoute(); }
	 */

	@Override
	public boolean isUseDebugger() {
		// must enable debugger
		return false;
	}

	@Override
	protected void debugBefore(Exchange exchange,
			org.apache.camel.Processor processor,
			ProcessorDefinition<?> definition, String id, String label) {
		log.info("Before " + definition + " with body "
				+ exchange.getIn().getBody());

	}

	@Override
	protected void debugAfter(Exchange exchange,
			org.apache.camel.Processor processor,
			ProcessorDefinition<?> definition, String id, String label,
			long timeTaken) {
		log.info("After " + definition + " with body "
				+ exchange.getIn().getBody());

	}

}
