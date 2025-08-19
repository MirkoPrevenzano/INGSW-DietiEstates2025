
package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum NotaryDeedState implements JsonStringValueEnum
{
    FREE("Free"),
    OCCUPIED("Occupied"),
    BARE_PROPERTY("Bare property"),
    NOT_SPECIFIED("Not specified");


    private final String value;


    private NotaryDeedState(String value) 
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
    public static NotaryDeedState fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(NotaryDeedState.class, value);
    }   
}