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
import java.util.HashMap;
import java.util.Map;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.talframework.util.beans.AComplexBean;
import org.talframework.util.beans.AnotherBean;

import junit.framework.Assert;

/**
 * Tests the {@link StandardBindingRequest} object.
 * 
 * FUTURE: Need to test registration of property editors
 *
 * @author Tom Spencer
 */
public class TestStandardBindingRequest {
    
    private Mockery context = new JUnit4Mockery();

    @Test
    public void basic() {
        String bean = "Testing";
        Map<String, Object> input = new HashMap<String, Object>();
        
        StandardBindingRequest<String> req = new StandardBindingRequest<String>();
        req.setTarget(bean);
        req.setInput(input);
        
        Assert.assertSame(bean, req.getTarget());
        Assert.assertSame(input, req.getInput());
        Assert.assertNotNull(req.getObjectCreator());

        BindingObjectCreator creator = context.mock(BindingObjectCreator.class);
        req.setObjectCreator(creator);
        Assert.assertSame(creator, req.getObjectCreator());
    }
    
    @Test
    public void getDefaultPropertyEditor() {
        AComplexBean bean = new AComplexBean();
        StandardBindingRequest<AComplexBean> req = new StandardBindingRequest<AComplexBean>();
        PropertyEditor editor = req.getPropertyEditor(bean, double.class, "dblVal");
        Assert.assertNotNull(editor);
        PropertyEditor editor2 = req.getPropertyEditor(bean, double.class, "dblVal");
        Assert.assertSame(editor, editor2);
        
        // Now test the other that should be there
        Assert.assertNotNull(req.getPropertyEditor(bean, float.class, "fltVal"));
        Assert.assertNotNull(req.getPropertyEditor(bean, long.class, "longVal"));
        Assert.assertNotNull(req.getPropertyEditor(bean, int.class, "intVal"));
        Assert.assertNotNull(req.getPropertyEditor(bean, short.class, "shortVal"));
        Assert.assertNotNull(req.getPropertyEditor(bean, byte.class, "byteVal"));
        // Assert.assertNotNull(req.getPropertyEditor(bean, char.class, "charVal"));
        Assert.assertNotNull(req.getPropertyEditor(bean, boolean.class, "boolVal"));
        
        AnotherBean bean2 = new AnotherBean();
        Assert.assertNotNull(req.getPropertyEditor(bean2, Double.class, "dblVal2"));
        Assert.assertNotNull(req.getPropertyEditor(bean2, Float.class, "fltVal2"));
        Assert.assertNotNull(req.getPropertyEditor(bean2, Long.class, "longVal2"));
        // Assert.assertNotNull(req.getPropertyEditor(bean2, Integer.class, "intVal2"));
        Assert.assertNotNull(req.getPropertyEditor(bean2, Short.class, "shortVal2"));
        Assert.assertNotNull(req.getPropertyEditor(bean2, Byte.class, "byteVal2"));
        // Assert.assertNotNull(req.getPropertyEditor(bean2, Character.class, "charVal2"));
        // Assert.assertNotNull(req.getPropertyEditor(bean2, Boolean.class, "boolVal2"));
    }
    
    @Test
    public void hasHashCode() {
        String bean = "Testing";
        String bean2 = "Testing2";
        Map<String, Object> input = new HashMap<String, Object>();
        
        StandardBindingRequest<String> obj1 = new StandardBindingRequest<String>(bean, input);
        StandardBindingRequest<String> obj2 = new StandardBindingRequest<String>(bean2, input);
        StandardBindingRequest<String> obj3 = new StandardBindingRequest<String>(bean, input);
        
        Assert.assertTrue(obj1.hashCode() == obj3.hashCode());
        Assert.assertFalse(obj1.hashCode() == obj2.hashCode());
    }
    
    @Test
    public void hasEquals() {
        String bean = "Testing";
        String bean2 = "Testing2";
        Map<String, Object> input = new HashMap<String, Object>();
        
        StandardBindingRequest<String> obj1 = new StandardBindingRequest<String>(bean, input);
        StandardBindingRequest<String> obj2 = new StandardBindingRequest<String>(bean2, input);
        StandardBindingRequest<String> obj3 = new StandardBindingRequest<String>(bean, input);
        
        Assert.assertTrue(obj1.equals(obj3));
        Assert.assertFalse(obj1.equals(obj2));
    }
    
    @Test
    public void hasToString() {
        try {
            Assert.assertNotNull(StandardBindingRequest.class.getDeclaredMethod("toString", (Class<?>[])null));
        }
        catch( NoSuchMethodException e) {
            Assert.assertTrue("No toString method declared", false);
        }
    }
}
