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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.talframework.util.beans.definition.BeanDefinitionImpl;

/**
 * This specially constructed bean allows the 
 * {@link BeanDefinitionImpl} to be tested.
 *
 * @author Tom Spencer
 */
public class AComplexBean {

    private String stringVal = "String";
    private String readOnlyVal = "Default";
    private Date dateVal;
    private double dblVal = 123.33;
    private float fltVal = 567.67f;
    private long lngVal = 34;
    private int intVal = 67898;
    private short shrVal = 345;
    private boolean boolVal = true;
    private char charVal = 'x';
    private String[] arrayVal = {"Val1", "Val2", "Val3"};
    private AnotherBean objectVal;
    private AnotherBean[] objectArrayVal;
    private Set<AnotherBean> objectCollectionVal = new HashSet<AnotherBean>();
    
    public AComplexBean() {
        Calendar cal = Calendar.getInstance();
        cal.set(2009, 7, 16, 12, 34);
        dateVal = cal.getTime();
        
        objectVal = new AnotherBean("AnotherBean");
        
        objectArrayVal = new AnotherBean[]{
                new AnotherBean("ArrayBean1"), 
                new AnotherBean("ArrayBean2")};
        
        objectCollectionVal.add(new AnotherBean("SetBean1"));
        objectCollectionVal.add(new AnotherBean("SetBean2"));
        objectCollectionVal.add(new AnotherBean("SetBean3"));
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
     * @return the dateVal
     */
    public Date getDateVal() {
        return dateVal;
    }

    /**
     * Setter for the dateVal field
     *
     * @param dateVal the dateVal to set
     */
    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    /**
     * @return the dblVal
     */
    public double getDblVal() {
        return dblVal;
    }

    /**
     * Setter for the dblVal field
     *
     * @param dblVal the dblVal to set
     */
    public void setDblVal(double dblVal) {
        this.dblVal = dblVal;
    }

    /**
     * @return the fltVal
     */
    public float getFltVal() {
        return fltVal;
    }

    /**
     * Setter for the fltVal field
     *
     * @param fltVal the fltVal to set
     */
    public void setFltVal(float fltVal) {
        this.fltVal = fltVal;
    }

    /**
     * @return the lngVal
     */
    public long getLngVal() {
        return lngVal;
    }

    /**
     * Setter for the lngVal field
     *
     * @param lngVal the lngVal to set
     */
    public void setLngVal(long lngVal) {
        this.lngVal = lngVal;
    }

    /**
     * @return the intVal
     */
    public int getIntVal() {
        return intVal;
    }

    /**
     * Setter for the intVal field
     *
     * @param intVal the intVal to set
     */
    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    /**
     * @return the shrVal
     */
    public short getShrVal() {
        return shrVal;
    }

    /**
     * Setter for the shrVal field
     *
     * @param shrVal the shrVal to set
     */
    public void setShrVal(short shrVal) {
        this.shrVal = shrVal;
    }

    /**
     * @return the boolVal
     */
    public boolean isBoolVal() {
        return boolVal;
    }

    /**
     * Setter for the boolVal field
     *
     * @param boolVal the boolVal to set
     */
    public void setBoolVal(boolean boolVal) {
        this.boolVal = boolVal;
    }

    /**
     * @return the charVal
     */
    public char getCharVal() {
        return charVal;
    }

    /**
     * Setter for the charVal field
     *
     * @param charVal the charVal to set
     */
    public void setCharVal(char charVal) {
        this.charVal = charVal;
    }

    /**
     * @return the arrayVal
     */
    public String[] getArrayVal() {
        return arrayVal;
    }

    /**
     * Setter for the arrayVal field
     *
     * @param arrayVal the arrayVal to set
     */
    public void setArrayVal(String[] arrayVal) {
        this.arrayVal = arrayVal;
    }

    /**
     * @return the objectVal
     */
    public AnotherBean getObjectVal() {
        return objectVal;
    }

    /**
     * Setter for the objectVal field
     *
     * @param objectVal the objectVal to set
     */
    public void setObjectVal(AnotherBean objectVal) {
        this.objectVal = objectVal;
    }

    /**
     * @return the objectArrayVal
     */
    public AnotherBean[] getObjectArrayVal() {
        return objectArrayVal;
    }

    /**
     * Setter for the objectArrayVal field
     *
     * @param objectArrayVal the objectArrayVal to set
     */
    public void setObjectArrayVal(AnotherBean[] objectArrayVal) {
        this.objectArrayVal = objectArrayVal;
    }

    /**
     * @return the objectCollectionVal
     */
    public Set<AnotherBean> getObjectCollectionVal() {
        return objectCollectionVal;
    }

    /**
     * Setter for the objectCollectionVal field
     *
     * @param objectCollectionVal the objectCollectionVal to set
     */
    public void setObjectCollectionVal(Set<AnotherBean> objectCollectionVal) {
        this.objectCollectionVal = objectCollectionVal;
    }

    /**
     * @return the readOnlyVal
     */
    public String getReadOnlyVal() {
        return readOnlyVal;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arrayVal);
        result = prime * result + (boolVal ? 1231 : 1237);
        result = prime * result + charVal;
        result = prime * result + ((dateVal == null) ? 0 : dateVal.hashCode());
        long temp;
        temp = Double.doubleToLongBits(dblVal);
        result = prime * result + (int)(temp ^ (temp >>> 32));
        result = prime * result + Float.floatToIntBits(fltVal);
        result = prime * result + intVal;
        result = prime * result + (int)(lngVal ^ (lngVal >>> 32));
        result = prime * result + Arrays.hashCode(objectArrayVal);
        result = prime * result + ((objectCollectionVal == null) ? 0 : objectCollectionVal.hashCode());
        result = prime * result + ((objectVal == null) ? 0 : objectVal.hashCode());
        result = prime * result + ((readOnlyVal == null) ? 0 : readOnlyVal.hashCode());
        result = prime * result + shrVal;
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
        AComplexBean other = (AComplexBean)obj;
        if( !Arrays.equals(arrayVal, other.arrayVal) ) return false;
        if( boolVal != other.boolVal ) return false;
        if( charVal != other.charVal ) return false;
        if( dateVal == null ) {
            if( other.dateVal != null ) return false;
        }
        else if( !dateVal.equals(other.dateVal) ) return false;
        if( Double.doubleToLongBits(dblVal) != Double.doubleToLongBits(other.dblVal) ) return false;
        if( Float.floatToIntBits(fltVal) != Float.floatToIntBits(other.fltVal) ) return false;
        if( intVal != other.intVal ) return false;
        if( lngVal != other.lngVal ) return false;
        if( !Arrays.equals(objectArrayVal, other.objectArrayVal) ) return false;
        if( objectCollectionVal == null ) {
            if( other.objectCollectionVal != null ) return false;
        }
        else if( !objectCollectionVal.equals(other.objectCollectionVal) ) return false;
        if( objectVal == null ) {
            if( other.objectVal != null ) return false;
        }
        else if( !objectVal.equals(other.objectVal) ) return false;
        if( readOnlyVal == null ) {
            if( other.readOnlyVal != null ) return false;
        }
        else if( !readOnlyVal.equals(other.readOnlyVal) ) return false;
        if( shrVal != other.shrVal ) return false;
        if( stringVal == null ) {
            if( other.stringVal != null ) return false;
        }
        else if( !stringVal.equals(other.stringVal) ) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AComplexBean [arrayVal=" + Arrays.toString(arrayVal) + ", boolVal=" + boolVal + ", charVal=" + charVal + ", dateVal=" + dateVal + ", dblVal="
                + dblVal + ", fltVal=" + fltVal + ", intVal=" + intVal + ", lngVal=" + lngVal + ", objectArrayVal=" + Arrays.toString(objectArrayVal)
                + ", objectCollectionVal=" + objectCollectionVal + ", objectVal=" + objectVal + ", readOnlyVal=" + readOnlyVal + ", shrVal=" + shrVal
                + ", stringVal=" + stringVal + "]";
    }
}
