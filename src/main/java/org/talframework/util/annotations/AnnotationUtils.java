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
package org.talframework.util.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This static class provides various helpers for dealing
 * with annotations and annotation values.
 * 
 * @author Tom Spencer
 */
public final class AnnotationUtils {

    /**
     * Call to get an annotation that is expected on the given
     * class, throwing a runtime exception if not found.
     * 
     * @param annotationType The expected annotation
     * @param cls The Java class to check
     * @return The annotation
     * @throws IllegalArgumentException If annotation not found
     */
    public static <T extends Annotation> T getExpectedAnnotation(Class<T> annotationType, Class<?> cls) {
        T ret = cls.getAnnotation(annotationType);
        if( ret == null ) throw new IllegalArgumentException("Expected annotation [" + annotationType + "] not found on class: " + cls);
        return ret;
    }
    
    /**
     * Call to get an annotation that is expected on the given
     * method, throwing a runtime exception if not found.
     * 
     * @param annotationType The expected annotation
     * @param method The method to check
     * @return The annotation
     * @throws IllegalArgumentException If annotation not found
     */
    public static <T extends Annotation> T getExpectedAnnotation(Class<T> annotationType, Method method) {
        T ret = method.getAnnotation(annotationType);
        if( ret == null ) throw new IllegalArgumentException("Expected annotation [" + annotationType + "] not found on method: " + method);
        return ret;
    }
    
    /**
     * Call to get all the methods of a class that have the given
     * annotation.
     * 
     * @param annotationType The annotation we want on the methods
     * @param cls The class to check
     * @return The methods and their annotation of the given type in a map (will be empty if nothing found)
     */
    public static <T extends Annotation> Map<Method, T> getAnnotatedMethods(Class<T> annotationType, Class<?> cls) {
        Map<Method, T> ret = new HashMap<Method, T>();
        
        Method[] methods = cls.getMethods();
        for( Method method : methods ) {
            T annotation = method.getAnnotation(annotationType);
            if( annotation != null ) ret.put(method, annotation);
        }
        
        return ret;
    }
 
    /**
     * Returns the given class (val) as itself or nulls if it
     * it is equal to the defaultClass. This is because you
     * cannot have null as default, so this method helps deal
     * with that.
     * 
     * @param val The class
     * @param defaultClass The class that if above is equal to should null the result
     * @return The val, or null if it is equal to default
     */
    public static Class<?> getAnnotationClass(Class<?> val, Class<?> defaultClass) {
        if( val.equals(defaultClass) ) return null;
        return val;
    }
}
