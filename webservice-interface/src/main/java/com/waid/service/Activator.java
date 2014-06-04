package com.waid.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	
    public void start(BundleContext context) throws Exception {
    	System.out.println("loaded webservice-interfaces");
    }

    public void stop(BundleContext context) throws Exception {
    	System.out.println("stopped webservice-interfaces");
    }

}
