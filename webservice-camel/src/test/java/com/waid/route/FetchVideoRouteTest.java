package com.waid.route;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.cxf.helpers.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.mockito.Mockito.*;


public class FetchVideoRouteTest extends CamelBlueprintTestSupport {



	

	// override this method, and return the location of our Blueprint XML file
	// to be used for testing
	@Override
	protected String getBlueprintDescriptor() {
		return "camelTestContext.xml";
	}
	

	
	@Test
	public void testFetchVideoRoutes() throws IOException {
		Resource xmlFile = new ClassPathResource("fetchVideoInput.xml");
		InputStream stream = xmlFile.getInputStream();
		String body = IOUtils.toString(stream);
		getMockEndpoint("activemq:queue:video.info.queue").setMinimumExpectedMessageCount(1);

		// // Prepare the request message for the camel-cxf procedure
		Map<Object, String> mapHeaders = new HashMap<Object, String>();
		mapHeaders.put(CxfConstants.OPERATION_NAMESPACE,
				"http://webservices.waid.com/");
		mapHeaders.put(CxfConstants.OPERATION_NAME, "fetchVideo");
		template.requestBodyAndHeader("direct:start", body,
				mapHeaders);
		template.requestBody(body);

	}

	/*
	@Override
	protected RouteBuilder createRouteBuilder() {
		return new FetchVideoRoute();
	}
	*/

	@Override
	public boolean isUseDebugger() {
		// must enable debugger
		return true;
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
