/* 
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum ContractType 
{
    FOR_SALE("For Sale"),
    FOR_RENT("For Rent");


    private final String value;


    private ContractType(String value) 
    {
        this.value = value;
    };


    @JsonValue
    public String getValue() 
    {
        return this.value;
    }


    @JsonCreator
    static public ContractType of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(ContractType.values())
                     .filter(contractType -> contractType.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Contract type value not valid: '" + value + "'"));
    }   
} */



package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum ContractType implements JsonStringValueEnum
{
    FOR_SALE("For Sale"),
    FOR_RENT("For Rent");


    private final String value;


    private ContractType(String value) 
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
    static public ContractType fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(ContractType.class, value);
    }   
}