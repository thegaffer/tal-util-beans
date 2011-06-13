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
 * This interface represents an error in the binding.
 * The binder just represents any errors in mapping the
 * input into the target objects by raising an instance
 * of this class - depending where the binding is
 * performed allows the caller to then iterate these
 * errors and raise them as appropriate for itself. A
 * little extra effort on the clients part in return
 * for absolutely no dependency on any mechanism to
 * store errors.
 *
 * @author Tom Spencer
 */
public interface BindingError {

    /**
     * @return The specific object the error occurred on
     */
    public Object getObject();
    
    /**
     * @return The name of the property the error occurred on
     */
    public String getProperty();
    
    /**
     * @return The full property path from the root the error occurred on
     */
    public String getFullProperty();
    
    /**
     * @return The actual value we tried to set
     */
    public Object getValue();
    
    /**
     * @return The actual error (as a key, i.e. error.invalid.number) 
     */
    public String getError();
}
