
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidableEnum;



public enum EnergyClass implements ValidableEnum
{
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G");



    private final String value;



    EnergyClass(String value) 
    {
        this.value = value;
    };



    public String getValue() 
    {
        return this.value;
    }
}