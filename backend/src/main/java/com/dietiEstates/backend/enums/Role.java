/* 
package com.dietiEstates.backend.enums;



public enum Role 
{
    ROLE_CUSTOMER,
    ROLE_AGENT,
    ROLE_ADMIN,
    ROLE_COLLABORATOR,
    ROLE_UNAUTHORIZED
} */


package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.dietiEstates.backend.exception.RoleMismatchException;


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
                     .orElseThrow(() -> new RoleMismatchException("Role value not valid: '" + value + "'"));
    }
}