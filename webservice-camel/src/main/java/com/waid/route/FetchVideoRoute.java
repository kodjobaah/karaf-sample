package com.waid.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.language.bean.BeanLanguage;
import org.apache.camel.processor.interceptor.Tracer;

import com.waid.utils.FileNameGenerator;
import com.waid.webservice.InputFetchVideo;
import com.waid.webservice.OutputFetchVideo;


public class FetchVideoRoute extends RouteBuilder {
  
	
    public void configure() throws Exception {
    	
    	 // add tracer as an interceptor so it will log the exchange executions at runtime
        // this can aid us to understand/see how the exchanges is routed etc.
        Tracer tracer = new Tracer();
        
        getContext().addInterceptStrategy(tracer);
        // webservice responses
    	OutputFetchVideo ok = new OutputFetchVideo();
    	ok.setName("testing something");

        //OutputReportIncident accepted = new OutputReportIncident();
        //accepted.setCode("Accepted");

        from("{{cxf.bean.cxfEndpoint}}")
        .convertBodyTo(InputFetchVideo.class)
		.wireTap("seda:save")
        .setHeader(Exchange.FILE_NAME,BeanLanguage.bean(FileNameGenerator.class,"generateFilename"))
		.pipeline("velocity:MailBody.vm","file://target/subfolder")
        .log(LoggingLevel.INFO,"com.waid.webservice","video to fetch ${body}")
        .transform(constant(ok));
        
        from("seda:save")
        .to("activemq:queue:video.info.queue");
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