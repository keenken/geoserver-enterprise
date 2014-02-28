/* Copyright (c) 2011 GeoSolutions - http://www.geo-solutions.it/.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package it.geosolutions.geoserver.jms.impl.handlers.configuration;

import it.geosolutions.geoserver.jms.JMSEventHandler;
import it.geosolutions.geoserver.jms.JMSEventHandlerSPI;

import com.thoughtworks.xstream.XStream;

/**
 * Abstract class which use Xstream as message serializer/de-serializer.
 * 
 * You have to extend this class to implement synchronize method.
 * 
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 *
 */
public abstract class JMSConfigurationHandler<TYPE> extends
		JMSEventHandler<String, TYPE> {
	public JMSConfigurationHandler(final XStream xstream,
			Class<JMSEventHandlerSPI<String, TYPE>> clazz) {
		super(xstream,clazz);
		// omit not serializable fields
		omitFields(xstream);
	}

	/**
	 * here you may modify XStream [de]serialization adding 
	 * omitFields and all other changes supported by XStream 
	 * 
	 * @param xstream a not null and initted XStream to use
	 */
	protected abstract void omitFields(final XStream xstream);
	
	@Override
	public String serialize(TYPE  event) throws Exception {
		return xstream.toXML(event);
	}

	@Override
	public TYPE deserialize(String s) throws Exception {
		final Object source = xstream.fromXML(s);
		if (source!=null) {
			return (TYPE) source;
		} else {
			throw new IllegalArgumentException(this.getClass().getCanonicalName()+" is unable to deserialize the object:"+s);
		}
		
	}
}
