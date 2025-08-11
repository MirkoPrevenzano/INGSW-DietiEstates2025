
package com.dietiEstates.backend.enums.common;

import java.util.Arrays;


public interface StringValueEnum 
{
    public String getValue();

    public static <E extends Enum<E> & StringValueEnum> E fromValue(Class<E> enumType, String value) 
    {
        if (value == null) return null;

        return Arrays.stream(enumType.getEnumConstants())
                     .filter(e -> e.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(enumType.getSimpleName() + " value not valid: '" + value + "'"));
    }
}