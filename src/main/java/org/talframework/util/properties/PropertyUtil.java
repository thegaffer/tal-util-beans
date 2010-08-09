package org.talframework.util.properties;

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
