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
package org.talframework.util.beans;

/**
 * This class contains static methods to aid the comparison of
 * beans. Originally designed for constructing the equals method
 * on a JavaBean with the very high code-complexity of what
 * comes out of Eclipse by default. Your equals method will then
 * be structured like this:
 * 
 * <code><pre>
 * public boolean equals(Object obj) {
 *   MyClass other = BeanComparison.basic(this, obj);
 *   boolean ret = other != null;
 *   if( ret && other != this ) {
 *      ret = BeanComparison.equals(ret, this.prop1, other.prop1);
 *      // Other fields
 *   }
 *   
 *   return ret;
 * }
 * </pre></code>
 *
 * @author Tom Spencer
 */
public class BeanComparison {

    /**
     * Performs the basic check on two objects to see if they
     * potentially are the same and return the anonymous
     * object as an instance of self so it can be further
     * compared. If the object definitely are not the same
     * then return null.
     * 
     * @param self The 'this' object we want to compare other to
     * @param other The other object
     * @return The other object as an instance of the self class if possibly the same
     */
    @SuppressWarnings("unchecked")
    public static <T> T basic(T self, Object other) {
        Object ret = null;
        
        if( self == other ) ret = other;
        else if( other == null ) ret = null;
        else if( other.getClass().equals(self.getClass()) ) ret = other;
        
        return (T)self.getClass().cast(ret);
    }
    
    /**
     * Call to simply compare two properties. The comparison is
     * not even performed if the boolean same parameter is false.
     * 
     * @param same Indicates if we think the two objects are currently the same
     * @param self The property of the 'self' or this object
     * @param other The property on the other object
     * @return True if we think they are the same, false otherwise
     */
    public static boolean equals(boolean same, Object self, Object other) {
        boolean ret = false;
        
        if( !same ) ret = same;
        else if( self == other ) ret = true;
        else if( self != null && self.equals(other) ) ret = true;
        else ret = false;
        
        return ret;
    }
}
