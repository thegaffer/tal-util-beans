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

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.talframework.util.beans.AComplexBean;

/**
 * This class tests the generic binder, both unit tests and
 * some semi-integration tests.
 *
 * @author Tom Spencer
 */
public class TestGenericBinder {

    /**
     * This is a fullish test into our sample beans
     */
    @Test
    public void fullTest() {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("stringVal", "A name");
        input.put("readOnlyVal", "Some other value that will be ignored");
        // TODO: Add in date support - input.put("dateVal", "");
        input.put("dblVal", "55");
        input.put("fltVal", "65");
        input.put("lngVal", "75");
        input.put("intVal", "85");
        input.put("shrVal", "95");
        input.put("boolVal", "false");
        input.put("charVal", "T");
        input.put("arrayVal", new String[]{"a", "b", "c", "d"});
        input.put("objectVal.stringVal", "Another Bean 1");
        input.put("objectVal.dblVal2", "77");
        input.put("objectArrayVal[0].stringVal", "Another Bean 2");
        input.put("objectArrayVal[0].dblVal2", "88");
        input.put("objectArrayVal[1].stringVal", "Another Bean 3");
        input.put("objectArrayVal[1].fltVal2", "99");
        input.put("objectCollectionVal[first].stringVal", "Another Bean 4");
        input.put("objectCollectionVal[first].stringVal", "Another Bean 5");

        AComplexBean target = new AComplexBean();
        GenericBinder binder = new GenericBinder();
        BindingRequest<AComplexBean> request = new StandardBindingRequest<AComplexBean>(target, input);
        BindingResult<AComplexBean> result = binder.bind(request);
        Assert.assertNotNull(result);
        
        System.out.println(target);
    }
}
