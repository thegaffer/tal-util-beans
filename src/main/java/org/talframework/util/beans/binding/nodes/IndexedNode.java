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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.binding.BindingErrorType;
import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

public class IndexedNode extends BaseCollectionNode implements BindingNode {

    public List<BindingNode> members;
    
    public IndexedNode(BindingNode parent, String name) {
        super(parent, name);
    }
    
    public BindingNode addProperty(String name, String key, Object val) {
        if( key == null ) throw new IllegalArgumentException("Cannot added a non-keyed property [" + name + "] to an IndexedNode: " + getNodeName());
        if( members == null ) members = new ArrayList<BindingNode>();
        
        int index = -1;
        try {
            index = Integer.parseInt(key);
        }
        catch( NumberFormatException e ) {}
        
        BindingNode node = null;
        if( index >= 0 && index < members.size() ) {
            node = members.get(index);
        }
        else if( index >= members.size() ) {
            for( int i = members.size() ; i <= index ; i++ ) members.add(null);
        }
        
        if( node == null ) {
            node = new ObjectNode(this, getNodeName());
            members.set(index, node);
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
            collection = applyArray(request, result, target, definition, members);
        }
        else if( Collection.class.isAssignableFrom(propertyType) ) {
            collection = applyCollection(request, result, target, definition, members);
        }
        else {
            result.addError(target, getNodeName(), getFullNodeName(), null, BindingErrorType.INVALID_MEMBER_INDEX.toString());
        }
        
        definition.write(target, getNodeName(), collection);
    }
}
