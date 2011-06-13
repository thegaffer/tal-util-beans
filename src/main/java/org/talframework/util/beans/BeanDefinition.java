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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * This interface represents a definition of a JavaBean.
 * The idea is that instance of this class are created
 * and then cached to allow reasonably fast access to
 * the bean. Part of the design of this interface is that
 * at no time are you exposed to any reflection classes,
 * this enables you to implement this interface in a 
 * non-generic fashion if required.
 *
 * @author Tom Spencer
 */
public interface BeanDefinition {

    /**
     * @return The type we are a definition for
     */
    public Class<?> getType();
    
    /**
     * A helper method to create a new instance of this bean
     * 
     * @return The new instance
     */
    public Object newInstance();
    
    /**
     * Call to get all the property names of this bean
     * 
     * @return The property names of this bean
     */
    public Collection<String> getProperties();
    
    /**
     * Call to get all the read-only properties, those
     * with a getter, but no setter
     * 
     * @return The readonly properties
     */
    public Collection<String> getReadOnlyProperties();
    
    /**
     * Call to get all the set-only properties, those
     * with a setter, but no getter
     * 
     * @return The setonly properties
     */
    public Collection<String> getSetOnlyProperties();
    
    /**
     * Call to get all properties that have both a 
     * getter and a setter.
     * 
     * @return The properties with a getter and setter
     */
    public Collection<String> getBalancedProperties();

    /**
     * Determines if the property exists on the bean
     * 
     * @param name The name of the property
     * @return True if it exists, false otherwise
     */
    public boolean hasProperty(String name);
    
    /**
     * Determines both if the property exists and if
     * it is of a particular type
     * 
     * @param name The name of the property
     * @param type The type of the property
     * @return True if it exists, false otherwise
     */
    public boolean hasProperty(String name, Class<?> type);

    /**
     * Call to get a properties type
     * 
     * @param name The name of the property
     * @return The type of the property
     * @throws IllegalArgumentException If the property does not exist
     */
    public Class<?> getPropertyType(String name);
    
    /**
     * Returns the properties type ensuring it is
     * in the Object format if the type represents
     * a primitive.
     * 
     * @param name The name of the proeprty
     * @return The object type of the property
     */
    public Class<?> getObjectPropertyType(String name);
    
    /**
     * Call to get the generic type of the property
     * 
     * @param name The name of the property
     * @return The generic type of the property
     * @throws IllegalArgumentException If the property does not exist
     */
    public Type getPropertyGenericType(String name);
    
    /**
     * Call to get a specific read annotation on the properties reader.
     * 
     * @param name The property to get annotation for
     * @param annotation The annotations class to find
     * @return The annotation
     */
    public <T extends Annotation> T getReadAnnotation(String name, Class<T> annotation);
    
    /**
     * Call to get a specific write annotation on the properties writer.
     * 
     * @param name The property to get annotation for
     * @param annotation The annotations class to find
     * @return The annotation
     */
    public <T extends Annotation> T getWriteAnnotation(String name, Class<T> annotation);

    /**
     * Determines if the property can be read
     * 
     * @param name The name of the property
     * @return True if it can be read, false otherwise
     * @throws IllegalArgumentException If the property does not exist
     */
    public boolean canRead(String name);

    /**
     * Reads the property value
     * 
     * @param obj The object to read from
     * @param name The name of the property to read
     * @return The value of the property
     * @throws IllegalArgumentException If the property does not exist, has no accessor or fails
     */
    public Object read(Object obj, String name);
    
    /**
     * Tempplate version of above
     * 
     * @param obj The object to read from
     * @param name The name of the property to read
     * @param expected The expected type of the property
     * @return The value of the property
     * @throws IllegalArgumentException If the property does not exist, has no accessor or fails
     */
    public <T> T read(Object obj, String name, Class<T> expected);

    /**
     * Determines if the property can be written to
     * 
     * @param name The name of the property
     * @return True if it can be written, false otherwise
     * @throws IllegalArgumentException If the property does not exist
     */
    public boolean canWrite(String name);

    /**
     * Returns the writer/mutator method
     * 
     * @param name The name of the property
     * @return The mutator or null
     * @throws IllegalArgumentException If the property does not exist, has no accessor or fails
     */
    public void write(Object obj, String name, Object val);

}