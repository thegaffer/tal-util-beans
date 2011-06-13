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

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains the standard binding result for 
 * the object.
 *
 * @author Tom Spencer
 */
public class StandardBindingResult<T> implements BindingResult<T> {

    /** Holds the target object */
    private final T target;
    /** Holds any errors */
    private List<BindingError> errors;
    
    public StandardBindingResult(T target) {
        this.target = target;
    }
    
    /**
     * @return the target
     */
    public T getTarget() {
        return target;
    }
    
    /**
     * @return If there are any errors at all
     */
    public boolean hasErrors() {
        return errors != null && errors.size() > 0;
    }
    
    /**
     * @return the errors
     */
    public List<BindingError> getErrors() {
        return errors;
    }
    
    /**
     * Call to add an error to the result.
     * 
     * @param error The error to add
     */
    public void addError(BindingError error) {
        if( errors == null ) errors = new ArrayList<BindingError>();
        errors.add(error);
    }

    /**
     * Call to add an error to the result.
     * 
     * @param obj The object the error is on
     * @param property The property on that object
     * @param fullProperty The full property path
     * @param value The value that caused the error
     * @param error The error ley itself
     */
    public void addError(Object obj, String property, String fullProperty, Object value, String error) {
        addError(new StandardBindingError(obj, property, fullProperty, value, error));
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((errors == null) ? 0 : errors.hashCode());
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
        StandardBindingResult<T> other = (StandardBindingResult<T>)obj;
        if( errors == null ) {
            if( other.errors != null ) return false;
        }
        else if( !errors.equals(other.errors) ) return false;
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
        return "StandardBindingResult [errors=" + errors + ", target=" + target + "]";
    }
}
