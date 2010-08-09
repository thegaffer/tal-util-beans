package org.talframework.util.beans;

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
