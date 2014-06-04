package com.waid.service.camel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	
    public void start(BundleContext context) throws Exception {
    	System.out.println("loaded webservice-camel");
    }

    public void stop(BundleContext context) throws Exception {
    	System.out.println("stopped webservice-camel");
    }

}
