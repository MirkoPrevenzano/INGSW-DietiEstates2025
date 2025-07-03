
package com.dietiEstates.backend.extra;

import java.util.stream.Stream;

public interface ValidatableEnum 
{
    public String getValue();
}


/* default public ValidatableEnum of(String value)
{
    return Stream.of(this.getClass().getEnumConstants()).filter(f -> f.getValue().equals(value)).findFirst().get();
} */