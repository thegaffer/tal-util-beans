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

import java.beans.PropertyEditor;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.binding.BindingErrorType;
import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This class represents a single object we are binding
 * into. During preparation this nodes stores all 'simple'
 * properties into a map of values and all children into
 * relevant {@link BindingNode} instances.
 *
 * @author Tom Spencer
 */
public class ObjectNode extends BaseBindingNode {
    
    /** Holds the nodes basic values */
    private Map<String, Object> values = new HashMap<String, Object>();
    /** Holds the nodes child nodes */
    private Map<String, BindingNode> childNodes;

    public ObjectNode(BindingNode parent, String name) {
        super(parent, name);
    }
    
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> getValues() {
        return values;
    }
    
    public String getFullPropertyPath(String path) {
        String ret = getFullNodeName();
        ret += "." + path;
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public void apply(BindingRequest<?> request, StandardBindingResult<?> result, Object target) {
        BeanDefinition def = BeanDefinitionsSingleton.getInstance().getDefinition(target.getClass());
        
        // a. Apply properties
        for( String prop : values.keySet() ) {
            if( def.hasProperty(prop) && def.canWrite(prop) ) {
                Object val = values.get(prop);
                Class<?> expected = def.getPropertyType(prop);
                Object current = def.canRead(prop) ? def.read(target, prop) : null;
                
                if( expected.isInstance(val) ) {
                    def.write(target, prop, val);
                }
                else if( val instanceof String ) {
                    val = getRealValue(request, result, target, prop, expected, current, val.toString());
                    def.write(target, prop, val);
                }
                else if( val instanceof String[] ) {
                    String[] arr = (String[])val;
                    
                    if( expected.isArray() ) {
                        Object newArray = Array.newInstance(expected.getComponentType(), arr.length);
                        for( int i = 0 ; i < arr.length ; i++ ) {
                            Array.set(newArray, i, getRealValue(request, result, target, prop, expected.getComponentType(), null, arr[i]));
                        }
                        def.write(target, prop, newArray);
                    }
                    else if( Collection.class.isAssignableFrom(expected) ) {
                        Class<?> memberType = getMemberType(def, prop);
                        Collection<Object> collection = (Collection<Object>)createCollection(expected, arr.length);
                        for( int i = 0 ; i < arr.length ; i++ ) {
                            collection.add(getRealValue(request, result, target, prop, memberType, null, arr[i]));
                        }
                        def.write(target, prop, collection);
                    }
                    else {
                        result.addError(target, prop, getFullPropertyPath(prop), val, BindingErrorType.INVALID_ARRAY.toString());
                    }
                }
                else {
                    result.addError(target, prop, getFullPropertyPath(prop), val, BindingErrorType.INVALID_INPUT.toString());
                }
            }
        }
        
        // b. Call other nodes
        if( childNodes == null ) return;
        for( String nodeName : childNodes.keySet() ) {
            BindingNode node = childNodes.get(nodeName);
            if( def.hasProperty(nodeName) && def.canRead(nodeName) ) {
                Object current = def.read(target, nodeName);
                
                // FUTURE: I don't like handling like this, really should be consistent
                if( node instanceof ObjectNode ) {
                    if( current == null && def.canWrite(nodeName) ) {
                        current = request.getObjectCreator().createObject(target, def.getPropertyType(node.getNodeName()), node.getNodeName(), values);
                        def.write(target, nodeName, current);
                    }
                    
                    if( current != null ) {
                        node.apply(request, result, current);
                    }
                }
                else {
                    node.apply(request, result, target);
                }
            }
        }
    }
    
    /**
     * Helper to convert the string value into the real value given
     * the expected type.
     * 
     * @param request The {@link BindingRequest}
     * @param target The object we are binding into
     * @param property The name of the property
     * @param expected The expected type of the property
     * @param input The input string
     * @return The real value
     */
    private Object getRealValue(BindingRequest<?> request, StandardBindingResult<?> result, Object target, String property, Class<?> expected, Object current, String input) {
        Object ret = null;
        
        if( input == null ) {
            ret = null;
        }
        else if( expected.equals(String.class) ) {
            ret = input;
        }
        else {
            PropertyEditor editor = request.getPropertyEditor(target, expected, property);
            
            if( editor == null ) {
                result.addError(target, property, getFullPropertyPath(property), input, BindingErrorType.INVALID_TYPE.toString());
                ret = current;
            }
            else {
                editor.setAsText(input);
                ret = editor.getValue();
            }
        }
        
        return ret;
    }
    
    /**
     * This adds the given property to the node. If the
     * name contains periods, '.', then the child property
     * set is found and the property added to that.
     * 
     * @param name The full name of the property
     * @param val The value
     */
    public BindingNode addProperty(String name, String key, Object val) {
        if( key != null ) throw new IllegalArgumentException("Cannot added a keyed property [" + key + "] to an ObjectNode: " + getNodeName());
        
        BindingNode ret = null;
        
        String[] parts = splitProperty(name);
        
        if( parts.length == 1 ) {
            values.put(parts[0], val);
            ret = this;
        }
        
        else if( parts.length > 1 ){
            if( childNodes == null ) childNodes = new HashMap<String, BindingNode>();
            
            String childName = parts[1];
            String childKey = parts.length > 2 ? parts[2] : null;
            
            BindingNode childNode = childNodes.get(parts[1]);
            
            if( childKey == null ) {
                if( childNode != null && !(childNode instanceof ObjectNode) ) throw new IllegalArgumentException("Invalid input a child seems to have both indexed/keyed access and straight access: " + name);
            
                if( childNode == null ) {
                    childNode = new ObjectNode(this, childName);
                    childNodes.put(childName, childNode);
                }
            }
            
            else {
                boolean isNumeric = isNumeric(childKey);
                
                if( childNode != null ) {
                    if( childNode instanceof IndexedNode ) {
                        if( !isNumeric ) {
                            childNode = new KeyedNode((IndexedNode)childNode);
                            childNodes.put(childName, childNode);
                        }
                    }
                    else if( !(childNode instanceof KeyedNode) ) {
                        throw new IllegalArgumentException("Invalid input a child seems to have both indexed/keyed access and straight access: " + name);
                    }
                }
                else {
                    childNode = isNumeric ? new IndexedNode(this, childName) : new KeyedNode(this, childName);
                    childNodes.put(childName, childNode);
                }
            }
            
            // Get child to add property
            ret = childNode.addProperty(parts[0], childKey, val);
        }
        
        return ret;
    }
    
    /**
     * This internal helper splits a property into it;s
     * constituent parts. The return string array will
     * consist of between 1 and 3 things:
     * <ol>
     * <li>The property, either the prop or the bit after the .
     * <li>The child object on which the element is placed (before the .)
     * <li>The key or index if the the child has the [] notation.
     * </ol>
     * 
     * @param prop The original property
     * @return Its parts
     */
    private String[] splitProperty(String prop) {
        int index = prop.indexOf('.');
        if( index > 0 ) {
            String node = prop.substring(0, index);
            prop = prop.substring(index + 1);
            
            int keyIndex = node.indexOf('[');
            int endKey = keyIndex > 0 ? node.indexOf(']', keyIndex) : -1;
            
            // Return the prop, child and key
            if( keyIndex > 0 && endKey > keyIndex + 1 ) {
                String key = node.substring(keyIndex + 1, endKey);
                String nodeName = node.substring(0, keyIndex);
                
                return new String[]{prop, nodeName, key};
            }
            
            // Return the prop and the child
            else {
                return new String[]{prop, node};
            }
        }
        
        // Simply return the prop
        else {
            return new String[]{prop};
        }
    }
    
    /**
     * Helper to determine if the given key is numeric or not
     * 
     * @param key
     * @return
     */
    private boolean isNumeric(String key) {
        try {
            Long.parseLong(key);
            return true;
        }
        catch( NumberFormatException e ) {
            return false;
        }
    }
}
