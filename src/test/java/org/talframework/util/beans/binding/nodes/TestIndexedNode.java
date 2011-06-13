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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talframework.util.beans.AComplexBean;
import org.talframework.util.beans.AnotherBean;
import org.talframework.util.beans.binding.BindingObjectCreator;
import org.talframework.util.beans.binding.BindingRequest;
import org.talframework.util.beans.binding.StandardBindingResult;

/**
 * This class tests the indexed node
 * 
 * @author Tom Spencer
 */
public class TestIndexedNode {

    private Mockery context = new JUnit4Mockery();
    
    private AComplexBean bean;
    private BindingRequest<AComplexBean> complexRequest;
    private StandardBindingResult<AComplexBean> complexResult;
    private BindingObjectCreator creator = null;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setup() {
        creator = context.mock(BindingObjectCreator.class);
        bean = new AComplexBean();
        complexRequest = context.mock(BindingRequest.class);
        complexResult = new StandardBindingResult<AComplexBean>(bean);
        
        context.checking(new Expectations() {{
            allowing(complexRequest).getObjectCreator(); will(returnValue(creator));
        }});
    }
    
    @Test
    public void basicArray() {
        IndexedNode underTest = new IndexedNode(null, "objectArrayVal");
        
        BindingNode node = underTest.addProperty("stringVal", "0", "test1");
        Assert.assertNotNull(node);
        Assert.assertNotSame(node, underTest);
        underTest.addProperty("stringVal", "1", "test2");
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertEquals("test1", bean.getObjectArrayVal()[0].getStringVal());
        Assert.assertEquals("test2", bean.getObjectArrayVal()[1].getStringVal());
    }
    
    @Test
    public void smallerArray() {
        IndexedNode underTest = new IndexedNode(null, "objectArrayVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertEquals(1, bean.getObjectArrayVal().length);
        Assert.assertEquals("test1", bean.getObjectArrayVal()[0].getStringVal());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void largerArray() {
        IndexedNode underTest = new IndexedNode(null, "objectArrayVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        underTest.addProperty("stringVal", "1", "test2");
        underTest.addProperty("stringVal", "2", "test3");
        
        final AnotherBean another1 = new AnotherBean();
        
        context.checking(new Expectations() {{
            oneOf(creator).createObject(with(bean), with(AnotherBean.class), with("objectArrayVal"), (Map<String, Object>)with(anything()));
                will(returnValue(another1));
        }});
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertEquals(3, bean.getObjectArrayVal().length);
        Assert.assertEquals("test1", bean.getObjectArrayVal()[0].getStringVal());
        Assert.assertEquals("test2", bean.getObjectArrayVal()[1].getStringVal());
        Assert.assertEquals("test3", bean.getObjectArrayVal()[2].getStringVal());
        context.assertIsSatisfied();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void initiallyNullArray() {
        IndexedNode underTest = new IndexedNode(null, "objectArrayVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        
        bean.setObjectArrayVal(null);
        
        final AnotherBean another1 = new AnotherBean();
        
        context.checking(new Expectations() {{
            oneOf(creator).createObject(with(bean), with(AnotherBean.class), with("objectArrayVal"), (Map<String, Object>)with(anything()));
                will(returnValue(another1));
        }});
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertEquals(1, bean.getObjectArrayVal().length);
        Assert.assertEquals("test1", bean.getObjectArrayVal()[0].getStringVal());
        context.assertIsSatisfied();
    }
    
    @Test
    public void basicSet() {
        IndexedNode underTest = new IndexedNode(null, "objectCollectionVal");
        
        BindingNode node = underTest.addProperty("stringVal", "0", "test1");
        Assert.assertNotNull(node);
        Assert.assertNotSame(node, underTest);
        
        underTest.addProperty("stringVal", "1", "test2");
        underTest.addProperty("stringVal", "2", "test3");
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertNotNull(bean.getObjectCollectionVal());
        Assert.assertEquals(3, bean.getObjectCollectionVal().size());
        Iterator<AnotherBean> it = bean.getObjectCollectionVal().iterator();
        List<String> possibles = Arrays.asList("test1", "test2", "test3");
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
    }
    
    @Test
    public void smallerSet() {
        IndexedNode underTest = new IndexedNode(null, "objectCollectionVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertNotNull(bean.getObjectCollectionVal());
        Assert.assertEquals(1, bean.getObjectCollectionVal().size());
        Iterator<AnotherBean> it = bean.getObjectCollectionVal().iterator();
        List<String> possibles = Arrays.asList("test1");
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void largerSet() {
        IndexedNode underTest = new IndexedNode(null, "objectCollectionVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        underTest.addProperty("stringVal", "1", "test2");
        underTest.addProperty("stringVal", "2", "test3");
        underTest.addProperty("stringVal", "3", "test4");
        
        final AnotherBean another1 = new AnotherBean();
        
        context.checking(new Expectations() {{
            oneOf(creator).createObject(with(bean), with(AnotherBean.class), with("objectCollectionVal"), (Map<String, Object>)with(anything()));
                will(returnValue(another1));
        }});
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertNotNull(bean.getObjectCollectionVal());
        Assert.assertEquals(4, bean.getObjectCollectionVal().size());
        Iterator<AnotherBean> it = bean.getObjectCollectionVal().iterator();
        List<String> possibles = Arrays.asList("test1", "test2", "test3", "test4");
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        context.assertIsSatisfied();
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void initiallyNullSet() {
        IndexedNode underTest = new IndexedNode(null, "objectCollectionVal");
        
        underTest.addProperty("stringVal", "0", "test1");
        
        bean.setObjectCollectionVal(null);
        
        final AnotherBean another1 = new AnotherBean();
        
        context.checking(new Expectations() {{
            oneOf(creator).createObject(with(bean), with(AnotherBean.class), with("objectCollectionVal"), (Map<String, Object>)with(anything()));
                will(returnValue(another1));
        }});
        
        underTest.apply(complexRequest, complexResult, bean);
        Assert.assertNotNull(bean.getObjectCollectionVal());
        Assert.assertEquals(1, bean.getObjectCollectionVal().size());
        Iterator<AnotherBean> it = bean.getObjectCollectionVal().iterator();
        List<String> possibles = Arrays.asList("test1");
        Assert.assertTrue(testOneOf(possibles, it.next().getStringVal()));
        context.assertIsSatisfied();
    }
    
    /**
     * Internal helper to help test sets which come in any order
     */
    private boolean testOneOf(List<?> possibles, Object val) {
        boolean ret = false;
        
        Iterator<?> it = possibles.iterator();
        while( it.hasNext() ) {
            if( val.equals(it.next()) ) {
                ret = true;
                //it.remove();
                break;
            }
        }
        
        return ret;
    }
}
