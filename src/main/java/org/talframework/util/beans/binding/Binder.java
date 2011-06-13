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
 * This interface represents an object that can bind
 * a map of property name/values into an object. This
 * is often used in Web Requests to bind the form
 * unencoded data into real objects.
 * 
 * TODO: Where to store errors!?! & logging against an ID!?!
 *
 * @author Tom Spencer
 */
public interface Binder {

    /**
     * Call to bind from a map of values into an actual object
     * as determined from the request. 
     * 
     * @param request Contains the target object and input for the binding
     * @return A binding result holding the target object and any errors
     */
    public <T> BindingResult<T> bind(BindingRequest<T> request);
    
}
