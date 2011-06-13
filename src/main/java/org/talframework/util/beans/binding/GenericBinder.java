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
package org.talframework.util.beans.binding;

import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.binding.nodes.ObjectNode;

/**
 * This implementation of the binder interface uses the
 * {@link BeanDefinition} interface to map into all settable
 * properties which are in the input. 
 * 
 * <p>There are limitations in the usage of this default
 * binder. These are best illustrated by examples of a 
 * property file:</p>
 * <code><pre>
 * form[element].property=x             // Error: Can't handle collections/maps in root object
 * form.childa.name=y
 * form.childa[key].name=z          // Error! Can't handle childa first being non-keyed/indexed and then it is
 * form.childb[key][key2].name = z  // Error! Can't handle collections of collections or maps of maps
 * form.childc[0].name=abc
 * form.childc[2].name=xyz          // Warning! In the end childc[2] will be at index 1 because there is no index 1
 * </pre></code>
 * 
 * <p>The {@link GenericBinder} when used with the 
 * {@link StandardBindingObjectCreator} will deal with
 * interfaces via a Proxy object handling all of its
 * getters and setters. Provide a custom object creator
 * if this is not what you want.</p>
 * 
 * FUTURE: Cache PropertyNodes so we can find them straight away if they exist
 *
 * @author Tom Spencer
 */
public class GenericBinder implements Binder {

    public <T> BindingResult<T> bind(BindingRequest<T> request) {
        StandardBindingResult<T> result = new StandardBindingResult<T>(request.getTarget());

        // a. Organise input
        ObjectNode root = new ObjectNode(null, "target");
        Map<String, ?> input = request.getInput();
        for( String prop : input.keySet() ) {
            root.addProperty(prop, null, input.get(prop));
        }
        
        // b. Apply input
        root.apply(request, result, result.getTarget());

        return result;
    }
}
