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
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.binding.BindingObjectCreator;
import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;

/**
 * This base class provides support for the Indexed and
 * Keyed nodes by creating the collection and determining
 * the element type.
 *
 * @author Tom Spencer
 */
public abstract class BaseCollectionNode extends BaseBindingNode {
    
    /** Held so that during apply we can generate property path */
    private String currentKey;

    public BaseCollectionNode(BindingNode parent, String name) {
        super(parent, name);
    }
    
    /**
     * The collection nodes do not hold any properties directly
     */
    public Map<String, Object> getValues() {
        return null;
    }
    
    @Override
    public String getFullNodeName() {
        String ret = super.getFullNodeName();
        if( currentKey != null ) ret += "[" + currentKey + "]";
        return ret;
    }
    
    /**
     * This helper will apply all nodes to an array
     * 
     * @param creator The creator to use
     * @param target The target object
     * @param definition The definition of the target
     * @param nodes The nodes to apply
     * @return The value to set on the target by caller
     */
    protected Object applyArray(BindingRequest<?> request, StandardBindingResult<?> result, Object target, BeanDefinition definition, Collection<BindingNode> nodes) {
        Class<?> expected = getMemberType(definition, getNodeName());
        
        int size = nodes.size();
        Object collection = definition.canRead(getNodeName()) ? definition.read(target, getNodeName()) : null;
        int currentSize = collection != null ? Array.getLength(collection) : 0;
        
        // Create a new array
        Object newCollection = Array.newInstance(expected, size);
        if( size > 0 && currentSize > 0 ) System.arraycopy(collection, 0, newCollection, 0, currentSize > size ? size : currentSize);
        collection = newCollection;
        
        // Map in each element
        BindingObjectCreator creator = request.getObjectCreator();
        int element = 0;
        for( BindingNode node : nodes ) {
            if( node == null ) continue;
            currentKey = Integer.toString(element);
            
            Object member = Array.get(collection, element);
            if( member == null ) {
                member = creator.createObject(target, expected, getNodeName(), node.getValues());
                Array.set(collection, element, member);
            }
            
            node.apply(request, result, member);
            
            ++element;
        }
        
        return collection;
    }
    
    /**
     * This helper will apply all nodes to a collection (list or set)
     * 
     * @param creator The creator to use
     * @param target The target object
     * @param definition The definition of the target
     * @param nodes The nodes to apply
     * @return The value to set on the target by caller
     */
    @SuppressWarnings("unchecked")
    protected Object applyCollection(BindingRequest<?> request, StandardBindingResult<?> result, Object target, BeanDefinition definition, Collection<BindingNode> nodes) {
        Class<?> expected = getMemberType(definition, getNodeName());
        
        Collection<Object> currentCollection = definition.canRead(getNodeName()) ? definition.read(target, getNodeName(), Collection.class) : null;
        Collection<Object> collection = (Collection<Object>)createCollection(definition.getPropertyType(getNodeName()), nodes != null ? nodes.size() : 0);
        
        Iterator<BindingNode> it = nodes.iterator();
        Iterator<Object> itExisting = currentCollection != null ? currentCollection.iterator() : null;
        
        int element = 0;
        BindingObjectCreator creator = request.getObjectCreator();
        while( it.hasNext() ) {
            BindingNode node = it.next();
            if( node != null ) {
                currentKey = Integer.toString(element);
                
                Object member = (itExisting != null && itExisting.hasNext()) ? itExisting.next() : null;
                if( member == null ) {
                    member = creator.createObject(target, expected, getNodeName(), node.getValues());
                }
                
                node.apply(request, result, member);
                collection.add(member);
            }
            
            element++;
        }
        
        return collection;
    }
    
    /**
     * This helper will apply all nodes to a map
     * 
     * @param creator The creator to use
     * @param target The target object
     * @param definition The definition of the target
     * @param nodes The nodes to apply
     * @return The value to set on the target by caller
     */
    @SuppressWarnings("unchecked")
    protected Object applyMap(BindingRequest<?> request, StandardBindingResult<?> result, Object target, BeanDefinition definition, Map<String, BindingNode> nodes) {
        Class<?> expected = getMemberType(definition, getNodeName());
        
        Map<Object, Object> currentMap = definition.canRead(getNodeName()) ? definition.read(target, getNodeName(), Map.class) : null;
        Map<Object, Object> map = (Map<Object, Object>)createCollection(definition.getPropertyType(getNodeName()), nodes != null ? nodes.size() : 0);
        
        BindingObjectCreator creator = request.getObjectCreator();
        Iterator<String> it = nodes.keySet().iterator();
        while( it.hasNext() ) {
            String key = it.next();
            currentKey = key;
            
            // FUTURE: Convert key to real key type??
            BindingNode node = nodes.get(key);
            
            Object member = currentMap != null && currentMap.containsKey(key) ? currentMap.get(key) : null;
            if( member == null ) {
                member = creator.createObject(target, expected, getNodeName(), node.getValues());
            }
                
            node.apply(request, result, member);
            map.put(key, member);
        }
        
        return map;
    }
}
