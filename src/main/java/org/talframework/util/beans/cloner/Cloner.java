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


/**
 * This interface represents an object that can clone an
 * instance of a class into another instance of the same
 * class. 
 * 
 * <p>Instances of this class can be obtained from and
 * registered with the {@link ClonersSingleton}. The
 * generic cloner uses the singleton when performing
 * deep clones and it comes across properties it does
 * not understand.</p>
 * 
 * @author Tom Spencer
 */
public interface Cloner {
    
    /**
     * Call this method to clone an object. Every set of
     * properties that has both a getter and a setter will
     * be copied over into a new instance.
     * 
     * @param object The object to clone
     * @return The cloned instance
     */
    public <T> T shallowClone(T object);
    
    /**
     * Call this method to clone an object into an existing
     * clone instance. Every set of properties that has both 
     * a getter and a setter will be copied over into the
     * supplied instance.
     * 
     * @param object The source object
     * @param clone The clone to copy into
     * @return The clone is returned
     */
    public <T> T shallowClone(T object, T clone);
    
    /**
     * Call this method to perform a deep clone of an object.
     * A deep clone also clones the members unless they
     * themselves are immutable.
     * 
     * @param object The object to clone
     * @return The cloned instance
     */
    public <T> T deepClone(T object);
    
    /**
     * Call this method to performa deep clone of an object 
     * into an existing clone instance. Every set of properties 
     * that has both a getter and a setter will be cloned into 
     * the supplied instance.
     * 
     * @param object The source object
     * @param clone The clone to copy into
     * @return The clone is returned
     */
    public <T> T deepClone(T object, T clone);
}
