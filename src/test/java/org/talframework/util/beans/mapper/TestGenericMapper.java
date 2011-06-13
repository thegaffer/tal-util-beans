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
package org.talframework.util.beans.mapper;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.talframework.util.beans.ASimilarBean;
import org.talframework.util.beans.AnotherBean;
import org.talframework.util.beans.BeanDefinition;
import org.talframework.util.beans.NearlySimilar;
import org.talframework.util.beans.NearlySimilarBean;
import org.talframework.util.beans.definition.BeanDefinitionsSingleton;

/**
 * This class tests the {@link GenericMapper}.
 *
 * @author Tom Spencer
 */
public class TestGenericMapper {

    @Test
    public void basic() {
        GenericMapper mapper = new GenericMapper();
        mapper.setSourceDefinition(BeanDefinitionsSingleton.getInstance().getDefinition(AnotherBean.class));
        mapper.setDestDefinition(BeanDefinitionsSingleton.getInstance().getDefinition(ASimilarBean.class));
        
        AnotherBean bean = new AnotherBean();
        ASimilarBean similar = mapper.map(bean, ASimilarBean.class);
        Assert.assertNotNull(similar);
        Assert.assertEquals(bean.toString(), similar.toString());
    }
    
    @Test
    public void nameVariation() {
        GenericMapper mapper = new GenericMapper();
        mapper.setSourceDefinition(BeanDefinitionsSingleton.getInstance().getDefinition(AnotherBean.class));
        mapper.setDestDefinition(BeanDefinitionsSingleton.getInstance().getDefinition(NearlySimilarBean.class));
        mapper.addNameVariation("stringVal", "differentVal");
        
        AnotherBean bean = new AnotherBean();
        NearlySimilar similar = new NearlySimilarBean();
        mapper.map(bean, similar);
        Assert.assertEquals(bean.toString(), similar.toString());
    }
    
    @Test
    @Ignore("Test not yet written")
    public void customMapping() {
        
    }
    
    @Test
    @Ignore("Test not yet written")
    public void withStringConversion() {
        
    }
    
    @Test
    @Ignore("Test not yet written")
    public void withNumberConversion() {
        
    }
    
    @Test
    @Ignore("Test not yet written")
    public void withCollectionConversion() {
        
    }
    
    @Test
    @Ignore("Test not yet written")
    public void withBooleanConversion() {
        
    }
    
    @Test
    @Ignore("Test not yet written")
    public void withDateConversion() {
        
    }
    
    @Test
    public void properties() {
        GenericMapper mapper = new GenericMapper();
        
        BeanDefinition sourceDef = BeanDefinitionsSingleton.getInstance().getDefinition(AnotherBean.class);
        mapper.setSourceDefinition(sourceDef);
        Assert.assertEquals(sourceDef, mapper.getSourceDefinition());
        
        BeanDefinition destDef = BeanDefinitionsSingleton.getInstance().getDefinition(ASimilarBean.class);
        mapper.setDestDefinition(destDef);
        Assert.assertEquals(destDef, mapper.getDestDefinition());
        
        Map<String, String> nameVariations = new HashMap<String, String>();
        mapper.setNameVariations(nameVariations);
        Assert.assertEquals(nameVariations, mapper.getNameVariations());
        
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();
        mapper.setPropertyMappers(mappers);
        Assert.assertEquals(mappers, mapper.getPropertyMappers());
    }
}
