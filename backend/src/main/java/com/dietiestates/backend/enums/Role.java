
package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;


public enum Role implements JsonStringValueEnum
{
    ROLE_CUSTOMER("Customer"),

    ROLE_AGENT("Agent"),

    ROLE_ADMIN("Admin"),

    ROLE_COLLABORATOR("Collaborator"),

    ROLE_UNAUTHORIZED("");


    private final String value;



    private Role(String value) 
    {
        this.value = value;
    }



    @Override
    public String getValue() 
    {
        return this.value;
    }

    
    public static Role fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(Role.class, value);
    }   
}