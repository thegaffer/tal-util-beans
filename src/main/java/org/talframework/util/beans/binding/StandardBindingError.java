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
 * Basic implementation of the {@link BindingError} interface
 *
 * @author Tom Spencer
 */
public final class StandardBindingError implements BindingError {
    
    /** The object binding into */
    private Object object;
    /** The name of the property */
    private String property;
    /** The full path of the property */ 
    private String fullProperty;
    /** The value we could not bind */
    private Object value;
    /** The error key */
    private String error;
    
    /**
     * Default constructor
     */
    public StandardBindingError() {
        
    }
    
    /**
     * This constructor allows one-line construction of the error
     * 
     * @param object The object binding into
     * @param property The property name
     * @param fullProperty The full path of the property
     * @param value The value could not bind
     * @param error The error key
     */
    public StandardBindingError(Object object, String property, String fullProperty, Object value, String error) {
        super();
        this.object = object;
        this.property = property;
        this.fullProperty = fullProperty;
        this.value = value;
        this.error = error;
    }
    
    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }
    
    /**
     * Setter for the object field
     *
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }
    
    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }
    
    /**
     * Setter for the property field
     *
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the fullProperty
     */
    public String getFullProperty() {
        return fullProperty;
    }

    /**
     * Setter for the fullProperty field
     *
     * @param fullProperty the fullProperty to set
     */
    public void setFullProperty(String fullProperty) {
        this.fullProperty = fullProperty;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }
 
    /**
     * Setter for the value field
     *
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Setter for the error field
     *
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((error == null) ? 0 : error.hashCode());
        result = prime * result + ((fullProperty == null) ? 0 : fullProperty.hashCode());
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        result = prime * result + ((property == null) ? 0 : property.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        StandardBindingError other = (StandardBindingError)obj;
        if( error == null ) {
            if( other.error != null ) return false;
        }
        else if( !error.equals(other.error) ) return false;
        if( fullProperty == null ) {
            if( other.fullProperty != null ) return false;
        }
        else if( !fullProperty.equals(other.fullProperty) ) return false;
        if( object == null ) {
            if( other.object != null ) return false;
        }
        else if( !object.equals(other.object) ) return false;
        if( property == null ) {
            if( other.property != null ) return false;
        }
        else if( !property.equals(other.property) ) return false;
        if( value == null ) {
            if( other.value != null ) return false;
        }
        else if( !value.equals(other.value) ) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BindingError [error=" + error + ", fullProperty=" + fullProperty + ", object=" + object + ", property=" + property + ", value=" + value
                + "]";
    }
}
