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
package org.talframework.util.beans.definition;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.talframework.util.beans.AComplexBean;
import org.talframework.util.beans.AnotherBean;
import org.talframework.util.beans.BeanDefinition;

public class TestBeanDefinition {

    @Test
    public void basic() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        
        Assert.assertNotNull(def);
        Assert.assertEquals(AComplexBean.class, def.getType());
        Assert.assertEquals(14, def.getProperties().size());
        Assert.assertEquals(13, def.getBalancedProperties().size());
        Assert.assertEquals(1, def.getReadOnlyProperties().size());
        Assert.assertEquals(0, def.getSetOnlyProperties().size());
        
        Assert.assertTrue(def.hasProperty("stringVal"));
        Assert.assertTrue(def.hasProperty("stringVal"));
        Assert.assertTrue(def.hasProperty("readOnlyVal"));
        Assert.assertTrue(def.hasProperty("dateVal"));
        Assert.assertTrue(def.hasProperty("dblVal"));
        Assert.assertTrue(def.hasProperty("fltVal"));
        Assert.assertTrue(def.hasProperty("lngVal"));
        Assert.assertTrue(def.hasProperty("intVal"));
        Assert.assertTrue(def.hasProperty("shrVal"));
        Assert.assertTrue(def.hasProperty("boolVal"));
        Assert.assertTrue(def.hasProperty("charVal"));
        Assert.assertTrue(def.hasProperty("arrayVal"));
        Assert.assertTrue(def.hasProperty("objectVal"));
        Assert.assertTrue(def.hasProperty("objectArrayVal"));
        Assert.assertTrue(def.hasProperty("objectCollectionVal"));
        
        Assert.assertEquals(Date.class, def.getPropertyType("dateVal"));
        Assert.assertEquals(long.class, def.getPropertyType("lngVal"));
        Assert.assertEquals(AnotherBean[].class, def.getPropertyType("objectArrayVal"));
        
        Assert.assertTrue(def.canRead("stringVal"));
        Assert.assertTrue(def.canWrite("stringVal"));
        Assert.assertTrue(def.canRead("readOnlyVal"));
        Assert.assertFalse(def.canWrite("readOnlyVal"));
    }
    
    @Test
    public void withAnotherBean() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        
        Assert.assertNotNull(def);
        Assert.assertEquals(AnotherBean.class, def.getType());
        Assert.assertEquals(12, def.getProperties().size());
        Assert.assertEquals(1, def.getReadOnlyProperties().size());
        Assert.assertEquals(1, def.getSetOnlyProperties().size());
        
        Assert.assertTrue(def.canRead("stringVal"));
        Assert.assertTrue(def.canWrite("stringVal"));
        Assert.assertTrue(def.canRead("readOnlyVal"));
        Assert.assertFalse(def.canWrite("readOnlyVal"));
        Assert.assertFalse(def.canRead("setOnlyVal"));
        Assert.assertTrue(def.canWrite("setOnlyVal"));
    }
    
    @Test
    public void create() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        Assert.assertNotNull(def.newInstance());
    }
    
    @Test
    public void read() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        
        AComplexBean bean = new AComplexBean();
        Assert.assertEquals("String", def.read(bean, "stringVal"));
    }
    
    @Test
    public void write() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        
        AComplexBean bean = new AComplexBean();
        def.write(bean, "stringVal", "Changed");
        Assert.assertEquals("Changed", bean.getStringVal());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void failReadIfNotReadable() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        
        AComplexBean bean = new AComplexBean();
        def.read(bean, "setOnlyVal");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void failWriteIfNotWritable() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        
        AComplexBean bean = new AComplexBean();
        def.write(bean, "readOnlyVal", "Changed");
    }
    
    @Test
    public void ensureNotReadable() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        Assert.assertFalse(def.canRead("setOnlyVal"));
    }
    
    @Test
    public void ensureNotSettable() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        Assert.assertFalse(def.canWrite("readOnlyVal"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void failIfMissing() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        def.getPropertyType("invalid");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void failIfNotSettable() {
        BeanDefinition def = new BeanDefinitionImpl(AComplexBean.class);
        def.canRead("invalid");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void failIfNotReadable() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        def.canWrite("invalid");
    }
}
