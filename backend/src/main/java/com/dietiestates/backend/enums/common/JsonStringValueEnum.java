
package com.dietiestates.backend.enums.common;

import java.util.Arrays;


public interface JsonStringValueEnum 
{
    public String getValue();

    
    public static <E extends Enum<E> & JsonStringValueEnum> E fromValue(Class<E> enumType, String value) 
    {
        if (value == null) return null;

        return Arrays.stream(enumType.getEnumConstants())
                     .filter(e -> e.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(enumType.getSimpleName() + " value not valid: '" + value + "'"));
    }
}