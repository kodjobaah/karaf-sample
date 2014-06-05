package com.waid.service.converter;

import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import com.waid.service.entities.entity.Video;
import com.waid.service.entities.entity.Viewer;
import com.waid.webservice.InputFetchVideo;

public class VideoInputConverter extends TypeConverterSupport {
	 
	@SuppressWarnings("unchecked")
	public <T> T convertTo(Class<T> type, Exchange exchange, Object value)
			throws TypeConversionException {
	
		InputFetchVideo fetchVideo = (InputFetchVideo)value;
		// converter from value to the MyOrder bean
	   Video video = new Video(fetchVideo.getVideoId());
	   for(String name: fetchVideo.getName()) {
			Viewer viewer = new Viewer();
			viewer.setName(name);
			video.addViewer(viewer);
		}
       
	      return (T) video;
	}
}