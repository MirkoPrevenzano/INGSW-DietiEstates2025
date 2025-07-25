
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;


public enum Role 
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
    };


    public String getValue() 
    {
        return this.value;
    }


    static public Role of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(Role.values())
                     .filter(role -> role.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Role value not valid: '" + value + "'"));
    }
}