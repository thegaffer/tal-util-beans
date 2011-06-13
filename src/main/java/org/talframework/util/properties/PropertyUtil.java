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
package org.talframework.util.properties;

import java.text.MessageFormat;

/**
 * This class contains a bunch of handy methods for
 * working with property values. It is envisaged this
 * class being used when working with maps, or psuedo-
 * maps (such as HttpRequest parameters) that type 
 * their contents as string or object, but in reality
 * represent numbers and other fields.
 *
 * @author Tom Spencer
 */
public final class PropertyUtil {
    
    /**
     * Helper to cleanse a property value to ensure it is not null,
     * throwing an error if it does.
     * 
     * @param val
     * @param message
     * @return
     */
    public static <T> T getExpected(T val, String message, Object... params) {
        if( val == null ) throw new IllegalArgumentException(MessageFormat.format(message, params));
        return val;
    }
    
    /**
     * Takes an input value and returns it as a string.
     * Optionally this will return null if the string is
     * empty.
     * 
     * @param val The value
     * @param emptyAsNull Indicates if we should null empty strings
     * @return The string value
     */
    public static String getString(Object val, boolean emptyAsNull) {
        String ret = val != null ? val.toString() : null;
        if( emptyAsNull && ret != null && ret.length() == 0 ) ret = null;
        return ret;
    }
    
    /**
     * Takes an input value and returns it as a string.
     * Optionally this will return null if the string is
     * empty. If, having done this, we have a null string
     * then an exception is raised.
     * 
     * @param val The value
     * @param emptyAsNull Indicates if we should null empty strings
     * @param message The message to use if it is null
     * @return The string value
     */
    public static String getExpectedString(Object val, boolean emptyAsNull, String message, Object... params) {
        String ret = getString(val, emptyAsNull);
        if( ret == null ) throw new IllegalArgumentException(MessageFormat.format(message, params));
        return ret;
    }
    
    /**
     * Takes an input value and returns is as a long.
     * 
     * @param val The value
     * @param def The default to use if not a number
     * @return The long value (or the default)
     */
    public static long getLong(Object val, long def) {
        long ret = def;
        if( val instanceof Number ) ret = ((Number)val).longValue();
        else if( val != null ) {
            try {
                ret = Long.parseLong(val.toString());
            }
            catch( NumberFormatException e ) {}
        }
        
        return ret;
    }
    
    /**
     * Takes an input value and returns is as a int.
     * 
     * @param val The value
     * @param def The default to use if not a number
     * @return The long value (or the default)
     */
    public static int getInt(Object val, int def) {
        int ret = def;
        if( val instanceof Number ) ret = ((Number)val).intValue();
        else if( val != null ) {
            try {
                ret = Integer.parseInt(val.toString());
            }
            catch( NumberFormatException e ) {}
        }
        
        return ret;
    }
    
    /**
     * Takes an input value and returns is as a short.
     * 
     * @param val The value
     * @param def The default to use if not a number
     * @return The short value (or the default)
     */
    public static int getShort(Object val, short def) {
        short ret = def;
        if( val instanceof Number ) ret = ((Number)val).shortValue();
        else if( val != null ) {
            try {
                ret = Short.parseShort(val.toString());
            }
            catch( NumberFormatException e ) {}
        }
        
        return ret;
    }
    
    /**
     * Takes an input value and returns is as a boolean.
     * 
     * @param val The value
     * @param def The default to use if not a boolean
     * @return The boolean value (or the default)
     */
    public static boolean getBoolean(Object val, boolean def) {
        boolean ret = def;
        if( val instanceof Boolean ) ret = ((Boolean)val).booleanValue();
        else if( val != null ) ret = Boolean.parseBoolean(val.toString());
        
        return ret;
    }
}
