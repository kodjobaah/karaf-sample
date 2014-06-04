package com.waid.service.entities.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.interceptor.Tracer;

import com.waid.service.converter.VideoInputConverter;
import com.waid.service.entities.entity.Video;
import com.waid.webservice.InputFetchVideo;

public class StoreVideoRoute extends RouteBuilder {
  
	
    public void configure() throws Exception {
    	
    	 // add tracer as an interceptor so it will log the exchange executions at runtime
        // this can aid us to understand/see how the exchanges is routed etc.
        Tracer tracer = new Tracer();
        
        getContext().addInterceptStrategy(tracer);

        getContext().getTypeConverterRegistry().addTypeConverter(Video.class, InputFetchVideo.class, new VideoInputConverter());
    	
        
        from("activemq:queue:video.info.queue")
        .convertBodyTo(InputFetchVideo.class)
        .beanRef("videoService","addVideo")
        .to("log:foo");
       }
}