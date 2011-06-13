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
package org.talframework.util.beans.cloner;

import junit.framework.Assert;

import org.junit.Test;
import org.talframework.util.beans.AnotherBean;

/**
 * This class tests the generic cloner, both functionally
 * and a basic performance test.
 *
 * @author Tom Spencer
 */
public class TestGenericCloner {

    @Test
    public void basic() {
        GenericCloner cloner = new GenericCloner();
        
        AnotherBean bean = new AnotherBean();
        bean.setStringVal("ToClone");
        AnotherBean clone = cloner.shallowClone(bean);
        
        Assert.assertNotNull(clone);
        Assert.assertEquals("ToClone", clone.getStringVal());
    }
    
    @Test
    public void performance() throws Exception {
        Runnable cmd = new Runnable() {
            public void run() {
                GenericCloner cloner = new GenericCloner();
                AnotherBean bean = new AnotherBean();
                
                double runs = 100000;
                long st = System.nanoTime();
                for( int i = 0 ; i < 100000 ; i++ ) {
                    AnotherBean clone = cloner.shallowClone(bean);
                    Assert.assertNotNull(clone);
                }
                double timePerClone = ((System.nanoTime() - st) / runs) / 1000000;
                
                System.out.println(Thread.currentThread().getId() + ": " + timePerClone + "ms");
                Assert.assertTrue(timePerClone < 0.02);
            }
        };
        
        Thread[] threads = new Thread[]{new Thread(cmd), new Thread(cmd), new Thread(cmd), new Thread(cmd)};
        for( Thread t : threads ) t.start();
        for( Thread t : threads ) t.join();
    }
    
    @Test
    public void performanceOnExistingInstance() throws Exception {
        Runnable cmd = new Runnable() {
            public void run() {
                GenericCloner cloner = new GenericCloner();
        
                AnotherBean bean = new AnotherBean();
                AnotherBean dest = new AnotherBean();
                
                double runs = 100000;
                long st = System.nanoTime();
                for( int i = 0 ; i < 100000 ; i++ ) {
                    AnotherBean clone = cloner.shallowClone(bean, dest);
                    Assert.assertNotNull(clone);
                }
                double timePerClone = ((System.nanoTime() - st) / runs) / 1000000;
                System.out.println(Thread.currentThread().getId() + ": " + timePerClone + "ms");
                Assert.assertTrue(timePerClone < 0.01);
            }
        };
        
        Thread[] threads = new Thread[]{new Thread(cmd), new Thread(cmd), new Thread(cmd), new Thread(cmd)};
        for( Thread t : threads ) t.start();
        for( Thread t : threads ) t.join();
    }
}
