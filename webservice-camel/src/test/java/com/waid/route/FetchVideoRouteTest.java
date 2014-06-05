package com.waid.route;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

import com.waid.webservice.InputFetchVideo;
import com.waid.webservice.OutputFetchVideo;

public class FetchVideoRouteTest extends CamelBlueprintTestSupport {

	@EndpointInject(uri = "mock:videoInput")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "seda:test")
	protected ProducerTemplate template;

	// override this method, and return the location of our Blueprint XML file
	// to be used for testing
	@Override
	protected String getBlueprintDescriptor() {
		return "camelTestContext.xml";
	}

	@Test
	public void testFetchVideoRoutes() throws IOException, InterruptedException {
	//	Resource xmlFile = new ClassPathResource("fetchVideoInput.xml");
	//	InputStream stream = xmlFile.getInputStream();
	//	String body = IOUtils.toString(stream);
		resultEndpoint.setMinimumExpectedMessageCount(1);
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+
						"<ns2:inputFetchVideo xmlns:ns2=\"http://webservice.waid.com\">\n"+
						"    <videoId>kodjo</videoId>\n"+
						"</ns2:inputFetchVideo>\n";
		
		
		
		resultEndpoint.expectedBodiesReceived(expectedResult);
		// // Prepare the request message for the camel-cxf procedure
		Map<Object, String> mapHeaders = new HashMap<Object, String>();
		mapHeaders.put(CxfConstants.OPERATION_NAMESPACE,
				"http://webservices.waid.com/");
		mapHeaders.put(CxfConstants.OPERATION_NAME, "fetchVideo");
		
		InputFetchVideo inputFetchVideo = new InputFetchVideo();
		
		inputFetchVideo.setVideoId("kodjo");
		OutputFetchVideo result = (OutputFetchVideo) template.requestBodyAndHeader(inputFetchVideo, "CxfConstants.OPERATION_NAME", "fetchVideo");
		
		assertMockEndpointsSatisfied();
		assertEquals(result.getName(),"testing something");

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

	
	@Override protected void debugBefore(Exchange exchange,
	  org.apache.camel.Processor processor, ProcessorDefinition<?> definition,
	  String id, String label) { log.info("Before " + definition +
	  " with body " + exchange.getIn().getBody());
	  
	  }
	  
	  
	  @Override protected void debugAfter(Exchange exchange,
	  org.apache.camel.Processor processor, ProcessorDefinition<?> definition,
	  String id, String label, long timeTaken) { log.info("After " + definition
	  + " with body " + exchange.getIn().getBody());
	  
	  }
	 

}
