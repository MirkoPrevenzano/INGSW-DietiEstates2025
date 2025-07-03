
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidatableEnum;



public enum EnergyClass implements ValidatableEnum
{
    A_PLUS_PLUS("A++"),
    A_PLUS("A+"),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    NON_SPECIFICATA("Non specificata");



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