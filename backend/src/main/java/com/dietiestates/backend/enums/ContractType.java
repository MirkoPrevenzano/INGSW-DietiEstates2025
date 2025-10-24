
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
    }



    @Override
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }
    

    @JsonCreator
    public static ContractType fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(ContractType.class, value);
    }   
}