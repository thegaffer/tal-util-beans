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

import java.util.concurrent.ConcurrentHashMap;


/**
 * This singleton (per classloader) class provides access
 * to a cloner for a JavaBean. If a cloner has not been
 * pre-registered for the class then a default one is
 * created.
 *
 * @author Tom Spencer
 */
public final class ClonersSingleton {
    /** Holds the single (per classloader) instance of this class */
    private static final ClonersSingleton INSTANCE = new ClonersSingleton();
    
    /** Holds the simple registered cloners */
    private final ConcurrentHashMap<String, Cloner> simpleCloners;
    /** Holds the default generic cloner */
    private final Cloner defaultCloner = new GenericCloner();
    
    /**
     * Private constructor
     */
    private ClonersSingleton() {
        simpleCloners = new ConcurrentHashMap<String,Cloner>();
    }
    
    /**
     * @return The single (per classloader) instance of the cloner
     */
    public static ClonersSingleton getInstance() {
        return INSTANCE;
    }
    
    /**
     * Call to get the cloner for a particular bean type.
     * 
     * @param beanType The bean type
     * @return The cloner for that type
     */
    public Cloner getCloner(Class<?> beanType) {
        Cloner ret = defaultCloner;
        
        String typeName = beanType.getName();
        if( simpleCloners.size() > 0 && simpleCloners.containsKey(typeName) ) {
            ret = simpleCloners.get(typeName);
        }
        
        return ret;
    }
    
    /**
     * Call to register a cloner for a particular bean type.
     * 
     * @param beanType The type of bean this cloner is for
     * @param cloner The cloner to register
     */
    public void registerCloner(Class<?> beanType, Cloner cloner) {
        simpleCloners.put(beanType.getName(), cloner);
    }
}
