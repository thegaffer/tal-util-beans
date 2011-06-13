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
package org.talframework.util.beans.binding.nodes;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talframework.util.beans.AComplexBean;

/**
 * This class tests the functionality of the object node
 *
 * @author Tom Spencer
 */
public class TestObjectNode {
    
    private Mockery context = new JUnit4Mockery();
    private ObjectNode underTest = null;
    
    @Before
    public void setup() {
        underTest = new ObjectNode(null, "test");
    }

    @Test
    public void basic() {
        BindingNode node = underTest.addProperty("stringVal", null, "test");
        Assert.assertSame(underTest, node);
        
        AComplexBean bean = new AComplexBean();
        underTest.apply(null, null, bean);
        Assert.assertEquals("test", bean.getStringVal());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void addKey() {
        underTest.addProperty("stringVal", "1", "test");
    }
}
