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

import junit.framework.Assert;

import org.junit.Test;
import org.talframework.util.beans.AComplexBean;
import org.talframework.util.beans.AnotherBean;
import org.talframework.util.beans.BeanDefinition;

/**
 * Tests the bean definition singleton
 *
 * @author Tom Spencer
 */
public class TestBeanDefinitionsSingleton {

    @Test
    public void basic() {
        BeanDefinition def = BeanDefinitionsSingleton.getInstance().getDefinition(AComplexBean.class);
        Assert.assertNotNull(def);
        
        BeanDefinition def2 = BeanDefinitionsSingleton.getInstance().getDefinition(AComplexBean.class);
        Assert.assertTrue(def == def2);
    }

    @Test
    public void register() {
        BeanDefinition def = new BeanDefinitionImpl(AnotherBean.class);
        BeanDefinitionsSingleton.getInstance().addDefinition(def);
        
        BeanDefinition def2 = BeanDefinitionsSingleton.getInstance().getDefinition(AnotherBean.class);
        Assert.assertTrue(def == def2);
    }
}
