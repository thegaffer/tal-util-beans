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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.binding.BindingErrorType;
import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This {@link BindingNode} represents a node that contains
 * keyed entries, by that we mean non-numeric keys in
 * properties such as:
 * 
 * <p><code>object.child[key].name</code></p>
 * 
 * <p>This node specifically would represent child and it
 * would contain {@link ObjectNode} instances against each
 * key found.</p>
 *
 * @author Tom Spencer
 */
public class KeyedNode extends BaseCollectionNode {
    
    /** Holds the members of this node */
    Map<String, BindingNode> members;

    public KeyedNode(BindingNode parent, String name) {
        super(parent, name);
    }
    
    public KeyedNode(IndexedNode node) {
        super(node.getParent(), node.getNodeName());
        
        throw new IllegalArgumentException("Cannot convert Indexed node to keyed node at present");
    }
    
    public BindingNode addProperty(String name, String key, Object val) {
        if( key == null ) throw new IllegalArgumentException("Cannot added a non-keyed property [" + name + "] to an KeyedNode: " + getNodeName());
        if( members == null ) members = new HashMap<String, BindingNode>();
        
        BindingNode node = members.get(key);
        if( node == null ) {
            node = new ObjectNode(this, getNodeName());
            members.put(key, node);
        }
        
        return node.addProperty(name, null, val);
    }
    
    /**
     * Applies the nodes to the target
     */
    public void apply(BindingRequest<?> request, StandardBindingResult<?> result, Object target) {
        BeanDefinition definition = BeanDefinitionsSingleton.getInstance().getDefinition(target.getClass());
        if( !definition.hasProperty(getNodeName()) || !definition.canWrite(getNodeName()) ) return;
        
        Object collection = null;
        Class<?> propertyType = definition.getPropertyType(getNodeName());
        if( propertyType.isArray() ) {
            collection = applyArray(request, result, target, definition, members != null ? members.values() : null);
            definition.write(target, getNodeName(), collection);
        }
        else if( Collection.class.isAssignableFrom(propertyType) ) {
            collection = applyCollection(request, result, target, definition, members != null ? members.values() : null);
            definition.write(target, getNodeName(), collection);
        }
        else if( Map.class.isAssignableFrom(propertyType) ) {
            collection = applyMap(request, result, target, definition, members);
            definition.write(target, getNodeName(), collection);
        }
        else {
            result.addError(target, getNodeName(), getFullNodeName(), null, BindingErrorType.INVALID_MEMBER_KEY.toString());
        }
    }
}
