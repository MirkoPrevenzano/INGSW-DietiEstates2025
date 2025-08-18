/* 
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum NotaryDeedState
{
    FREE("Free"),
    OCCUPIED("Occupied"),
    BARE_PROPERTY("Bare property"),
    NOT_SPECIFIED("Not specified");


    private final String value;
    

    private NotaryDeedState(String value) 
    {
        this.value = value;
    };

    
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }


    @JsonCreator
    static public NotaryDeedState of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(NotaryDeedState.values())
                     .filter(notaryDeedState -> notaryDeedState.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Notary Deed State value not valid: '" + value + "'"));
    }
} */




package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.enums.common.JsonStringValueEnum;

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
    };


    @Override
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }

    @JsonCreator
    static public NotaryDeedState fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(NotaryDeedState.class, value);
    }   
}