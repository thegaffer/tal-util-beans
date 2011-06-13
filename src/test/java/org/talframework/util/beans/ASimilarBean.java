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
package org.talframework.util.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talframework.util.beans.mapper.GenericMapper;


/**
 * This bean is identical in structure to {@link AnotherBean}.
 * This is used in testing the {@link GenericMapper}
 *
 * @author Tom Spencer
 */
public class ASimilarBean {

    private String stringVal = "String";
    private String readOnlyVal = "Default";
    private String setOnlyVal = "SomeVal";
    private Double dblVal2 = 1004.5678;
    private Float fltVal2 = 3.14159f;
    private Long lngVal2 = 1006L;
    private Integer intVal2 = 1005678;
    private Short shrVal2 = 6678;
    private Boolean boolVal2 = false;
    private Character charVal2 = 'y';
    private List<String> collectionVal = new ArrayList<String>();
    private Map<String, String> mapVal = new HashMap<String, String>();
    
    public ASimilarBean() {
        collectionVal.add("String1");
        collectionVal.add("String2");
        collectionVal.add("String3");
        
        mapVal.put("Key1", "Map1");
        mapVal.put("Key2", "Map2");
    }
    
    public ASimilarBean(String val) {
        this.stringVal = val;
    }
    
    /**
     * @return the stringVal
     */
    public String getStringVal() {
        return stringVal;
    }
    /**
     * Setter for the stringVal field
     *
     * @param stringVal the stringVal to set
     */
    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }
    /**
     * @return the dblVal2
     */
    public Double getDblVal2() {
        return dblVal2;
    }
    /**
     * Setter for the dblVal2 field
     *
     * @param dblVal2 the dblVal2 to set
     */
    public void setDblVal2(Double dblVal2) {
        this.dblVal2 = dblVal2;
    }
    /**
     * @return the fltVal2
     */
    public Float getFltVal2() {
        return fltVal2;
    }
    /**
     * Setter for the fltVal2 field
     *
     * @param fltVal2 the fltVal2 to set
     */
    public void setFltVal2(Float fltVal2) {
        this.fltVal2 = fltVal2;
    }
    /**
     * @return the lngVal2
     */
    public Long getLngVal2() {
        return lngVal2;
    }
    /**
     * Setter for the lngVal2 field
     *
     * @param lngVal2 the lngVal2 to set
     */
    public void setLngVal2(Long lngVal2) {
        this.lngVal2 = lngVal2;
    }
    /**
     * @return the intVal2
     */
    public Integer getIntVal2() {
        return intVal2;
    }
    /**
     * Setter for the intVal2 field
     *
     * @param intVal2 the intVal2 to set
     */
    public void setIntVal2(Integer intVal2) {
        this.intVal2 = intVal2;
    }
    /**
     * @return the shrVal2
     */
    public Short getShrVal2() {
        return shrVal2;
    }
    /**
     * Setter for the shrVal2 field
     *
     * @param shrVal2 the shrVal2 to set
     */
    public void setShrVal2(Short shrVal2) {
        this.shrVal2 = shrVal2;
    }
    /**
     * @return the boolVal2
     */
    public Boolean getBoolVal2() {
        return boolVal2;
    }
    /**
     * Setter for the boolVal2 field
     *
     * @param boolVal2 the boolVal2 to set
     */
    public void setBoolVal2(Boolean boolVal2) {
        this.boolVal2 = boolVal2;
    }
    /**
     * @return the charVal2
     */
    public Character getCharVal2() {
        return charVal2;
    }
    /**
     * Setter for the charVal2 field
     *
     * @param charVal2 the charVal2 to set
     */
    public void setCharVal2(Character charVal2) {
        this.charVal2 = charVal2;
    }
    /**
     * @return the readOnlyVal
     */
    public String getReadOnlyVal() {
        return readOnlyVal;
    }
    /**
     * Setter for the setOnlyVal field
     *
     * @param setOnlyVal the setOnlyVal to set
     */
    public void setSetOnlyVal(String setOnlyVal) {
        this.setOnlyVal = setOnlyVal;
    }
    /**
     * @return the collectionVal
     */
    public List<String> getCollectionVal() {
        return collectionVal;
    }
    /**
     * Setter for the collectionVal field
     *
     * @param collectionVal the collectionVal to set
     */
    public void setCollectionVal(List<String> collectionVal) {
        this.collectionVal = collectionVal;
    }
    /**
     * @return the mapVal
     */
    public Map<String, String> getMapVal() {
        return mapVal;
    }
    /**
     * Setter for the mapVal field
     *
     * @param mapVal the mapVal to set
     */
    public void setMapVal(Map<String, String> mapVal) {
        this.mapVal = mapVal;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((boolVal2 == null) ? 0 : boolVal2.hashCode());
        result = prime * result + ((charVal2 == null) ? 0 : charVal2.hashCode());
        result = prime * result + ((collectionVal == null) ? 0 : collectionVal.hashCode());
        result = prime * result + ((dblVal2 == null) ? 0 : dblVal2.hashCode());
        result = prime * result + ((fltVal2 == null) ? 0 : fltVal2.hashCode());
        result = prime * result + ((intVal2 == null) ? 0 : intVal2.hashCode());
        result = prime * result + ((lngVal2 == null) ? 0 : lngVal2.hashCode());
        result = prime * result + ((mapVal == null) ? 0 : mapVal.hashCode());
        result = prime * result + ((readOnlyVal == null) ? 0 : readOnlyVal.hashCode());
        result = prime * result + ((setOnlyVal == null) ? 0 : setOnlyVal.hashCode());
        result = prime * result + ((shrVal2 == null) ? 0 : shrVal2.hashCode());
        result = prime * result + ((stringVal == null) ? 0 : stringVal.hashCode());
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if( this == obj ) return true;
        if( obj == null ) return false;
        if( getClass() != obj.getClass() ) return false;
        ASimilarBean other = (ASimilarBean)obj;
        if( boolVal2 == null ) {
            if( other.boolVal2 != null ) return false;
        }
        else if( !boolVal2.equals(other.boolVal2) ) return false;
        if( charVal2 == null ) {
            if( other.charVal2 != null ) return false;
        }
        else if( !charVal2.equals(other.charVal2) ) return false;
        if( collectionVal == null ) {
            if( other.collectionVal != null ) return false;
        }
        else if( !collectionVal.equals(other.collectionVal) ) return false;
        if( dblVal2 == null ) {
            if( other.dblVal2 != null ) return false;
        }
        else if( !dblVal2.equals(other.dblVal2) ) return false;
        if( fltVal2 == null ) {
            if( other.fltVal2 != null ) return false;
        }
        else if( !fltVal2.equals(other.fltVal2) ) return false;
        if( intVal2 == null ) {
            if( other.intVal2 != null ) return false;
        }
        else if( !intVal2.equals(other.intVal2) ) return false;
        if( lngVal2 == null ) {
            if( other.lngVal2 != null ) return false;
        }
        else if( !lngVal2.equals(other.lngVal2) ) return false;
        if( mapVal == null ) {
            if( other.mapVal != null ) return false;
        }
        else if( !mapVal.equals(other.mapVal) ) return false;
        if( readOnlyVal == null ) {
            if( other.readOnlyVal != null ) return false;
        }
        else if( !readOnlyVal.equals(other.readOnlyVal) ) return false;
        if( setOnlyVal == null ) {
            if( other.setOnlyVal != null ) return false;
        }
        else if( !setOnlyVal.equals(other.setOnlyVal) ) return false;
        if( shrVal2 == null ) {
            if( other.shrVal2 != null ) return false;
        }
        else if( !shrVal2.equals(other.shrVal2) ) return false;
        if( stringVal == null ) {
            if( other.stringVal != null ) return false;
        }
        else if( !stringVal.equals(other.stringVal) ) return false;
        return true;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     * 
     * Note this is the same as AnotherBean so we can test!
     */
    @Override
    public String toString() {
        return "AnotherBean [boolVal2=" + boolVal2 + ", charVal2=" + charVal2 + ", collectionVal=" + collectionVal + ", dblVal2=" + dblVal2 + ", fltVal2="
                + fltVal2 + ", intVal2=" + intVal2 + ", lngVal2=" + lngVal2 + ", mapVal=" + mapVal + ", readOnlyVal=" + readOnlyVal + ", setOnlyVal="
                + setOnlyVal + ", shrVal2=" + shrVal2 + ", stringVal=" + stringVal + "]";
    }
}
