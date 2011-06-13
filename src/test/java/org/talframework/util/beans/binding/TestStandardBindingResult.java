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
 * Tests the binding result object
 *
 * @author Tom Spencer
 */
public class TestStandardBindingResult {

    @Test
    public void basic() {
        String bean = "Testing";
        
        StandardBindingResult<String> result = new StandardBindingResult<String>(bean);
        Assert.assertSame(bean, result.getTarget());
        Assert.assertFalse(result.hasErrors());
        
        // TODO: Add errors in
        result.addError(new StandardBindingError(bean, "test", "node.test", new Date(), "error.test"));
        Assert.assertTrue(result.hasErrors());
        Assert.assertEquals(1, result.getErrors().size());
    }
    
    @Test
    public void hasHashCode() {
        String bean = "Testing";
        String bean2 = "Testing2";
        
        StandardBindingResult<String> obj1 = new StandardBindingResult<String>(bean);
        StandardBindingResult<String> obj2 = new StandardBindingResult<String>(bean2);
        StandardBindingResult<String> obj3 = new StandardBindingResult<String>(bean);
        
        Assert.assertTrue(obj1.hashCode() == obj3.hashCode());
        Assert.assertFalse(obj1.hashCode() == obj2.hashCode());
    }
    
    @Test
    public void hasEquals() {
        String bean = "Testing";
        String bean2 = "Testing2";
        
        StandardBindingResult<String> obj1 = new StandardBindingResult<String>(bean);
        StandardBindingResult<String> obj2 = new StandardBindingResult<String>(bean2);
        StandardBindingResult<String> obj3 = new StandardBindingResult<String>(bean);
        
        Assert.assertTrue(obj1.equals(obj3));
        Assert.assertFalse(obj1.equals(obj2));
    }
    
    @Test
    public void hasToString() {
        try {
            Assert.assertNotNull(StandardBindingResult.class.getDeclaredMethod("toString", (Class<?>[])null));
        }
        catch( NoSuchMethodException e) {
            Assert.assertTrue("No toString method declared", false);
        }
    }
}
