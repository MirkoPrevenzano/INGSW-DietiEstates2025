
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.EnumInterface;



public enum EnergyClass implements EnumInterface
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