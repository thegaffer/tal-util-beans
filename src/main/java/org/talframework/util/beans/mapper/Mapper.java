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

/**
 * This interface represents a mapper between two objects.
 * Mapping is very common is many applications particularly
 * where there are layers added for abstraction. The
 * tendency is to either use some reflection technology
 * directly (which often is frowned upon) or to write very 
 * specific mappers between two objects by hand. By using
 * this interface you can abstract the real code away from
 * this decision, leaving you free to use a reflection
 * based approach initially and then move to custom mappers
 * as and when profiling determines it is required.
 *
 * @author Tom Spencer
 */
public interface Mapper {

    /**
     * This version of the map method maps between a source
     * and a target instance. The source and the target may
     * be the same class.
     * 
     * @param <Source> Represents the source class
     * @param <Target> Represents the target class
     * @param source The source object
     * @param target The target object
     */
    public <Source, Target> void map(Source source, Target target);
    
    /**
     * This version of the map method creates an instance of
     * target and then maps source into it.
     * 
     * @param <Source> Represents the source class
     * @param <Target> Represents the target class
     * @param source The source object
     * @param expected The expected type of the target
     * @return The newly constructed target instance
     */
    public <Source, Target> Target map(Source source, Class<Target> expected);
}
