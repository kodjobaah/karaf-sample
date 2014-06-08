/*
 * FluxIT - La Plata - Argentina
 * http://www.fluxit.com.ar
 * Author: Ramiro Pugh
 * 23/05/2013
 */
package com.esb.test.camel;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.cxf.helpers.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Ramiro Pugh
 * @email ramiro.pugh@fluxit.com.ar
 * 
 *        FluxIT - Argentina
 * 
 */

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:camel-context-test.xml")
@DisableJmx(true)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CamelMockedRouteTest
{
	@Autowired
	protected CamelContext context;

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce(uri = "cxf:bean:CustomersServiceConsumerEndpoint")
	protected ProducerTemplate inputProducerTemplate;

	@Test
	public void testRespondToMessageUsingMocks() throws Exception
	{
		Resource xmlFile = new ClassPathResource("in.xml");
		InputStream stream = xmlFile.getInputStream();
		String body = IOUtils.toString(stream);
		resultEndpoint.setMinimumExpectedMessageCount(1);

		// // Prepare the request message for the camel-cxf procedure
		 Map<Object, String> mapHeaders = new HashMap<Object, String>();
		 mapHeaders.put(CxfConstants.OPERATION_NAMESPACE,
		 "http:customer.service.esb.tecplata.com/");
		 mapHeaders.put(CxfConstants.OPERATION_NAME, "getCustomerByCuit");
		 inputProducerTemplate.requestBodyAndHeader("cxf:bean:CustomersServiceConsumerEndpoint",
		 body, mapHeaders);
		 inputProducerTemplate.requestBody(body);

		
//		CustomersService cxfClient = createCXFClient("http://localhost:8181/cxf/CustomersService");
//		Customer customerByCuit = cxfClient.getCustomerByCuit("1");
		
		resultEndpoint.assertIsSatisfied();

	}
}
