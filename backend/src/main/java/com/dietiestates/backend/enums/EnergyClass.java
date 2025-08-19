
package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum EnergyClass implements JsonStringValueEnum
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
    NOT_SPECIFIED("Not specified");


    private final String value;


    private EnergyClass(String value) 
    {
        this.value = value;
    }


    @Override
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }

    @JsonCreator
    public static EnergyClass fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(EnergyClass.class, value);
    }   
}