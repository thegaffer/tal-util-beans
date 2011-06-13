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
package org.talframework.util.beans.definition;

import java.util.concurrent.ConcurrentHashMap;

import org.talframework.util.beans.BeanDefinition;

/**
 * This class provides access to bean definitions. If the
 * definition does not exist then a default one is created
 * and then cache for all future requests. The caching uses
 * a ConcurrentHashMap so is quite fast and does not block
 * on reads.
 * 
 * <p>This class acts as a singleton per classloader.</p>
 *
 * @author Tom Spencer
 */
public final class BeanDefinitionsSingleton {
    /** Static member holds the single (per classloader) instance */
    private static final BeanDefinitionsSingleton INSTANCE = new BeanDefinitionsSingleton();
    
    /** Member holds the bean definitions we know about */
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitions;
    
    /**
     * Hidden constructor
     */
    private BeanDefinitionsSingleton() {
        this.beanDefinitions = new ConcurrentHashMap<String, BeanDefinition>();
    }
    
    /**
     * @return The single (per classloader) instance of this class
     */
    public static BeanDefinitionsSingleton getInstance() {
        return INSTANCE;
    }
    
    /**
     * This method returns a bean definition for the given
     * type (class). If one does not exist one is created 
     * and added to the internal map for future use. This
     * method does not block on read access.
     * 
     * @param type The type we want the definition for
     * @return The bean definition
     */
    public BeanDefinition getDefinition(Class<?> type) {
        BeanDefinition ret = beanDefinitions.get(type.getName());
        if( ret == null ) {
            ret = new BeanDefinitionImpl(type);
            beanDefinitions.put(type.getName(), ret);
        }
        
        return ret;
    }

    /**
     * This method allows you to add on an arbitrary bean
     * definition instead of the default one that is created.
     * 
     * @param definition The bean definition to add
     */
    public void addDefinition(BeanDefinition definition) {
        this.beanDefinitions.put(definition.getType().getName(), definition);
    }
}
