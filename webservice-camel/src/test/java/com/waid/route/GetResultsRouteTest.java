package com.waid.route;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Dictionary;
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
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;

import com.waid.webservice.InputFetchResults;
import com.waid.webservice.InputFetchVideo;
import com.waid.webservice.OutputFetchResults;
import com.waid.webservice.OutputFetchVideo;

public class GetResultsRouteTest extends CamelBlueprintTestSupport {

	
	
	@Produce(uri = "seda:test.results")
	protected ProducerTemplate resultsTemplate;


	@Produce(uri = "seda:results")
	protected ProducerTemplate messageProducer;


	private SessionInfo sessionInfo;

	// override this method, and return the location of our Blueprint XML file
	// to be used for testing
	@Override
	protected String getBlueprintDescriptor() {
		return "camelTestContext.xml";
	}

	
	@Override
	protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
			super.addServicesOnStartup(services);
			sessionInfo = mock(SessionInfo.class);
			when(sessionInfo.isRequiredResponse(any(Exchange.class))).thenReturn(true);
			services.put("sessionInfo", asService(sessionInfo, "sessionInfo","sessionInfo"));
	}
	  

	@Test
	public void testGetSuccessfullResult() throws IOException, InterruptedException {
	//	Resource xmlFile = new ClassPathResource("fetchVideoInput.xml");
	//	InputStream stream = xmlFile.getInputStream();
	//	String body = IOUtils.toString(stream);
	
		String body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+
						"<ns2:inputFetchVideo xmlns:ns2=\"http://webservice.waid.com\">\n"+
						"    <videoId>kodjo</videoId>\n"+
						"</ns2:inputFetchVideo>\n";
		
		
		//Place messages on to queue
		messageProducer.sendBodyAndHeader(body, SessionInfo.SESSION_HEADER_INFO, "fifty");;

		
		InputFetchResults inputFetchResults = new InputFetchResults();
		
		inputFetchResults.setId("fifty");
		//OutputFetchResults result = (OutputFetchResults) resultsTemplate.requestBodyAndHeader(inputFetchResults, "CxfConstants.OPERATION_NAME", "fetchVideo");
		
		verify(sessionInfo).isRequiredResponse(any(Exchange.class));
		
		verify(sessionInfo).setSessionId(any(InputFetchResults.class));
		
		//assertEquals(result.getMessage(),"done");

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
