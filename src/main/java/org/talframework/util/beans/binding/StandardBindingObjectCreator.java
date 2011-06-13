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
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This default implementation of the {@link BindingObjectCreator}
 * uses the {@link BeanDefinition} to create an instance of the
 * expected class.
 * 
 * @author Tom Spencer
 */
public class StandardBindingObjectCreator implements BindingObjectCreator {

    /**
     * Simply uses {@link BeanDefinition} on the expected class
     * to create a new instance
     */
    public Object createObject(Object owner, Class<?> expected, String name, Map<String, Object> values) {
        BeanDefinition def = BeanDefinitionsSingleton.getInstance().getDefinition(expected);
        return def.newInstance();
    }
}
