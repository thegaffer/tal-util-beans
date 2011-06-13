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
package org.talframework.util.beans.binding.nodes;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.talframework.util.beans.BeanDefinition;

/**
 * This base class provides support for all {@link BindingNode}
 * classes.
 *
 * @author Tom Spencer
 */
public abstract class BaseBindingNode implements BindingNode {

    private final BindingNode parent;
    private final String nodeName;
    
    public BaseBindingNode(BindingNode parent, String name) {
        this.parent = parent;
        this.nodeName = name;
    }
    
    public BindingNode getParent() {
        return parent;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getNodeName() {
        return nodeName;
    }
    
    /**
     * {@inheritDoc}
     */
    public String getFullNodeName() {
        String ret = null;
        if( parent != null ) ret = getFullNodeName() + ".";
        if( !(this instanceof ObjectNode) || parent instanceof ObjectNode ) ret += getNodeName();
        return null;
    }
    
    /**
     * Helper for the other classes to create collections with.
     * This will create either an array, list, set or map as
     * governed.
     * 
     * @param definition
     * @param max
     * @return
     */
    protected Object createCollection(Class<?> propertyType, int max) {
        Object ret = null;
        
        if( propertyType.isArray() ) {
            ret = Array.newInstance(propertyType.getComponentType(), max);
        }
        
        else if( List.class.isAssignableFrom(propertyType) ) {
            if( !propertyType.isInterface() ) ret = createClass(propertyType);
            else if( List.class.equals(propertyType) ) ret = new ArrayList<Object>();
            else throw new IllegalArgumentException("Unable to create collection: " + propertyType);
        }
        
        else if( Set.class.isAssignableFrom(propertyType) ) {
            if( !propertyType.isInterface() ) ret = createClass(propertyType);
            else if( SortedSet.class.equals(propertyType) ) ret = new TreeSet<Object>();
            else if( Set.class.equals(propertyType) ) ret = new HashSet<Object>();
            else throw new IllegalArgumentException("Unable to create collection: " + propertyType);
        }
        
        else if( Map.class.isAssignableFrom(propertyType) ) {
            if( !propertyType.isInterface() ) ret = createClass(propertyType);
            else if( Map.class.equals(propertyType) ) ret = new HashMap<Object, Object>();
            else if( SortedMap.class.equals(propertyType) ) ret = new TreeMap<Object, Object>();
            else throw new IllegalArgumentException("Unable to create collection: " + propertyType);
        }
        
        else {
            throw new IllegalArgumentException("Unable to create collection: " + propertyType);
        }
        
        return ret;
    }
    
    /**
     * Helper to create collection/map types in one line
     * 
     * @param type The type to create
     * @return The new instance
     */
    private Object createClass(Class<?> type) {
        if( type.isInterface() ) return null;
        
        try {
            return type.newInstance();
        }
        catch( RuntimeException e ) {
            throw e;
        }
        catch( Exception e ) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Determines the member type based on either the arrays
     * component type or the generic type argument to the collection.
     * 
     * @param definition The definition of the target object
     * @return The member type
     */
    protected Class<?> getMemberType(BeanDefinition definition, String propName) {
        Class<?> propertyType = definition.getPropertyType(propName);
        
        Class<?> ret = null;
        if( propertyType.isArray() ) {
            return propertyType.getComponentType();
        }
        
        else if( Collection.class.isAssignableFrom(propertyType) ) {
            Type type = definition.getPropertyGenericType(propName);
            if( type instanceof ParameterizedType ) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
            
                if( parameterizedType.getActualTypeArguments() != null &&
                        parameterizedType.getActualTypeArguments().length == 1 ) {
                    Type memberType = parameterizedType.getActualTypeArguments()[0];
                    if( memberType instanceof Class<?> ) ret = (Class<?>)memberType;
                }
            }
        }
        
        else if( Map.class.isAssignableFrom(propertyType) ) {
            Type type = definition.getPropertyGenericType(propName);
            if( type instanceof ParameterizedType ) {
                ParameterizedType parameterizedType = (ParameterizedType)type;
            
                if( parameterizedType.getActualTypeArguments() != null &&
                        parameterizedType.getActualTypeArguments().length == 2 ) {
                    Type memberType = parameterizedType.getActualTypeArguments()[1];
                    if( memberType instanceof Class<?> ) ret = (Class<?>)memberType;
                }
            }
        }
        
        return ret;        
    }
}
