
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dietiEstates.backend.extra.ValidatableEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class EnumValidatorImpl implements ConstraintValidator<EnumValidator,CharSequence> 
{
    private List<String> acceptedValues;


    
    @Override
    public void initialize(EnumValidator enumValidator) 
    {
        acceptedValues = Stream.of(enumValidator.enumClass().getEnumConstants())
                                .map(ValidatableEnum::getValue)
                                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) 
    {
        return acceptedValues.contains(value.toString());
    }

}