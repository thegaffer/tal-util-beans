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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.util.HashMap;
import java.util.Map;

import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

public class StandardBindingRequest<T> implements BindingRequest<T> {
    
    /** Holds the target object */
    private T target;
    /** Holds the input */
    private Map<String, ?> input;
    /** Holds the object creator (defaults if not set) */
    private BindingObjectCreator objectCreator;
    /** Holds any custom property editors */
    private Map<EditorKey, PropertyEditor> propertyEditors;
    
    /**
     * Default constructor
     */
    public StandardBindingRequest() {
    }
    
    /**
     * Helper constructor for 1 line construction.
     */
    public StandardBindingRequest(T target, Map<String, ?> input) {
        this.target = target;
        this.input = input;
    }
    
    /**
     * @return the target
     */
    public T getTarget() {
        return target;
    }
    
    /**
     * Setter for the target field
     *
     * @param target the target to set
     */
    public void setTarget(T target) {
        this.target = target;
    }
    
    /**
     * Setter for the target field, but sets it
     * as a new instance of the given class.
     * 
     * @param expected The expected type of the class
     */
    @SuppressWarnings("unchecked")
    public void setTarget(Class<?> expected) {
        BeanDefinition def = BeanDefinitionsSingleton.getInstance().getDefinition(expected);
        target = (T)def.newInstance();
    }
    
    /**
     * @return the input
     */
    public Map<String, ?> getInput() {
        return input;
    }
    
    /**
     * Setter for the input field
     *
     * @param input the input to set
     */
    public void setInput(Map<String, ?> input) {
        this.input = input;
    }
    
    /**
     * @return the objectCreator
     */
    public BindingObjectCreator getObjectCreator() {
        if( objectCreator == null ) objectCreator = new StandardBindingObjectCreator();
        return objectCreator;
    }
    
    /**
     * Setter for the objectCreator field
     *
     * @param objectCreator the objectCreator to set
     */
    public void setObjectCreator(BindingObjectCreator objectCreator) {
        this.objectCreator = objectCreator;
    }
    
    /**
     * {@inheritDoc}
     */
    public PropertyEditor getPropertyEditor(Object object, Class<?> propertyType, String property) {
        PropertyEditor ret = null;
        
        if( propertyEditors != null ) {
            // Try with full key
            EditorKey key = new EditorKey(object.getClass(), propertyType, property);
            if( propertyEditors.containsKey(key) ) ret = propertyEditors.get(key);
            
            // Try with just property type
            key = new EditorKey(propertyType);
            if( propertyEditors.containsKey(key) ) ret = propertyEditors.get(key);
        }
        
        // Get a default as appropriate
        if( ret == null ) {
            ret = PropertyEditorManager.findEditor(propertyType);
            
            // Cache this one for this request
            if( ret != null ) {
                if( propertyEditors == null ) propertyEditors = new HashMap<EditorKey, PropertyEditor>();
                propertyEditors.put(new EditorKey(propertyType), ret);
            }
        }

        return ret;
    }
    
    /**
     * Call to add a property editor whenever a certain type of
     * field is added.
     * 
     * @param propertyType The type of property
     * @param editor The editor to use
     */
    public void addPropertyEditor(Class<?> propertyType, PropertyEditor editor) {
        propertyEditors.put(new EditorKey(propertyType), editor);
    }

    /**
     * Call to add a property editor for a specific property
     * on a given type of class.
     * 
     * @param owningType The class the property is on
     * @param propertyType The type of the property
     * @param propertyName The name of the property
     * @param editor The editor to use
     */
    public void addPropertyEditor(Class<?> owningType, Class<?> propertyType, String propertyName, PropertyEditor editor) {
        propertyEditors.put(new EditorKey(owningType, propertyType, propertyName), editor);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        result = prime * result + ((objectCreator == null) ? 0 : objectCreator.hashCode());
        result = prime * result + ((propertyEditors == null) ? 0 : propertyEditors.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        StandardBindingRequest<T> other = (StandardBindingRequest<T>)obj;
        if( input == null ) {
            if( other.input != null ) return false;
        }
        else if( !input.equals(other.input) ) return false;
        if( objectCreator == null ) {
            if( other.objectCreator != null ) return false;
        }
        else if( !objectCreator.equals(other.objectCreator) ) return false;
        if( propertyEditors == null ) {
            if( other.propertyEditors != null ) return false;
        }
        else if( !propertyEditors.equals(other.propertyEditors) ) return false;
        if( target == null ) {
            if( other.target != null ) return false;
        }
        else if( !target.equals(other.target) ) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "StandardBindingRequest [input=" + input + ", objectCreator=" + objectCreator + ", propertyEditors=" + propertyEditors + ", target=" + target
                + "]";
    }

    /**
     * This class represents a property editor key
     *
     * @author Tom Spencer
     */
    private final static class EditorKey {
        private final Class<?> owningType;
        private final Class<?> propertyType;
        private final String propertyName;
        
        public EditorKey(Class<?> propertyType) {
            this.owningType = null;
            this.propertyType = propertyType;
            this.propertyName = null;
        }
        
        public EditorKey(Class<?> owningType, Class<?> propertyType, String propertyName) {
            this.owningType = owningType;
            this.propertyType = propertyType;
            this.propertyName = propertyName;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((owningType == null) ? 0 : owningType.hashCode());
            result = prime * result + ((propertyName == null) ? 0 : propertyName.hashCode());
            result = prime * result + ((propertyType == null) ? 0 : propertyType.hashCode());
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
            EditorKey other = (EditorKey)obj;
            if( owningType == null ) {
                if( other.owningType != null ) return false;
            }
            else if( !owningType.equals(other.owningType) ) return false;
            if( propertyName == null ) {
                if( other.propertyName != null ) return false;
            }
            else if( !propertyName.equals(other.propertyName) ) return false;
            if( propertyType == null ) {
                if( other.propertyType != null ) return false;
            }
            else if( !propertyType.equals(other.propertyType) ) return false;
            return true;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "EditorKey [owningType=" + owningType + ", propertyName=" + propertyName + ", propertyType=" + propertyType + "]";
        }
    }
}
