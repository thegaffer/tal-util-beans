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
package org.talframework.util.beans.cloner;

import java.util.Collection;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This object clones a bean into an instance of itself
 * using a {@link BeanDefinition}. This is very simple
 * and simply maps every balanced property of the class
 * into itself. Thus, any properties that are read-only, 
 * or set only, are not mapped over.
 * 
 * TODO: If the object support cloneable, use that first!
 *
 * @author Tom Spencer
 */
public class GenericCloner implements Cloner {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> T shallowClone(T object) {
        BeanDefinition definition = BeanDefinitionsSingleton.getInstance().getDefinition(object.getClass());
        T dest = (T)definition.newInstance();
        return internalShallowClone(definition, object, dest);
    }
    
    /**
     * {@inheritDoc}
     */
    public <T> T shallowClone(T source, T dest) {
        BeanDefinition definition = BeanDefinitionsSingleton.getInstance().getDefinition(source.getClass());
        return internalShallowClone(definition, source, dest);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <T> T deepClone(T object) {
        BeanDefinition definition = BeanDefinitionsSingleton.getInstance().getDefinition(object.getClass());
        T dest = (T)definition.newInstance();
        return internalDeepClone(definition, object, dest);
    };
    
    /**
     * {@inheritDoc}
     */
    public <T> T deepClone(T object, T clone) {
        BeanDefinition definition = BeanDefinitionsSingleton.getInstance().getDefinition(object.getClass());
        return internalDeepClone(definition, object, clone);
    };

    /**
     * Internal method that does the actual work of a shallow clone.
     * 
     * @param definition The bean definition to use
     * @param source The source object
     * @param dest The destination object
     * @param deep Indicates if a deep clone is required
     * @return The returned object
     */
    protected <T> T internalShallowClone(BeanDefinition definition, T source, T dest) {
        // Iterate balanced properties
        Collection<String> props = definition.getBalancedProperties();
        for( String prop : props ) {
            definition.write(dest, prop, definition.read(source, prop));
        }
        
        return dest;
    } 
    
    /**
     * Internal method that does the actual work of a deep clone.
     * 
     * @param definition The bean definition to use
     * @param source The source object
     * @param dest The destination object
     * @param deep Indicates if a deep clone is required
     * @return The returned object
     */
    protected <T> T internalDeepClone(BeanDefinition definition, T source, T dest) {
        // Iterate balanced properties
        Collection<String> props = definition.getBalancedProperties();
        for( String prop : props ) {
            // TODO: Perform the deep clone
            definition.write(dest, prop, definition.read(source, prop));
        }
        
        return dest;
    } 
}
