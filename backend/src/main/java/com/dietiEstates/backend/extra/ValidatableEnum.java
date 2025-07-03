
package com.dietiEstates.backend.extra;

import java.util.stream.Stream;


public interface ValidatableEnum 
{
    public String getValue();


    default public ValidatableEnum of(String value)
    {
        return Stream.of(this.getClass().getEnumConstants())
                     .filter(filteredEnum -> filteredEnum.getValue().equals(value))
                     .findFirst()
                     .orElseThrow();
    }

    static public <E extends Enum<E>> E of(Class<E> enumClass, String value) 
    {
        return Stream.of(enumClass.getEnumConstants())
                     .filter(filteredEnum -> ((ValidatableEnum) filteredEnum).getValue().equals(value))
                     .findFirst()
                     .orElseThrow();
    }
}