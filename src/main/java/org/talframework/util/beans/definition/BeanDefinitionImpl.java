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

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.InterfaceAdaptor;

/**
 * This class contains the methods accessing and mutating
 * a JavaBean class. The idea is that these classes can
 * be cached and then used to reflectively access a bean.
 * They are used within the beans utility by the mapper,
 * but can be used outside as well.
 *
 * @author Tom Spencer
 */
public final class BeanDefinitionImpl implements BeanDefinition {

    /** The type this instance holds the definition for */
    private final Class<?> type;
    /** Holds all the accessors for the properties in the bean */
    private final Map<String, PropertyDefinition> properties;
    /** Holds all balanced property names */
    private final List<String> balancedProperties;
    /** Holds all readonly property names */
    private final List<String> readOnlyProperties;
    /** Holds all setonly property names */
    private final List<String> setOnlyProperties;
    
    /**
     * Constructs a {@link BeanDefinitionImpl} for the
     * given bean class.
     * 
     * @param type The bean class
     */
    public BeanDefinitionImpl(Class<?> type) {
        this.type = type;
        this.properties = new HashMap<String, PropertyDefinition>();
        this.balancedProperties = new ArrayList<String>();
        this.readOnlyProperties = new ArrayList<String>();
        this.setOnlyProperties = new ArrayList<String>();
        
        // Get all properties
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(type).getPropertyDescriptors();
            if( props != null ) {
                for( PropertyDescriptor prop : props ) {
                    if( prop.getName().equals("class") ) continue;
                    PropertyDefinition def = new PropertyDefinition(prop);
                    properties.put(prop.getName(), def);
                    
                    // Add prop to list of properties in correct set
                    if( def.getAccessor() != null ) {
                        if( def.getMutator() == null ) readOnlyProperties.add(prop.getName());
                        else balancedProperties.add(prop.getName());
                    }
                    else if( def.getMutator() != null ) {
                        setOnlyProperties.add(prop.getName());
                    }
                }
            }
        }
        catch( Exception e ) {
            throw new IllegalArgumentException("Cannot create bean property definition for bean: " + type, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Class<?> getType() {
        return type;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object newInstance() {
        try {
            if( type.isInterface() ) {
                return type.cast(Proxy.newProxyInstance(
                        Thread.currentThread().getContextClassLoader(), 
                        new Class[]{type}, 
                        new InterfaceAdaptor()));
            }
            else {
                return type.newInstance();
            }
        }
        catch( RuntimeException e ) {
            throw e;
        }
        catch( Exception e ) {
            throw new RuntimeException("Cannot create instance of bean: " + type, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public Collection<String> getProperties() {
        return properties.keySet();
    }
    
    /**
     * {@inheritDoc}
     */
    public Collection<String> getReadOnlyProperties() {
        return readOnlyProperties;
    }
    
    /**
     * {@inheritDoc}
     */
    public Collection<String> getSetOnlyProperties() {
        return setOnlyProperties;
    }
    
    /**
     * {@inheritDoc}
     */
    public Collection<String> getBalancedProperties() {
        return balancedProperties;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasProperty(String name) {
        return this.properties.containsKey(name);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean hasProperty(String name, Class<?> type) {
        if( this.properties.containsKey(name) && type.isAssignableFrom(this.properties.get(name).getType()) ) return true;
        return false;
    }
    
    /**
     * {@inheritDoc}
     */
    public Class<?> getPropertyType(String name) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        return properties.get(name).getType();
    }
    
    /**
     * {@inheritDoc}
     */
    public Class<?> getObjectPropertyType(String name) {
        Class<?> ret = getPropertyType(name);
        if( ret.isPrimitive() ) {
            if( ret.equals(int.class) ) ret = Integer.class;
            else if( ret.equals(long.class) ) ret = Long.class;
            else if( ret.equals(boolean.class) ) ret = Boolean.class;
            else if( ret.equals(double.class) ) ret = Double.class;
            else if( ret.equals(short.class) ) ret = Short.class;
            else if( ret.equals(char.class) ) ret = Character.class;
            else if( ret.equals(byte.class) ) ret = Byte.class;
            else if( ret.equals(float.class) ) ret = Float.class;
        }
        
        return ret;
    }
    
    /**
     * {@inheritDoc}
     */
    public Type getPropertyGenericType(String name) {
        if( canRead(name) ) return properties.get(name).getAccessor().getGenericReturnType();
        else if( canWrite(name) ) return properties.get(name).getMutator().getGenericParameterTypes()[0];
        else return null; // Wouldn't be here if there wasn't a reader or writer!
    }
    
    /**
     * {@inheritDoc}
     */
    public <T extends Annotation> T getReadAnnotation(String name, Class<T> annotation) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        Method reader = properties.get(name).getAccessor();
        return reader != null ? reader.getAnnotation(annotation) : null;
    }
    
    /**
     * {@inheritDoc}
     */
    public <T extends Annotation> T getWriteAnnotation(String name, Class<T> annotation) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        Method writer = properties.get(name).getMutator();
        return writer != null ? writer.getAnnotation(annotation) : null;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean canRead(String name) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        return properties.get(name).getAccessor() != null;
    }
    
    /**
     * {@inheritDoc}
     */
    public Object read(Object obj, String name) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        Method reader = properties.get(name).getAccessor();
        if( reader == null ) throw new IllegalArgumentException("Cannot read property [" + name + "] as no accessor exists for bean type: " + type);
        
        try {
            return reader.invoke(obj, (Object[])null);
        }
        catch( RuntimeException e ) {
            throw e;
        }
        catch( Exception e ) {
            throw new RuntimeException("Cannot read property [" + name + "] due to caught exception: " + type, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public <T> T read(Object obj, String name, Class<T> expected) {
        Object val = read(obj, name);
        return expected.cast(val);
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean canWrite(String name) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        return properties.get(name).getMutator() != null;
    }
    
    /**
     * {@inheritDoc}
     */
    public void write(Object obj, String name, Object val) {
        if( !hasProperty(name) ) throw new IllegalArgumentException("Property [" + name + "] does not exist for bean type: " + type);
        Method writer = properties.get(name).getMutator();
        if( writer == null ) throw new IllegalArgumentException("Cannot write property [" + name + "] as no mutator exists for bean type: " + type);
        
        try {
            writer.invoke(obj, val);
        }
        catch( RuntimeException e ) {
            throw e;
        }
        catch( Exception e ) {
            throw new RuntimeException("Cannot write property [" + name + "] due to caught exception: " + type, e);
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((balancedProperties == null) ? 0 : balancedProperties.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((readOnlyProperties == null) ? 0 : readOnlyProperties.hashCode());
        result = prime * result + ((type == null) ? 0 : type.getName().hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        BeanDefinitionImpl other = (BeanDefinitionImpl)obj;
        if( balancedProperties == null ) {
            if( other.balancedProperties != null ) return false;
        }
        else if( !balancedProperties.equals(other.balancedProperties) ) return false;
        if( properties == null ) {
            if( other.properties != null ) return false;
        }
        else if( !properties.equals(other.properties) ) return false;
        if( readOnlyProperties == null ) {
            if( other.readOnlyProperties != null ) return false;
        }
        else if( !readOnlyProperties.equals(other.readOnlyProperties) ) return false;
        if( type == null ) {
            if( other.type != null ) return false;
        }
        else if( !type.equals(other.type) ) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BeanDefinitionImpl [balancedProperties=" + balancedProperties 
            + ", properties=" + properties 
            + ", readOnlyProperties=" + readOnlyProperties
            + ", type=" + type + "]";
    }

    /**
     * Internal helper to hold any one properties type,
     * accessor and mutator. We do this because 
     * {@link PropertyDescriptor} has synchronised methods
     * for the methods.
     *
     * @author Tom Spencer
     */
    private final static class PropertyDefinition {
        //private final PropertyDescriptor property;
        private final Class<?> type;
        private final Method accessor;
        private final Method mutator;
        
        public PropertyDefinition(PropertyDescriptor prop) {
            //this.property = prop;
            this.type = prop.getPropertyType();
            this.accessor = prop.getReadMethod();
            this.mutator = prop.getWriteMethod();
        }
        
        /**
         * @return The properties type
         */
        public Class<?> getType() {
            return type;
        }
        
        /**
         * @return The properties accessor method (or null)
         */
        public Method getAccessor() {
            return accessor;
        }
        
        /**
         * @return The properties mutator method (or null)
         */
        public Method getMutator() {
            return mutator;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((accessor == null) ? 0 : accessor.hashCode());
            result = prime * result + ((mutator == null) ? 0 : mutator.hashCode());
            result = prime * result + ((type == null) ? 0 : type.getName().hashCode());
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if( this == obj ) return true;
            if( obj == null ) return false;
            if( getClass() != obj.getClass() ) return false;
            PropertyDefinition other = (PropertyDefinition)obj;
            if( accessor == null ) {
                if( other.accessor != null ) return false;
            }
            else if( !accessor.equals(other.accessor) ) return false;
            if( mutator == null ) {
                if( other.mutator != null ) return false;
            }
            else if( !mutator.equals(other.mutator) ) return false;
            if( type == null ) {
                if( other.type != null ) return false;
            }
            else if( !type.equals(other.type) ) return false;
            return true;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "PropertyDefinition [accessor=" + accessor + ", mutator=" + mutator + ", type=" + type + "]";
        }
    }
}
