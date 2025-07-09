
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public enum EnergyClass
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



    private EnergyClass(String value) 
    {
        this.value = value;
    };



    @JsonValue
    public String getValue() 
    {
        return this.value;
    }


    @JsonCreator
    static public EnergyClass of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(EnergyClass.values())
                     .filter(energyClass -> energyClass.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Energy class value not valid: '" + value + "'"));
    }
}