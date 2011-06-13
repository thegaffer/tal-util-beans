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

import java.util.Map;

import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;

/**
 * Represents a node in the input. A node is a root
 * object, for example in the following:
 * 
 * <p><code><pre>object.name
 * object.child.name
 * object.reference[1].name
 * object.map[key].name</pre></code></p>
 * 
 * <p>In the above, object, child, reference and map would
 * all be represented as nodes. Name would be a property
 * of each of those nodes.</p>
 *
 * @author Tom Spencer
 */
public interface BindingNode {
    
    /**
     * @return The parent node to this one
     */
    public BindingNode getParent();

    /**
     * @return The name of the node
     */
    public String getNodeName();
    
    /**
     * @return The full node path
     */
    public String getFullNodeName();
    
    /**
     * @return The raw basic properties of this object as received in the input
     */
    public Map<String, Object> getValues();
    
    /**
     * Called during preparation to add a property to the
     * node.
     * 
     * @param name The property name
     * @param key The key if there is one
     * @param val The value of this property
     * @return The binding node that actually owns the property
     */
    public BindingNode addProperty(String name, String key, Object val);
    
    /**
     * Called for the node to bind in any members to the
     * target object.
     * 
     * @param request The {@link BindingRequest} for the current bind
     * @param target The target object to bind into
     */
    public void apply(BindingRequest<?> request, StandardBindingResult<?> result, Object target);
}
