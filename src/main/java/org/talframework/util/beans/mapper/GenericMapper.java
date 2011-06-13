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
package org.talframework.util.beans.mapper;

import java.util.HashMap;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.cloner.Cloner;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This class is a generic mapper that uses {@link BeanDefinition}
 * to access and copy the properties. The default instance will
 * copy all properties that have the same name between the source
 * and destination object. Additionally though it supports setting
 * up a map containing the name of the source property with the
 * name of the destination property so that minor name changes can
 * be dealt with.
 * 
 * <p>The generic mapper also has some inbuilt conversion rules
 * between basic types. However, it is also possible to setup custom
 * conversion against each source property.</p>
 * 
 * <p>Mappers are not served up by a singleton instance unlike
 * {@link BeanDefinition} and {@link Cloner} instead you would set
 * up your mappers in your config (i.e. Spring config) and inject
 * them in where needed. Be careful though if you are registering
 * custom BeanDefinitions to make sure these are setup before
 * creating the GenericMappers. If using spring the depends-on
 * setting is your man for this.</p>
 *
 * @author Tom Spencer
 */
public class GenericMapper implements Mapper {

    /** Holds the definition of the source bean */
    private BeanDefinition sourceDefinition;
    /** Holds the definition of the destination bean */
    private BeanDefinition destDefinition;
    /** Holds an name variations between source (key) and destination (value) */
    private Map<String, String> nameVariations;
    /** Holds any mappers for embedded objects against source property names */
    private Map<String, Mapper> propertyMappers;
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <Source, Target> Target map(Source source, Class<Target> expected) {
        if( !source.getClass().equals(sourceDefinition.getType()) ) throw new IllegalArgumentException("The mapper has been supplied with a source [" + source + "] of different type to configured source: " + sourceDefinition);
        if( !expected.equals(destDefinition.getType()) ) throw new IllegalArgumentException("The mapper has been supplied with a expected [" + expected + "] of different type to configured dest: " + destDefinition);
        
        Target ret = source != null ? (Target)destDefinition.newInstance() : null;
        if( ret != null ) internalMap(source, ret);
        return ret;
    };
    
    /**
     * {@inheritDoc}
     */
    public <Source, Target> void map(Source source, Target target) {
        if( !source.getClass().equals(sourceDefinition.getType()) ) throw new IllegalArgumentException("The mapper has been supplied with a source [" + source + "] of different type to configured source: " + sourceDefinition);
        if( !target.getClass().equals(destDefinition.getType()) ) throw new IllegalArgumentException("The mapper has been supplied with a target [" + target + "] of different type to configured dest: " + destDefinition);
        
        if( source != null ) internalMap(source, target);
    }
    
    /**
     * Internal helper to actually perform the mapping, used by the
     * public methods.
     */
    private <Source, Target> void internalMap(Source source, Target target) {
        for( String prop : sourceDefinition.getProperties() ) {
            if( !sourceDefinition.canRead(prop) ) continue;
            
            String destProp = prop;
            if( nameVariations != null && nameVariations.containsKey(prop) ) destProp = nameVariations.get(prop);
            
            // Map if possible
            if( destDefinition.canWrite(destProp) ) {
                destDefinition.write(target, destProp, mapProperty(prop, sourceDefinition.read(source, prop), destDefinition.getPropertyType(destProp)));
            }
        }
    }
    
    /**
     * Internal helper to map a property value from source to destination
     */
    private Object mapProperty(String prop, Object value, Class<?> type) {
        Object ret = null;
        
        // If mapper use it
        if( propertyMappers != null && propertyMappers.containsKey(prop) ) {
            ret = propertyMappers.get(prop).map(value, type);
        }
        
        // Otherwise perform defaults
        else if( value != null ) {
            if( type.isAssignableFrom(value.getClass()) ) ret = value;
            else if( String.class.equals(type) ) ret = value.toString();
            // TODO: Utilities for conversion, will be needed from binding also
        }
        
        return ret;
    }
    
    
    ////////////////////////////////////////////////////
    // Getters/Setters if using default constructor

    /**
     * @return the sourceDefinition
     */
    public BeanDefinition getSourceDefinition() {
        return sourceDefinition;
    }

    /**
     * Setter for the sourceDefinition field
     *
     * @param sourceDefinition the sourceDefinition to set
     */
    public void setSourceDefinition(BeanDefinition sourceDefinition) {
        this.sourceDefinition = sourceDefinition;
    }
    
    /**
     * Setter for the sourceDefinition field by class
     *
     * @param sourceClass the sourceClass to use
     */
    public void setSourceDefinition(Class<?> sourceClass) {
        this.sourceDefinition = BeanDefinitionsSingleton.getInstance().getDefinition(sourceClass);
    }

    /**
     * @return the destDefinition
     */
    public BeanDefinition getDestDefinition() {
        return destDefinition;
    }

    /**
     * Setter for the destDefinition field
     *
     * @param destDefinition the destDefinition to set
     */
    public void setDestDefinition(BeanDefinition destDefinition) {
        this.destDefinition = destDefinition;
    }
    
    /**
     * Setter for the destDefinition field by class
     *
     * @param destClass the destClass to use
     */
    public void setDestDefinition(Class<?> destClass) {
        this.destDefinition = BeanDefinitionsSingleton.getInstance().getDefinition(destClass);
    }

    /**
     * @return the nameVariations
     */
    public Map<String, String> getNameVariations() {
        return nameVariations;
    }

    /**
     * Setter for the nameVariations field
     *
     * @param nameVariations the nameVariations to set
     */
    public void setNameVariations(Map<String, String> nameVariations) {
        this.nameVariations = nameVariations;
    }
    
    public void addNameVariation(String sourceName, String destName) {
        if( nameVariations == null ) nameVariations = new HashMap<String, String>();
        nameVariations.put(sourceName, destName);
    }

    /**
     * @return the propertyMappers
     */
    public Map<String, Mapper> getPropertyMappers() {
        return propertyMappers;
    }

    /**
     * Setter for the propertyMappers field
     *
     * @param propertyMappers the propertyMappers to set
     */
    public void setPropertyMappers(Map<String, Mapper> propertyMappers) {
        this.propertyMappers = propertyMappers;
    };
}
