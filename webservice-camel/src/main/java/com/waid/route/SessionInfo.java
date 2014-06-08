package com.waid.route;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;

import com.waid.webservice.InputFetchResults;

public class SessionInfo {

	public static String SESSION_HEADER_INFO = "WAID_SESSION_HEADER_INFO";
	public ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();
	public ThreadLocal<String> setSessionId(InputFetchResults inputFetchResults) {
		   System.out.println("SETTING SESSION ID");
			userThreadLocal.set(inputFetchResults.getId());
			return userThreadLocal;
	}
	
	@Handler
	public boolean isRequiredResponse(Exchange ex) {
		System.out.println("CHECKING REQUIRED RESPONSE");
		String sessionId = (String)ex.getIn().getHeader(SESSION_HEADER_INFO);
		return userThreadLocal.get().equalsIgnoreCase(sessionId);
	}
 }
