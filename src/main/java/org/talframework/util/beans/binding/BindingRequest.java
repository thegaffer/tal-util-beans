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

import java.beans.PropertyEditor;
import java.util.Map;

/**
 * This interface represents a request to perform binding
 * from an input map into a target object.
 *
 * @author Tom Spencer
 * @param <T> The type of target object
 */
public interface BindingRequest<T> {

    /**
     * @return The object we are binding into
     */
    public T getTarget();
    
    /**
     * @return The input we are add into
     */
    public Map<String, ?> getInput();
    
    /**
     * @return The object creator to use for nested objects
     */
    public BindingObjectCreator getObjectCreator();
    
    /**
     * Called to get the property editor for a non-string
     * type.
     * 
     * @param object The object we are acting upon
     * @param propertyType The type of the property we are binding into
     * @param property The name of the property we are binding into
     * @return The {@link PropertyEditor} for that property
     */
    public PropertyEditor getPropertyEditor(Object object, Class<?> propertyType, String property);
}
