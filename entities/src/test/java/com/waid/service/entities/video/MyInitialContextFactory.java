package com.waid.service.entities.video;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class MyInitialContextFactory implements InitialContextFactory {

	public final class MapContext implements Context {
		private Map<String, Object> map = new HashMap<String, Object>();

		public void unbind(String name) throws NamingException {
		}

		public void unbind(Name name) throws NamingException {
		}

		public void rename(String oldName, String newName)
				throws NamingException {
		}

		public void rename(Name oldName, Name newName) throws NamingException {
		}

		public Object removeFromEnvironment(String propName)
				throws NamingException {
			return null;
		}

		public void rebind(String name, Object obj) throws NamingException {
		}

		public void rebind(Name name, Object obj) throws NamingException {
		}

		public Object lookupLink(String name) throws NamingException {
			return null;
		}

		public Object lookupLink(Name name) throws NamingException {
			return null;
		}

		public Object lookup(String name) throws NamingException {
			return map.get(name);
		}

		public Object lookup(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NamingEnumeration<Binding> listBindings(String name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NamingEnumeration<Binding> listBindings(Name name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NamingEnumeration<NameClassPair> list(String name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NamingEnumeration<NameClassPair> list(Name name)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NameParser getNameParser(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public NameParser getNameParser(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public String getNameInNamespace() throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public Hashtable<?, ?> getEnvironment() throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public void destroySubcontext(String name) throws NamingException {
			// TODO Auto-generated method stub

		}

		public void destroySubcontext(Name name) throws NamingException {
			// TODO Auto-generated method stub

		}

		public Context createSubcontext(String name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public Context createSubcontext(Name name) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public String composeName(String name, String prefix)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public Name composeName(Name name, Name prefix) throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}

		public void close() throws NamingException {
			// TODO Auto-generated method stub

		}

		public void bind(String name, Object obj) throws NamingException {
			map.put(name, obj);
		}

		public void bind(Name name, Object obj) throws NamingException {
			map.put(name.toString(), obj);

		}

		public Object addToEnvironment(String propName, Object propVal)
				throws NamingException {
			// TODO Auto-generated method stub
			return null;
		}
	}

	private static Context defaultContext = null;

	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {
		if (defaultContext == null) {
			defaultContext = new MapContext();
		}
		return defaultContext;
	}

}
