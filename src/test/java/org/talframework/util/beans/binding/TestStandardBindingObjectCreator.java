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

import java.lang.reflect.Proxy;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.talframework.util.beans.AComplexBean;
import org.talframework.util.beans.NearlySimilar;

/**
 * This tests the basic object creator
 *
 * @author Tom Spencer
 */
public class TestStandardBindingObjectCreator {

    private StandardBindingObjectCreator underTest;
    
    @Before
    public void setup() {
        underTest = new StandardBindingObjectCreator();
    }
    
    @Test
    public void create() {
        Object bean = underTest.createObject(null, AComplexBean.class, null, null);
        Assert.assertNotNull(bean);
        Assert.assertTrue(bean instanceof AComplexBean);
    }
    
    @Test
    public void createInterface() {
        Object bean = underTest.createObject(null, NearlySimilar.class, null, null);
        Assert.assertNotNull(bean);
        Assert.assertTrue(bean instanceof NearlySimilar);
        Assert.assertTrue(Proxy.isProxyClass(bean.getClass()));
    }
}
