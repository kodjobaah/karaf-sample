package com.waid.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.bean.BeanLanguage;
import org.apache.camel.processor.interceptor.Tracer;

import com.waid.utils.FileNameGenerator;
import com.waid.webservice.InputFetchResults;
import com.waid.webservice.InputFetchVideo;
import com.waid.webservice.OutputFetchResults;
import com.waid.webservice.OutputFetchVideo;


public class GetResultsRoute extends RouteBuilder {
  
	
    public void configure() throws Exception {
    	
    	 // add tracer as an interceptor so it will log the exchange executions at runtime
        // this can aid us to understand/see how the exchanges is routed etc.
        Tracer tracer = new Tracer();
        
        getContext().addInterceptStrategy(tracer);
        // webservice responses
    	OutputFetchResults ok = new OutputFetchResults();
    		
    	ok.setMessage("done");

        //OutputReportIncident accepted = new OutputReportIncident();
        //accepted.setCode("Accepted");

    	SessionInfo sessionInfo = (SessionInfo)getContext().getRegistry().lookupByName("sessionInfo");
        from("{{cxf.bean.results.cxfEndpoint}}")
        .convertBodyTo(InputFetchResults.class)
        .beanRef("sessionInfo","setSessionId")
        .from("{{results.queue}}")
        .filter().method("sessionInfo","isRequiredResponse")
        .transform(constant(ok));
        
        //.to("activemq:queue:video.info.queue");
        
          //  .convertBodyTo(InputReportIncident.class)
        //    .setHeader(Exchange.FILE_NAME, constant("request-${date:now:yyyy-MM-dd-HHmmssSSS}"))
        //    .wireTap("file://target/inbox/")
         //   .choice().when(simple("${body.givenName} == 'Claus'"))
          //      .transform(constant(ok))
         //   .otherwise();
            //    .transform(constant(accepted));
    }
}