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


/**
 * This enum holds all the possible binding errors supported
 * in the standard binders. Actual errors are held as strings
 * to support extension.
 *
 * @author Tom Spencer
 */
public enum BindingErrorType {
    /** Indicates the type of the input cannot be converted into the expected type */
    INVALID_TYPE("error.invalid.type"),
    /** Indicates the input is a primitive array, but the target is neither an array or collection */
    INVALID_ARRAY("error.invalid.array"),
    /** Indicates the input is an unrecognised type that does not match the target type */
    INVALID_INPUT("error.invalid.input"),
    /** Indicates we have a member array on input, but the type of the property is not an array, collection or map */
    INVALID_MEMBER_INDEX("error.invalid.member.index"),
    /** Indicates we have a member array on input, but the type of the property is not an array, collection or map */
    INVALID_MEMBER_KEY("error.invalid.member.key");
    
    /** Holds the string value of the enum */
    private final String error;
    
    /**
     * Constructor to set the error text.
     * 
     * @param error The error string
     */
    BindingErrorType(String error) {
        this.error = error;
    }
    
    /**
     * Overridden to use the error member
     */
    @Override
    public String toString() {
        return this.error;
    }
    
    /**
     * Call to get the enum matching the given string
     * 
     * @param error The error to get enum for
     * @return The correct version of the enum
     */
    public static BindingErrorType fromString(String error) {
        BindingErrorType ret = null;
        if( error != null ) { 
            for( BindingErrorType b : BindingErrorType.values() ) { 
                if( error.equalsIgnoreCase(b.toString())) { 
                    ret = b;
                    break;
                } 
            } 
        }
        
        return ret;
    }
}
