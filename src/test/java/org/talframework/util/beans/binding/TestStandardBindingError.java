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

import java.util.Date;

import org.junit.Test;

import junit.framework.Assert;

/**
 * Simply tests the {@link StandardBindingError} class.
 *
 * @author Tom Spencer
 */
public class TestStandardBindingError {

    @Test
    public void basic() {
        Object bean = "Testing";
        Object val = new Date();
        
        StandardBindingError err = new StandardBindingError();
        err.setObject(bean);
        err.setProperty("test");
        err.setFullProperty("node.test");
        err.setValue(val);
        err.setError("error.test");
        
        Assert.assertEquals(bean, err.getObject());
        Assert.assertEquals("test", err.getProperty());
        Assert.assertEquals("node.test", err.getFullProperty());
        Assert.assertEquals(val, err.getValue());
        Assert.assertEquals("error.test", err.getError());
    }
    
    @Test
    public void hasHashCode() {
        Object bean = "Testing";
        Object val = new Date();
        
        StandardBindingError obj1 = new StandardBindingError(bean, "test", "node.test", val, "error.test");
        StandardBindingError obj2 = new StandardBindingError(bean, "test2", "node.test2", val, "error.test");
        StandardBindingError obj3 = new StandardBindingError(bean, "test", "node.test", val, "error.test");
        
        Assert.assertTrue(obj1.hashCode() == obj3.hashCode());
        Assert.assertFalse(obj1.hashCode() == obj2.hashCode());
    }
    
    @Test
    public void hasEquals() {
        Object bean = "Testing";
        Object val = new Date();
        
        StandardBindingError obj1 = new StandardBindingError(bean, "test", "node.test", val, "error.test");
        StandardBindingError obj2 = new StandardBindingError(bean, "test2", "node.test2", val, "error.test");
        StandardBindingError obj3 = new StandardBindingError(bean, "test", "node.test", val, "error.test");
        
        Assert.assertTrue(obj1.equals(obj3));
        Assert.assertFalse(obj1.equals(obj2));
    }

    @Test
    public void hasToString() {
        try {
            Assert.assertNotNull(StandardBindingError.class.getDeclaredMethod("toString", (Class<?>[])null));
        }
        catch( NoSuchMethodException e) {
            Assert.assertTrue("No toString method declared", false);
        }
    }
}
