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

/**
 * This interface represents an object that can create new 
 * instances of other objects found in the hierarchy.
 *
 * @author Tom Spencer
 */
public interface BindingObjectCreator {

    /**
     * Called to create a new object that will be set at
     * the given property name of the owning object.
     * 
     * @param owner The object that will hold this object
     * @param expected The expected type of the owner
     * @param name The name of the property in owning object
     * @param values The raw basic values (excluding children) or the object
     * @return The new object to bind into
     */
    public Object createObject(Object owner, Class<?> expected, String name, Map<String, Object> values);
}
