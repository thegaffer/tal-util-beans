/**
 * Copyright (C) 2011 Tom Spencer <thegaffer@tpspencer.com>
 *
 * This file is part of TAL.
 *
 * TAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TAL. If not, see <http://www.gnu.org/licenses/>.
 *
 * Note on dates: Year above is the year this code was built. This
 * project first created in 2008. Code was created between these two
 * years inclusive.
 */
package org.talframework.util.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.talframework.tal.aspects.annotations.TraceWarn;

/**
 * This class is used to create bind objects to an interface
 * where the bind class is not specified
 * 
 * FUTURE: Could work out and cache all methods up front
 * 
 * @author Tom Spencer
 */
public class InterfaceAdaptor implements InvocationHandler {

	/** Member holds the properties of the interface */
	private Map<String, Object> props = null;
	
	/**
	 * Handles if the method is a simple get, set or is.
	 * Does not currently handle indexed getter/setter
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String name = method.getName();
		
		Object ret = null;
		if( name.startsWith("get") ) {
			name = name.substring(3);
			if( props != null ) ret = props.get(name);
			if( ret == null ) ret = getDefaultReturn(method.getReturnType());
		}
		else if( name.startsWith("is") ) {
			name = name.substring(2);
			if( props != null ) ret = props.get(name);
			if( ret == null ) ret = false;
		}
		else if( name.startsWith("set") ) {
			name = name.substring(3);
			Object val = args.length > 0 ? args[0] : null;
			if( val != null ) {
				if( props == null ) props = new HashMap<String, Object>();
				props.put(name, val);
			}
			else if( props != null ) {
				props.remove(name);
			}
		}
		else if( name.equals("toString") ) {
			return props != null ? props.toString() : "Empty Proxy";
		}
		else if( name.equals("hashCode") ) {
			return props != null ? props.hashCode() : 30;
		}
		else if( name.equals("equals") ) {
			return props != null ? props.equals(args[0]) : false;
		}
		else {
		    cannotProcessMethod(method);
			return null;
		}
		
		return ret;
	}
	
	/**
	 * Helper to get the default return which is null unless the
	 * return type is a primitive
	 * 
	 * @param returnType The return type
	 * @return The value to return by default
	 */
	private Object getDefaultReturn(Class<?> returnType) {
		Object ret = null;
		
		// Default primitives
		if( returnType.isPrimitive() ) {
			if( returnType.equals(Boolean.TYPE) ) ret = false;
			if( returnType.equals(Long.TYPE) ) ret = (long)0;
			if( returnType.equals(Integer.TYPE) ) ret = 0;
			if( returnType.equals(Double.TYPE) ) ret = 0.0;
			if( returnType.equals(Character.TYPE) ) ret = (char)0;
			if( returnType.equals(Short.TYPE) ) ret = (short)0;
			if( returnType.equals(Byte.TYPE) ) ret = (byte)0;
			if( returnType.equals(Float.TYPE) ) ret = 0.0f;
		}
		
		return ret;
	}

	/**
	 * Method is present to call so the aspect loggers can log it.
	 */
	@TraceWarn
	private void cannotProcessMethod(Method method) {
	}
}
