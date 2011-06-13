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


/**
 * This condition cloner only clones properties from the
 * source object to the existing target if the properties
 * have been set. For any property it must have a
 * 'boolean is{Name}Set()' property for any real property
 * we want to merge. This is not part of the JavaBeans
 * specification, but it something that is added in other
 * parts of Tal.
 * 
 * <p>Note: This conditional cloner has no singleton
 * class that allows these to be registered. It also
 * does not support deep cloning - it is a specialised
 * use if if required shallow cloning and was originally
 * created to support merging of a bean received via a
 * web service into the servers copy of that bean.</p>
 *
 * @author Tom Spencer
 */
public class GenericMerger extends GenericCloner {

    protected <T> T internalShallowClone(BeanDefinition definition, T source, T dest) {
        Collection<String> props = definition.getBalancedProperties();
        for( String prop : props ) {
            String isSetProp = prop + "Set";
            if( definition.hasProperty(isSetProp, boolean.class) ) {
                boolean isSet = definition.read(source, isSetProp, Boolean.class);
                if( isSet ) definition.write(dest, prop, definition.read(source, prop));
            }
        }
        
        return dest;
    }
    
    protected <T> T internalDeepClone(BeanDefinition definition, T source, T dest) {
        throw new UnsupportedOperationException("Deep copy from merger not yet supported");
    }
}
