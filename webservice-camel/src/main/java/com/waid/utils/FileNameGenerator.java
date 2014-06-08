package com.waid.utils;

import com.waid.webservice.InputFetchVideo;


/**
 * iPlain java class to be used for filename pgenrtion baed on the reported incident
 * @author kodjobaah
 *
 */
public class FileNameGenerator {
	
	public String generateFilename(InputFetchVideo input) {
		// compute the filename
		return "Incident" + input.getVideoId() + ".txt";
	}

}



